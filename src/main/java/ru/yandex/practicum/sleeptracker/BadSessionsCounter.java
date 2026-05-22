package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class BadSessionsCounter implements Function{

    @Override
    public int calculate(List<SleepingSession> sessions) {
        return (int) sessions.stream()
                .filter(s -> s.getSleepQuality() == SleepQuality.BAD)
                .count();
    }

    @Override
    public String getDescription() {
        return "Количество сессий с плохим качеством сна";
    }
}
