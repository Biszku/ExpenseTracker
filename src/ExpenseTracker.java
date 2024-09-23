import java.util.*;

public class ExpenseTracker {

    private static int id;
    private final List<String> commands;
    private final List<Transaction> transactions;

    public ExpenseTracker(List<String> commands) {
        id = 0;
        this.commands = commands;
        transactions = new ArrayList<>();
    }

    public void run() {

            try {
                if (commands.isEmpty()) {
                    throw new IllegalArgumentException("\u001B[31m" +
                            "No operation provided!" +
                            "\u001B[0m");
                }
                String operationType = commands.remove(0);
                switch (operationType) {
                    case "add" -> addTransaction();
                    case "update" -> updateTransaction();
                    case "delete" -> deleteTransaction();
                    case "list" -> printTransactions();
                    case "summary" -> printSummary();
                    default -> throw new IllegalArgumentException("\u001B[31mInvalid operation type!\n" +
                            "List of operations " +
                            "\"add\", \"update\", \"delete\", \"list\", \"summary\"\u001B[0m");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
    }

    private void addTransaction() {

        try {
            String description = commands.get(1).replaceAll("\"", "");
            double amount = Double.parseDouble(commands.get(3));

            if (description.isEmpty() || amount < 0) {
                throw new IllegalArgumentException("\u001B[31m" +
                        "Invalid transaction data!" +
                        "\u001B[0m");
            }

            Transaction transaction = new Transaction(++id, description, amount);
            transactions.add(transaction);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("\u001B[31m" +
                    "Not enough arguments provided!" +
                    "\u001B[0m");
        }
    }

    private void updateTransaction() {

        try {
            int id = Integer.parseInt(commands.get(1));
            String description = commands.get(3).replaceAll("\"", "");
            double amount = Double.parseDouble(commands.get(5));

            if (description.isEmpty() || amount < 0) {
                throw new IllegalArgumentException("\u001B[31m" +
                        "Invalid transaction data!" +
                        "\u001B[0m");
            }

            Transaction transaction = findTransaction(id);
            if (transaction == null) {
                throw new IllegalArgumentException("\u001B[31m" +
                        "Transaction with id " + id + " not found!" +
                        "\u001B[0m");
            }
            transaction.updateTransaction(description, amount);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("\u001B[31m" +
                    "Not enough arguments provided!" +
                    "\u001B[0m");
        }
    }

    private void deleteTransaction() {

            try {
                int id = Integer.parseInt(commands.get(1));

                Transaction transaction = findTransaction(id);
                if (transaction == null) {
                    throw new IllegalArgumentException("\u001B[31m" +
                            "Transaction with id " + id + " not found!" +
                            "\u001B[0m");
                }
                transactions.remove(transaction);
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException("\u001B[31m" +
                        "Not enough arguments provided!" +
                        "\u001B[0m");
            }
    }

    private Transaction findTransaction(int id) {
        int left = 0;
        int right = transactions.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            Transaction transaction = transactions.get(mid);
            if (transaction.getId() == id) {
                return transaction;
            }
            if (id > transaction.getId()) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    private void printTransactions() {
        System.out.println("Printing transactions...");
    }

    private void printSummary() {
        System.out.println("Printing summary...");
    }
}