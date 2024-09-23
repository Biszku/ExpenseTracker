import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        List<String> arguments = new ArrayList<>(List.of(args));
        ExpenseTracker expenseTracker = new ExpenseTracker(arguments);
        expenseTracker.run();
    }
}