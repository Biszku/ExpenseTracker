import Operations.Operation;

public class TaskTracker {

    private CommandHandler commandHandler;

    public TaskTracker() {
        commandHandler = new CommandHandler();
    }

    public void start() {

        while (true) {
            String operationType = commandHandler.readCommand();
            Operation operation = OperationFactory.createOperation(operationType);
            operation.execute();
        }
    }
}
