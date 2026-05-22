package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class AverageSleepSession implements Function {

    @Override
    public int calculate(List<SleepingSession> sessions) {
        return (int) Math.round(sessions.stream()
                .mapToLong(session -> Duration.between(session.getStartSleep(),
                        session.getFinishSleep()).toMinutes())
                .average()
                .orElse(0));
    }

    @Override
    public String getDescription() {
        return "Среднее время сна";
    }
}