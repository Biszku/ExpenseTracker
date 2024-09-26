import java.util.*;

public class ExpenseTracker implements Observable {

    private final String[] commands;

    private final List<Observer> observers;

    private static int idCounter;
    private final List<Transaction> transactions;


    public ExpenseTracker(String[] commands) {
        this.commands = commands;

        observers = new ArrayList<>();

        FileHandler fileHandler = new FileHandler("Expenses.csv", this);
        transactions = fileHandler.readFromFile();
        idCounter = !transactions.isEmpty() ? transactions.get(transactions.size() - 1).getId() : 0;
    }

    public List<Transaction> getTransactions() {
        return transactions;
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

            Transaction transaction = new Transaction(++idCounter, description, amount);
            transactions.add(transaction);
            System.out.printf("Expense added successfully (ID: %d)\n", idCounter);
            notifyObservers();
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("\u001B[31m" +
                    "Not enough arguments provided!" +
                    "\u001B[0m");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("\u001B[31m" +
                    "Invalid argument provided!" +
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
            System.out.printf("Expense updated successfully (ID: %d)\n", id);
            notifyObservers();
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("\u001B[31m" +
                    "Not enough arguments provided!" +
                    "\u001B[0m");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("\u001B[31m" +
                    "Invalid argument provided!" +
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
            System.out.printf("Expense deleted successfully (ID: %d)\n", id);
            notifyObservers();
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("\u001B[31m" +
                    "Not enough arguments provided!" +
                    "\u001B[0m");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("\u001B[31m" +
                    "Invalid argument provided!" +
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

        if (transactions.isEmpty()) {
            System.out.println("No transactions found!");
            return;
        }
        System.out.printf("%-4s %-12s %-12s %-6s\n", "ID", "Date", "Description", "Amount");
        transactions.forEach(System.out::print);
    }

    private void printSummary() {

        if (transactions.isEmpty()) {
            System.out.println("No transactions found!");
            return;
        }

        double total = transactions.stream().mapToDouble(Transaction::getAmount).sum();

        try {
            if (commands.length > 1) {
            int month = Integer.parseInt(commands[2]);

            if (month < 1 || month > 12) {
                throw new IllegalArgumentException("\u001B[31m" +
                        "Invalid month provided!" +
                        "\u001B[0m");
            }

            total = total - transactions.stream()
                    .filter(transaction -> transaction.getDate().getMonthValue() != month)
                    .mapToDouble(Transaction::getAmount)
                    .sum();
            }
            System.out.printf("Total expenses: $%.2f\n", total);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("\u001B[31m" +
                    "Invalid argument provided!" +
                    "\u001B[0m");
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}