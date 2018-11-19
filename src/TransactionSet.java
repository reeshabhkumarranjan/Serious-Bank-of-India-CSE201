import java.util.ArrayList;

public final class TransactionSet implements Runnable {

    private volatile ArrayList<Transaction> transactions;

    public TransactionSet() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public void run() {

        for (Transaction transaction :
                transactions) {
            transaction.performTransaction();
        }
    }
}
