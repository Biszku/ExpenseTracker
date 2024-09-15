import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private int id;
    private String date;
    private String description;
    private double amount;

    private static DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public Transaction(int id, String description, double amount) {
        this.id = id;
        date = LocalDateTime.now().format(formatter);
        System.out.println(date);
        this.description = description;
        this.amount = amount;
    }
}
