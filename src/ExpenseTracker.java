import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpenseTracker {

    private static int id;
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
                    default -> throw new IllegalArgumentException("\u001B[31mInvalid operation type!\n" +
                            "List of operations " +
                            "\"add\", \"update\", \"delete\", \"list\", \"summary\"\u001B[0m");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String readInput() {

        Scanner scanner = setInputSource(System.in);
        String input = scanner.nextLine();
        boolean isValid = validate("^expense-tracker.*", input);
        if (!isValid) {
            throw new IllegalArgumentException("\u001B[31mInvalid syntax!\n" +
                    "Enter command in format \"expense-tracker <operationType> <commandArgs>\"\u001B[0m");
        }
        input = removeBoundCommandElement(input);
        return input;
    }

    private Scanner setInputSource(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        return scanner;
    }

    private void addTransaction() {

        String description = retrieveParamValue("description");
        double amount = Double.parseDouble(retrieveParamValue("amount"));

        Transaction transaction = new Transaction(++id, description, amount);
        transactions.add(transaction);
    }

    private String retrieveParamValue(String param) {

        String suffix = param.equals("description") ? "\"\\w+\".*" : "\\d+$";
        String errorSuffix = param.equals("description") ? "<\"value\">\u001B[0m" : "<amount>\u001B[0m";
        boolean isParamValid = validate("^--" + param + "\\s" + suffix, command);

        if (!isParamValid) {
            throw new IllegalArgumentException("\u001B[31mInvalid "+ param +"!\n" +
                    "Enter "+param+" in format: --"+param+" "+errorSuffix);
        }
        command = command.replaceAll("--"+param+"\\s", "");
        return removeBoundCommandElement();
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