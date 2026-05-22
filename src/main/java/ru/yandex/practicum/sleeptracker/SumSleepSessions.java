package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class SumSleepSessions implements Function {

    @Override
    public int calculate(List<SleepingSession> sessions) {
        return sessions.size();
    }

    @Override
    public String getDescription() {
        return "Всего сессий сна";
    }
}
