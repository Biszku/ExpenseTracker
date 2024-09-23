import java.time.LocalDate;

public class Transaction implements Comparable<Transaction> {

    private int id;
    private String description;
    private double amount;
    private LocalDate date;

    public Transaction (int id, String description, double amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = LocalDate.now();
    }

    public void updateTransaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Transaction o) {
        return id;
    }
}
