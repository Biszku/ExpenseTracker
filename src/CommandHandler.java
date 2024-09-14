import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandHandler {

    private static final Scanner scanner = new Scanner(System.in);
    private String command;

    public String readCommand() {

        command = scanner.nextLine();
        String emailRegex = "^expense-tracker \\S.*";
        boolean isCommandValid = validateInput(command, emailRegex);

        if (!isCommandValid) {
            throw new ErrorHandler("\u001B[31mInvalid command! Enter command in format \"expense-tracker <commandType> <commandArgs>\"\u001B[0m");
        }

        command = command.replaceAll("expense-tracker ", "");
        return command;
    }

    public static boolean validateInput(String input, String regex) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
