public class TaskTracker {

    private CommandHandler commandHandler;

    public TaskTracker() {
        commandHandler = new CommandHandler();
    }

    public void start() {
        while (true) {
            System.out.println(commandHandler.readCommand());
        }
    }
}
