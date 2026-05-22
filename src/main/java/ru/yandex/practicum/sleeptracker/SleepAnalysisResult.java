package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    private final String description;
    private final int value;

    public SleepAnalysisResult(String description, int value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public void printResult() {
        if (description.equals("Определение хронотипа")) {
            System.out.printf("%-40s: ", description);
            switch (value) {
                case 1:
                    System.out.println("Сова");
                    break;
                case 2:
                    System.out.println("Жаворонок");
                    break;
                case 3:
                    System.out.println("Голубь");
                    break;
                default:
                    System.out.println("Недостаточно данных");
            }
        } else {
            System.out.printf("%-40s: %d%n", description, value);
        }
    }
}