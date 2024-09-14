import Operations.*;

public class OperationFactory {

    public static Operation createOperation(String operationType) {
        System.out.println(operationType);

        return switch (operationType) {
            case "add" -> new AddOperation();
            case "update" -> new UpdateOperation();
            case "delete" -> new DeleteOperation();
            case "list" -> new PrintListOperation();
            case "summary" -> new PrintSummaryOperation();
            default -> throw new ErrorHandler("\u001B[31mInvalid operation! Enter: add, update, delete, list or summary\u001B[0m");
        };
    }
}
