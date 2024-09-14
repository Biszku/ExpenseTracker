import Operations.Operation;

public class ExpenseTracker {

    private final CommandHandler commandHandler;

    public ExpenseTracker() {
        commandHandler = new CommandHandler();
    }

    public void start() {

        while (true) {
            try {
                String operationType = commandHandler.readCommand();
                Operation operation = OperationFactory.createOperation(operationType);
                operation.execute(operationType);
            } catch (ErrorHandler e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
