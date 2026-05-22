package ru.yandex.practicum.sleeptracker;

import java.util.List;

public interface Function {

    int calculate(List<SleepingSession> sessions);
    String getDescription();
}
