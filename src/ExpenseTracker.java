public class ExpenseTracker {

    public static void main(String[] args) {

        ExpenseController expenseController = new ExpenseController(args);
        expenseController.run();
    }
}