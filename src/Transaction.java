import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Transaction (int id, String date, String description, double amount) {

    private static DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public Transaction (int id, String description, double amount) {
        this(id, LocalDateTime.now().format(formatter), description, amount);
    }
}
