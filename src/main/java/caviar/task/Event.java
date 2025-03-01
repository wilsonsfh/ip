package caviar.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import caviar.exception.CaviarException;

/**
 * Represents an event task with a start and end date/time.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructs an Event with the given description, start, and end times.
     *
     * @param description The event description.
     * @param from        The start date/time string.
     * @param to          The end date/time string.
     * @throws CaviarException If date/time parsing fails.
     */
    public Event(String description, String from, String to) throws CaviarException {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

    private LocalDateTime parseDateTime(String input) throws CaviarException {
        List<String> dateFormats = Arrays.asList(
            "d/M/yyyy HHmm",    // 2/12/2019 1800
            "yyyy-MM-dd HHmm",  // 2024-02-13 1800
            "yyyy-MM-dd HH:mm", // 2024-02-13 18:00
            "yyyy-MM-dd",       // 2024-02-13 (Default time 00:00)
            "MMM d yyyy h:mm a",// Dec 2 2019 6:00 PM
            "yyyy/MM/dd HH:mm"  // 2019/12/02 18:00
        );

        for (String format : dateFormats) {
            LocalDateTime dateTime = tryParseDateTime(input, format);
            if (dateTime != null) {
                return dateTime;
            }
        }

        LocalDate date = tryParseDate(input, "yyyy-MM-dd");
        if (date != null) {
            return date.atStartOfDay();
        }

        throw new CaviarException("roe..!! Please use a valid date format, roe..! Supported formats:\n"
            + " - d/M/yyyy HHmm (e.g., 2/12/2019 1800)\n"
            + " - yyyy-MM-dd HHmm (e.g., 2024-02-13 1800)\n"
            + " - yyyy-MM-dd (e.g., 2024-02-13) (Default time 00:00)\n"
            + " - MMM d yyyy h:mm a (e.g., Dec 2 2019 6:00 PM)\n"
            + " - yyyy/MM/dd HH:mm (e.g., 2019/12/02 18:00)");
    }

    private LocalDateTime tryParseDateTime(String input, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private LocalDate tryParseDate(String input, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
        return "[E]" + super.toString()
            + " (from: " + from.format(displayFormat)
            + " to: " + to.format(displayFormat) + ")";
    }

    @Override
    public String toStorageString() {
        String status = isDone ? "1" : "0";
        return "E | " + status + " | " + description + " | "
            + from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            + " | "
            + to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
