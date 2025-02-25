package caviar.task;

import caviar.storage.Storage;
import caviar.task.Deadline;
import caviar.task.Task;
import caviar.task.Todo;
import caviar.command.TaskList;
import caviar.exception.CaviarException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, String from, String to) throws CaviarException {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

    /**
     * Parses date-time from multiple possible formats.
     */
    private LocalDateTime parseDateTime(String input) throws CaviarException {
        List<String> dateFormats = Arrays.asList(
                "d/M/yyyy HHmm",    // 2/12/2019 1800
                "yyyy-MM-dd HHmm",  // 2024-02-13 1800
                "yyyy-MM-dd HH:mm", // 2024-02-13 18:00
                "yyyy-MM-dd",       // 2024-02-13 (Default time 00:00)
                "MMM d yyyy h:mm a", // Dec 2 2019 6:00 PM
                "yyyy/MM/dd HH:mm"  // 2019/12/02 18:00
        );

        for (String format : dateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalDateTime.parse(input, formatter);
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

        throw new CaviarException("roe..!! Please use a valid date format, roe..! Supported formats:\n"
                + " - d/M/yyyy HHmm (e.g., 2/12/2019 1800)\n"
                + " - yyyy-MM-dd HHmm (e.g., 2024-02-13 1800)\n"
                + " - yyyy-MM-dd (e.g., 2024-02-13) (Default time 00:00)\n"
                + " - MMM d yyyy h:mm a (e.g., Dec 2 2019 6:00 PM)\n"
                + " - yyyy/MM/dd HH:mm (e.g., 2019/12/02 18:00)");
    }

    @Override
    public String toString() {
        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
        return "[E]" + super.toString() + " (from: " + from.format(displayFormat) + " to: " + to.format(displayFormat) + ")";
    }

    @Override
    public String toStorageString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " | "
                + to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
