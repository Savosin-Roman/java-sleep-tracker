package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SleepTrackerLoader {

    public List<SleepingSession> readFromFile(String filename) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            return lines
                    .map(line -> line.split(";"))
                    .filter(parts -> parts.length == 3)
                    .map(parts -> new SleepingSession(
                            LocalDateTime.parse(parts[0].trim(), formatter),
                            LocalDateTime.parse(parts[1].trim(), formatter),
                            SleepQuality.valueOf(parts[2].trim())
                    ))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
