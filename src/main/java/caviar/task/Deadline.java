package caviar.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import caviar.exception.CaviarException;

/**
 * Represents a deadline task with a specific due date/time.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Constructs a Deadline with the given description and due date/time.
     *
     * @param description The task description.
     * @param by          The due date/time string.
     * @throws CaviarException If the date/time cannot be parsed.
     */
    public Deadline(String description, String by) throws CaviarException {
        super(description);
        this.by = parseDateTime(by);
    }

    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Parses date-time from multiple possible formats.
     *
     * @param input The date-time string provided by the user.
     * @return LocalDateTime representing the parsed date-time.
     * @throws CaviarException if the input does not match any valid formats.
     */
    private LocalDateTime parseDateTime(String input) throws CaviarException {
        List<String> dateFormats = Arrays.asList("d/M/yyyy HHmm",    // 2/12/2019 1800
            "yyyy-MM-dd HHmm",  // 2024-02-13 1800
            "yyyy-MM-dd HH:mm", // 2024-02-13 18:00
            "yyyy-MM-dd",       // 2024-02-13 (Default time 00:00)
            "MMM d yyyy h:mm a", // Dec 2 2019 6:00 PM
            "yyyy/MM/dd HH:mm"  // 2019/12/02 18:00
        );

        for (String format : dateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
                return dateTime;
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }

        try {
            LocalDate date = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return date.atStartOfDay(); // Default time is 00:00
        } catch (DateTimeParseException ignored) {
            // Do nothing, throw an error below
        }

        throw new CaviarException("Please use a valid date format, roe..! Supported formats:\n"
            + " - d/M/yyyy HHmm (e.g., 2/12/2019 1800)\n" + " - yyyy-MM-dd HHmm (e.g., 2024-02-13 1800)\n"
            + " - yyyy-MM-dd (e.g., 2024-02-13) (Default time 00:00)\n"
            + " - MMM d yyyy h:mm a (e.g., Dec 2 2019 6:00 PM)\n" + " - yyyy/MM/dd HH:mm (e.g., 2019/12/02 18:00)");
    }

    @Override
    public String toString() {
        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
        return "[D]" + super.toString() + " (by: " + by.format(displayFormat) + ")";
    }

    @Override
    public String toStorageString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | "
            + by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
