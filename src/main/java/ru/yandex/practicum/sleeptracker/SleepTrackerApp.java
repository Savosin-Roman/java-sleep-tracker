package ru.yandex.practicum.sleeptracker;

import java.util.ArrayList;
import java.util.List;

public class SleepTrackerApp {

    private static String sleepLog = "src\\main\\resources\\sleep_log.txt";

    public static void main(String[] args) {

        SleepTrackerLoader sleepTrackerLoader = new SleepTrackerLoader();
        List<SleepingSession> sleepingSessions = sleepTrackerLoader.readFromFile(sleepLog);

        List<Function> functions = new ArrayList<>();
        functions.add(new SumSleepSessions());
        functions.add(new MinSleepSession());
        functions.add(new MaxSleepSession());
        functions.add(new AverageSleepSession());
        functions.add(new BadSessionsCounter());
        functions.add(new SleeplessNightsCounter());
        functions.add(new ChronotypeCounter());

                List<SleepingSession> finalSessions = sleepingSessions;

        System.out.println("=== Статистика сна ===");
        functions.stream()
                .map(f -> new SleepAnalysisResult(f.getDescription(), f.calculate(finalSessions)))
                .forEach(SleepAnalysisResult::printResult);
    }
}