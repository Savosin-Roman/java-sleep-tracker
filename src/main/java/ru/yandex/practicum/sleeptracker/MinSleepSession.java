package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class MinSleepSession implements Function{
    @Override
    public int calculate(List<SleepingSession> sessions) {
        return (int) Math.round(sessions.stream()
                .mapToLong(session -> Duration.between(session.getStartSleep(),
                        session.getFinishSleep()).toMinutes())
                .min()
                .orElse(0));
    }

    @Override
    public String getDescription() {
        return "Минимальное время сна";
    }
}
