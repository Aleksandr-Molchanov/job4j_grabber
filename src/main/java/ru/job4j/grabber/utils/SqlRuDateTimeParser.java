package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("d MM yy");

    private static final Map<String, String> MONTHS =
            Map.ofEntries(
                    Map.entry("янв", "01"),
                    Map.entry("фев", "02"),
                    Map.entry("мар", "03"),
                    Map.entry("апр", "04"),
                    Map.entry("май", "05"),
                    Map.entry("июн", "06"),
                    Map.entry("июл", "07"),
                    Map.entry("авг", "08"),
                    Map.entry("сен", "09"),
                    Map.entry("окт", "10"),
                    Map.entry("ноя", "11"),
                    Map.entry("дек", "12"),
                    Map.entry("сегодня", LocalDateTime.now().format(FORMATTER)),
                    Map.entry("вчера", LocalDateTime.now().minusDays(1).format(FORMATTER))
            );

    @Override
    public LocalDateTime parse(String parse) {
        String rsl = "";
        String[] dataTime = parse.split(",");
        String key = dataTime[0];
        if (key.equals("сегодня") || key.equals("вчера")) {
            rsl = MONTHS.get(key) + dataTime[1];
        }
        for (String k : MONTHS.keySet()) {
            if (key.contains(k)) {
                String[] data = key.split(" ");
                rsl = data[0]
                        + " " + MONTHS.get(k)
                        + " " + data[2]
                        + dataTime[1];
            }
            break;
        }
        return LocalDateTime.parse(rsl);
    }
}