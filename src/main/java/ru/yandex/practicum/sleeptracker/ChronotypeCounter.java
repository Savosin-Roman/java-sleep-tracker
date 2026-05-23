package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChronotypeCounter implements Function {

    @Override
    public int calculate(List<SleepingSession> sessions) {
        Chronotype chronotype = determineChronotype(sessions);
        return chronotype.getNumber();
    }

    @Override
    public String getDescription() {
        return "Определение хронотипа";
    }

    public Chronotype determineChronotype(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return Chronotype.INSUFFICIENT_DATA;
        }

        List<SleepingSession> nightSessions = sessions.stream()
                .filter(this::isNightSession)
                .toList();

        if (nightSessions.isEmpty()) {
            return Chronotype.NO_NIGHT_SESSIONS;
        }

        Map<Chronotype, Long> counts = nightSessions.stream()
                .map(this::getSessionChronotype)
                .collect(Collectors.groupingBy(
                        chronotype -> chronotype,
                        Collectors.counting()
                ));

        long owl = counts.getOrDefault(Chronotype.OWL, 0L);
        long lark = counts.getOrDefault(Chronotype.LARK, 0L);
        long pigeon = counts.getOrDefault(Chronotype.PIGEON, 0L);

        if (owl > lark && owl > pigeon) {
            return Chronotype.OWL;
        } else if (lark > owl && lark > pigeon) {
            return Chronotype.LARK;
        } else {
            return Chronotype.PIGEON;
        }
    }

    private boolean isNightSession(SleepingSession session) {
        LocalDateTime start = session.getStartSleep();
        LocalDateTime end = session.getFinishSleep();

        boolean isSameDay = start.toLocalDate().equals(end.toLocalDate());
        boolean startedAfterSix = start.getHour() >= 6;

        return !(isSameDay && startedAfterSix);
    }

    private Chronotype getSessionChronotype(SleepingSession session) {
        LocalTime startTime = session.getStartSleep().toLocalTime();
        LocalTime endTime = session.getFinishSleep().toLocalTime();

        boolean crossesMidnight = !session.getStartSleep().toLocalDate()
                .equals(session.getFinishSleep().toLocalDate());

        boolean isOwlStart = startTime.isAfter(LocalTime.of(23, 0))
                || startTime.equals(LocalTime.of(23, 0));

        boolean isLarkStart = startTime.isBefore(LocalTime.of(22, 0));

        LocalTime adjustedEnd = endTime;
        if (crossesMidnight) {
            adjustedEnd = endTime.plusHours(24);
        }

        boolean isOwlEnd = adjustedEnd.isAfter(LocalTime.of(9, 0));

        boolean isLarkEnd = adjustedEnd.isBefore(LocalTime.of(7, 0));

        if (isOwlStart && isOwlEnd) return Chronotype.OWL;
        if (isLarkStart && isLarkEnd) return Chronotype.LARK;
        return Chronotype.PIGEON;
    }
}