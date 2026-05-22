package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerAppTest {

    private List<SleepingSession> sessions;

    @BeforeEach
    void setUp() {
        sessions = new ArrayList<>();
    }

    private SleepingSession createSession(String start, String end, SleepQuality quality) {
        return new SleepingSession(
                LocalDateTime.parse(start),
                LocalDateTime.parse(end),
                quality
        );
    }

    @Test
    void testSumSleepSessionsShouldReturnCount() {
        SumSleepSessions sumSleepSessions = new SumSleepSessions();
        sessions.add(createSession("2024-01-01T22:00", "2024-01-02T06:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-02T22:00", "2024-01-03T06:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-03T22:00", "2024-01-04T06:00", SleepQuality.NORMAL));

        int result = sumSleepSessions.calculate(sessions);

        assertEquals(3, result);
    }

    @Test
    void testSumSleepSessionsShouldReturnZeroWhenEmpty() {
        SumSleepSessions sumSleepSessions = new SumSleepSessions();

        int result = sumSleepSessions.calculate(sessions);

        assertEquals(0, result);
    }

    @Test
    void testMinSleepSessionShouldFindMinimumDuration() {
        MinSleepSession minSleepSession = new MinSleepSession();
        sessions.add(createSession("2024-01-01T22:00", "2024-01-02T06:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-02T23:00", "2024-01-03T05:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-03T21:00", "2024-01-04T07:00", SleepQuality.NORMAL));

        int result = minSleepSession.calculate(sessions);

        assertEquals(360, result);
    }

    @Test
    void testMinSleepSessionShouldReturnZeroWhenEmpty() {
        MinSleepSession minSleepSession = new MinSleepSession();

        int result = minSleepSession.calculate(sessions);

        assertEquals(0, result);
    }

    @Test
    void testMaxSleepSessionShouldFindMaximumDuration() {
        MaxSleepSession maxSleepSession = new MaxSleepSession();
        sessions.add(createSession("2024-01-01T22:00", "2024-01-02T06:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-02T23:00", "2024-01-03T08:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-03T21:00", "2024-01-04T07:00", SleepQuality.NORMAL));

        int result = maxSleepSession.calculate(sessions);

        assertEquals(600, result);
    }

    @Test
    void testMaxSleepSessionShouldReturnZeroWhenEmpty() {
        MaxSleepSession maxSleepSession = new MaxSleepSession();

        int result = maxSleepSession.calculate(sessions);

        assertEquals(0, result);
    }

    @Test
    void testAverageSleepSessionShouldCalculateAverage() {
        AverageSleepSession averageSleepSession = new AverageSleepSession();
        sessions.add(createSession("2024-01-01T22:00", "2024-01-02T06:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-02T22:00", "2024-01-03T06:00", SleepQuality.NORMAL));

        int result = averageSleepSession.calculate(sessions);

        assertEquals(480, result);
    }

    @Test
    void testAverageSleepSessionShouldRoundAverage() {
        AverageSleepSession averageSleepSession = new AverageSleepSession();
        sessions.add(createSession("2024-01-01T22:00", "2024-01-02T06:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-02T23:00", "2024-01-03T07:05", SleepQuality.NORMAL));

        int result = averageSleepSession.calculate(sessions);

        assertEquals(483, result);
    }

    @Test
    void testBadSessionsCounterShouldCountBadQuality() {
        BadSessionsCounter badSessionsCounter = new BadSessionsCounter();
        sessions.add(createSession("2024-01-01T22:00", "2024-01-02T06:00", SleepQuality.GOOD));
        sessions.add(createSession("2024-01-02T22:00", "2024-01-03T06:00", SleepQuality.BAD));
        sessions.add(createSession("2024-01-03T22:00", "2024-01-04T06:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-04T22:00", "2024-01-05T06:00", SleepQuality.BAD));

        int result = badSessionsCounter.calculate(sessions);

        assertEquals(2, result);
    }

    @Test
    void testBadSessionsCounterShouldReturnZeroWhenNoBadSessions() {
        BadSessionsCounter badSessionsCounter = new BadSessionsCounter();
        sessions.add(createSession("2024-01-01T22:00", "2024-01-02T06:00", SleepQuality.GOOD));
        sessions.add(createSession("2024-01-02T22:00", "2024-01-03T06:00", SleepQuality.NORMAL));

        int result = badSessionsCounter.calculate(sessions);

        assertEquals(0, result);
    }

    @Test
    void testSleeplessNightsCounterShouldCountSleeplessNights() {
        SleeplessNightsCounter sleeplessNightsCounter = new SleeplessNightsCounter();
        sessions.add(createSession("2024-01-01T22:00", "2024-01-02T06:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-02T23:00", "2024-01-03T07:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-04T22:00", "2024-01-05T06:00", SleepQuality.NORMAL));

        int result = sleeplessNightsCounter.calculate(sessions);

        assertEquals(1, result);
    }

    @Test
    void testSleeplessNightsCounterShouldReturnZeroWhenAllNightsHaveSleep() {
        SleeplessNightsCounter sleeplessNightsCounter = new SleeplessNightsCounter();
        sessions.add(createSession("2024-01-01T22:00", "2024-01-02T06:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-02T23:00", "2024-01-03T07:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-03T22:30", "2024-01-04T06:30", SleepQuality.NORMAL));

        int result = sleeplessNightsCounter.calculate(sessions);

        assertEquals(0, result);
    }

    @Test
    void testSleeplessNightsCounterShouldCountMultipleSleeplessNights() {
        SleeplessNightsCounter sleeplessNightsCounter = new SleeplessNightsCounter();
        sessions.add(createSession("2024-01-01T22:00", "2024-01-02T06:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-04T22:00", "2024-01-05T06:00", SleepQuality.NORMAL));

        int result = sleeplessNightsCounter.calculate(sessions);

        assertEquals(2, result);
    }

    @Test
    void testSleeplessNightsCounterShouldReturnZeroWhenEmpty() {
        SleeplessNightsCounter sleeplessNightsCounter = new SleeplessNightsCounter();

        int result = sleeplessNightsCounter.calculate(sessions);

        assertEquals(0, result);
    }

    @Test
    void testSleeplessNightsCounterBoundaryValueMidnightStart() {
        SleeplessNightsCounter sleeplessNightsCounter = new SleeplessNightsCounter();
        sessions.add(createSession("2024-01-01T00:00", "2024-01-01T08:00", SleepQuality.NORMAL));

        int result = sleeplessNightsCounter.calculate(sessions);

        assertEquals(0, result);
    }

    @Test
    void testSleeplessNightsCounterShouldHandleCrossMidnightSession() {
        SleeplessNightsCounter sleeplessNightsCounter = new SleeplessNightsCounter();
        sessions.add(createSession("2024-01-01T23:00", "2024-01-02T07:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-02T23:30", "2024-01-03T06:30", SleepQuality.NORMAL));

        int result = sleeplessNightsCounter.calculate(sessions);

        assertEquals(0, result);
    }

    @Test
    void testChronotypeCounterShouldReturnOwl() {
        ChronotypeCounter chronotypeCounter = new ChronotypeCounter();
        sessions.add(createSession("2024-01-01T23:30", "2024-01-02T10:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-02T23:45", "2024-01-03T09:30", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-03T23:15", "2024-01-04T10:15", SleepQuality.NORMAL));

        int result = chronotypeCounter.calculate(sessions);

        assertEquals(1, result);
    }

    @Test
    void testChronotypeCounterShouldReturnLark() {
        ChronotypeCounter chronotypeCounter = new ChronotypeCounter();
        sessions.add(createSession("2024-01-01T21:30", "2024-01-02T06:30", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-02T21:45", "2024-01-03T06:15", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-03T21:15", "2024-01-04T06:45", SleepQuality.NORMAL));

        int result = chronotypeCounter.calculate(sessions);

        assertEquals(2, result);
    }

    @Test
    void testChronotypeCounterShouldReturnPigeon() {
        ChronotypeCounter chronotypeCounter = new ChronotypeCounter();
        sessions.add(createSession("2024-01-01T23:30", "2024-01-02T08:00", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-02T21:30", "2024-01-03T06:30", SleepQuality.NORMAL));
        sessions.add(createSession("2024-01-03T22:30", "2024-01-04T08:00", SleepQuality.NORMAL));

        int result = chronotypeCounter.calculate(sessions);

        assertEquals(3, result);
    }

    @Test
    void testChronotypeCounterShouldReturnZeroWhenNoData() {
        ChronotypeCounter chronotypeCounter = new ChronotypeCounter();

        int result = chronotypeCounter.calculate(sessions);

        assertEquals(0, result);
    }

    @Test
    void testChronotypeCounterShouldHandleCrossMidnight() {
        ChronotypeCounter chronotypeCounter = new ChronotypeCounter();
        sessions.add(createSession("2024-01-01T23:30", "2024-01-02T09:30", SleepQuality.NORMAL));

        int result = chronotypeCounter.calculate(sessions);

        assertEquals(1, result);
    }
}