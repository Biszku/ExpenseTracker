import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHandler implements Observer {

    private final Path fileName;
    ExpenseController expenseController;

    public FileHandler(String fileName, ExpenseController expenseController) {
        this.fileName = Path.of(fileName);
        createFile();
        this.expenseController = expenseController;
        expenseController.addObserver(this);
    }

    private void createFile() {
        try {
            if (!Files.exists(fileName)) Files.createFile(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToFile() {

        try {
            Files.write(fileName, "".getBytes());
            List<Transaction> transactions = expenseController.getTransactions();
            for (Transaction transaction : transactions) {
                Files.write(fileName, transaction.toFile().getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> readFromFile() {
        List<Transaction> transactions = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(fileName);
            for (String line : lines) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                LocalDate date = LocalDate.parse(parts[1]);
                String description = parts[2];
                double amount = Double.parseDouble(parts[3]);
                Transaction transaction = new Transaction(id, description, amount, date);
                transactions.add(transaction);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public void update() {
         saveToFile();
    }
}
