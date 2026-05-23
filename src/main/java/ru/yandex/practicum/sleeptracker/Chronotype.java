package ru.yandex.practicum.sleeptracker;

public enum Chronotype {
    OWL(1, "Сова"),
    LARK(2, "Жаворонок"),
    PIGEON(3, "Голубь"),
    INSUFFICIENT_DATA(0, "Недостаточно данных"),
    NO_NIGHT_SESSIONS(0, "Нет ночных сессий сна");

    private final int number;
    private final String displayName;

    Chronotype(int number, String displayName) {
        this.number = number;
        this.displayName = displayName;
    }

    public int getNumber() {
        return number;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}