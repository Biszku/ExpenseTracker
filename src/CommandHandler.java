import java.util.*;

public class CommandHandler {

    private static final Scanner scanner = new Scanner(System.in);
    Stack<String> commands = new Stack<>();

    public String readCommand() {

        List<String> commands = new ArrayList<>(List.of(scanner.nextLine().split(" ")));
        Collections.reverse(commands);

        for (String command : commands) {
            this.commands.push(command);

        }
        String mainCommand = this.commands.pop();

        if (!mainCommand.equals("expense-tracker")) {
            return "Unknown command";
        }
        return this.commands.pop();
    }

    public String getCommand() {
        return this.commands.pop();
    }
}
