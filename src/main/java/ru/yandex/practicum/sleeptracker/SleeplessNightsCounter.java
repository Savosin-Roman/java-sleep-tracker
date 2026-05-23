package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public class SleeplessNightsCounter implements Function {

    @Override
    public int calculate(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) return 0;

        LocalDate firstDate = sessions.get(0).getStartSleep().toLocalDate();
        LocalDate lastDate = sessions.get(sessions.size() - 1).getStartSleep().toLocalDate();

        int totalNights = Period.between(firstDate, lastDate).getDays() + 1;

        long nightsWithSleep = sessions.stream()
                .filter(this::isNightSession)
                .map(session -> session.getStartSleep().toLocalDate())
                .distinct()
                .count();

        return totalNights - (int) nightsWithSleep;
    }

    @Override
    public String getDescription() {
        return "Количество бессонных ночей";
    }

    private boolean isNightSession(SleepingSession session) {
        LocalDateTime start = session.getStartSleep();
        LocalDateTime end = session.getFinishSleep();

        LocalDate startDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();

        boolean sleptAcrossMidnight = !startDate.equals(endDate);
        boolean sleptBeforeSix = start.getHour() < 6;

        return sleptAcrossMidnight || sleptBeforeSix;
    }
}