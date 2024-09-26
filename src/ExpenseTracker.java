import java.util.*;

public class ExpenseTracker {

    private static int id;
    private final String[] commands;
    private final List<Transaction> transactions;

    public ExpenseTracker(String[] commands) {
        id = 0;
        this.commands = commands;
        transactions = new ArrayList<>();
    }

    public void run() {

            try {
                if (commands.length == 0) {
                    throw new IllegalArgumentException("\u001B[31m" +
                            "No operation provided!" +
                            "\u001B[0m");
                }
                String operationType = commands[0];
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
            String description = commands[2].replaceAll("\"", "");
            double amount = Double.parseDouble(commands[4]);

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
            int id = Integer.parseInt(commands[2]);
            String description = commands[4].replaceAll("\"", "");
            double amount = Double.parseDouble(commands[6]);

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
                int id = Integer.parseInt(commands[2]);

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

        System.out.printf("# %-4s %-12s %-12s %-6s\n", "ID", "Date", "Description", "Amount");
    }

    private void printSummary() {
        System.out.println("Printing summary...");
    }
}