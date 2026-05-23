package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class MaxSleepSession implements Function {

    @Override
    public int calculate(List<SleepingSession> sessions) {
        return Math.round(sessions.stream()
                .mapToLong(session -> Duration.between(session.getStartSleep(),
                        session.getFinishSleep()).toMinutes())
                .max()
                .orElse(0));
    }

    @Override
    public String getDescription() {
        return "Максимальное время сна";
    }
}
