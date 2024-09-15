import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpenseTracker {

    private static int id;
    private static final Scanner scanner = new Scanner(System.in);
    private String command;
    private final List<Transaction> transactions;

    public ExpenseTracker() {
        transactions = new ArrayList<>();
        id = 0;
    }

    public void start() {

        while (true) {

            try {

                command = readInput();
                String operationType = removeBoundCommandElement();
                switch (operationType) {
                    case "add" -> addTransaction();
                    case "update" -> updateTransaction();
                    case "delete" -> deleteTransaction();
                    case "list" -> printTransactions();
                    case "summary" -> printSummary();
                    default -> throw new IllegalArgumentException("\u001B[31mInvalid operation! " +
                            "Enter add, update, delete, list or summary\u001B[0m");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String readInput() {

        String input = scanner.nextLine();
        boolean isValid = validate("^expense-tracker\\s.*", input);
        if (!isValid) {
            throw new IllegalArgumentException("\u001B[31mInvalid syntax! " +
                    "Enter command in format \"expense-tracker <commandType> <commandArgs>\"\u001B[0m");
        }
        input = removeBoundCommandElement(input);
        return input;
    }

    private void addTransaction() {

        boolean isDescriptionValid = validate("^--description\\s\"\\w+\".*", command);
        if (!isDescriptionValid) {
            throw new IllegalArgumentException("\u001B[31mInvalid description! " +
                    "Enter description in format: --description \"<description>\"\u001B[0m");
        }
        command = command.replaceAll("--description\\s", "");
        String description = removeBoundCommandElement();

        boolean isAmountValid = validate("^--amount\\s\\d+$", command);
        if (!isAmountValid) {
            throw new IllegalArgumentException("\u001B[31mInvalid amount! " +
                    "Enter amount in format: --amount <amount>\u001B[0m");
        }
        command = command.replaceAll("--amount\\s", "");
        double amount = Double.parseDouble(removeBoundCommandElement());

        Transaction transaction = new Transaction(++id, description, amount);
        transactions.add(transaction);
    }

    public boolean validate(String regex, String text) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    private String removeBoundCommandElement() {

        String[] commands = command.split(" ");
        String boundCommandElement = commands[0];
        String boundaryOperation = commands.length < 2 ? "." : "^" + boundCommandElement + "\\s";
        command = command.replaceAll(boundaryOperation, "");
        return boundCommandElement;
    }

    private String removeBoundCommandElement(String command) {

        String boundCommandElement = command.split(" ")[0];
        return command.replaceAll(boundCommandElement + "\\s", "");
    }

    private void updateTransaction() {
        System.out.println("Updating transaction...");
    }

    private void deleteTransaction() {
        System.out.println("Deleting transaction...");
    }

    private void printTransactions() {
        System.out.println("Printing transactions...");
    }

    private void printSummary() {
        System.out.println("Printing summary...");
    }
}