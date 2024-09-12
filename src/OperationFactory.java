import Operations.*;

public class OperationFactory {

    public static Operation createOperation(String operationType) {

        return switch (operationType) {
            case "add" -> new AddOperation();
            case "update" -> new UpdateOperation();
            case "delete" -> new DeleteOperation();
            case "list" -> new PrintListOperation();
            case "summary" -> new PrintSummaryOperation();
            default -> new ErrorOperation();
        };
    }
}
