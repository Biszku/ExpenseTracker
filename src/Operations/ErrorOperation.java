package Operations;

public class ErrorOperation implements Operation {

    @Override
    public void execute() {
        System.out.println("Unknown command");
    }
}
