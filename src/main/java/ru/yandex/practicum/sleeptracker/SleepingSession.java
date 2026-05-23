package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {
    private LocalDateTime startSleep;
    private LocalDateTime finishSleep;
    private SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime startSleep, LocalDateTime finishSleep, SleepQuality sleepQuality) {
        this.startSleep = startSleep;
        this.finishSleep = finishSleep;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getFinishSleep() {
        return finishSleep;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    public LocalDateTime getStartSleep() {
        return startSleep;
    }
}
