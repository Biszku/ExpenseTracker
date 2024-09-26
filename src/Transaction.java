import java.time.LocalDate;

public class Transaction {

    private final int id;
    private String description;
    private double amount;
    private LocalDate date;

    public Transaction (int id, String description, double amount, LocalDate date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Transaction (int id, String description, double amount) {
        this(id, description, amount, LocalDate.now());
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void updateTransaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "%-4s %-12s %-12s %-6.2f\n".formatted(id, date, description, amount);
    }

    public String toFile() {
        return "%d,%s,%s,%.2f\n".formatted(id, date, description, amount);
    }
}
