package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChronotypeCounter implements Function {

    private static final LocalTime OWL_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_END = LocalTime.of(9, 0);
    private static final LocalTime LARK_START = LocalTime.of(22, 0);
    private static final LocalTime LARK_END = LocalTime.of(7, 0);

    @Override
    public int calculate(List<SleepingSession> sessions) {
        String chronotype = determineChronotype(sessions);
        switch (chronotype) {
            case "Сова": return 1;
            case "Жаворонок": return 2;
            case "Голубь": return 3;
            default: return 0;
        }
    }

    @Override
    public String getDescription() {
        return "Определение хронотипа";
    }

    public String determineChronotype(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return "Недостаточно данных";
        }

        // Фильтруем только ночные сессии
        List<SleepingSession> nightSessions = sessions.stream()
                .filter(this::isNightSession)
                .toList();

        if (nightSessions.isEmpty()) {
            return "Нет ночных сессий сна";
        }

        Map<String, Long> counts = nightSessions.stream()
                .map(this::getSessionType)
                .collect(Collectors.groupingBy(
                        type -> type,
                        Collectors.counting()
                ));

        long owl = counts.getOrDefault("Сова", 0L);
        long lark = counts.getOrDefault("Жаворонок", 0L);
        long pigeon = counts.getOrDefault("Голубь", 0L);

        int total = nightSessions.size();

        // Определяем преобладающий хронотип
        if (owl > lark && owl > pigeon) {
            return "Сова";
        } else if (lark > owl && lark > pigeon) {
            return "Жаворонок";
        } else {
            return "Голубь";
        }
    }

    private boolean isNightSession(SleepingSession session) {
        LocalDateTime start = session.getStartSleep();
        LocalDateTime end = session.getFinishSleep();

        boolean isSameDay = start.toLocalDate().equals(end.toLocalDate());
        boolean startedAfterSix = start.getHour() >= 6;

        return !(isSameDay && startedAfterSix);
    }

    private String getSessionType(SleepingSession session) {
        LocalTime startTime = session.getStartSleep().toLocalTime();
        LocalTime endTime = session.getFinishSleep().toLocalTime();

        boolean crossesMidnight = !session.getStartSleep().toLocalDate()
                .equals(session.getFinishSleep().toLocalDate());

        // Сова: заснул в 23:00 или позже
        boolean isOwlStart = startTime.isAfter(LocalTime.of(23, 0))
                || startTime.equals(LocalTime.of(23, 0));

        // Жаворонок: заснул строго до 22:00
        boolean isLarkStart = startTime.isBefore(LocalTime.of(22, 0));

        LocalTime adjustedEnd = endTime;
        if (crossesMidnight) {
            adjustedEnd = endTime.plusHours(24);
        }

        // Сова: проснулся строго ПОСЛЕ 9:00
        boolean isOwlEnd = adjustedEnd.isAfter(LocalTime.of(9, 0));

        // Жаворонок: проснулся строго ДО 7:00
        boolean isLarkEnd = adjustedEnd.isBefore(LocalTime.of(7, 0));

        if (isOwlStart && isOwlEnd) return "Сова";
        if (isLarkStart && isLarkEnd) return "Жаворонок";
        return "Голубь";
    }
}