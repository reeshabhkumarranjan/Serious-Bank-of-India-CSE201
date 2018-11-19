import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Bank {

    private final long startTime;
    private final long endTime;
    private final int accountCount;
    private int transactionCount;
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;
    private Random random;
    private LocalDateTime localDateTime;
    private ExecutorService executorService;
    private double timeTaken;
    private ArrayList<TransactionSet> transactionSets;
    private boolean transactionPassed;
    private int[] expected;
    private int threads;

    public Bank(int accountCount, int threads, int transactionCount) {

        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
        expected = new int[accountCount];
        random = new Random();
        executorService = Executors.newFixedThreadPool(threads);
        this.accountCount = accountCount;
        this.transactionSets = new ArrayList<>();
        this.transactionCount = transactionCount;
        this.threads=threads;

        buildAccounts();
        buildTransactions();
        buildTransactionSets();

        startTime = System.currentTimeMillis();
        executeTransactions();
        endTime = System.currentTimeMillis();

        timeTaken = endTime - startTime;
        timeTaken = (timeTaken / 1000);

        verifyTransactions();
    }

    private void verifyTransactions() {

        transactionPassed = true;

        for (int i = 0; i < accountCount; i++) {

            if (accounts.get(i).getAmount() != expected[i]) {
                transactionPassed = false;
                break;
            }
        }
    }

    private void buildTransactionSets() {

        TransactionSet currentTransactionSet = new TransactionSet();
        for (int i = 0; i < transactionCount; i++) {

            int numberOfTasks = threads*10;
            if (transactionCount < threads || i % (transactionCount / numberOfTasks) == 0) {
                transactionSets.add(new TransactionSet());
                currentTransactionSet = transactionSets.get(transactionSets.size() - 1);
            }

            currentTransactionSet.addTransaction(transactions.get(i));
        }
    }

    private void executeTransactions() {
        try {
            for (TransactionSet transactionSet : transactionSets) {
                executorService.execute(transactionSet);
            }

            if (!executorService.isTerminated()) {
                executorService.shutdown();
                executorService.awaitTermination(5L, TimeUnit.SECONDS);
            }
        } catch (InterruptedException ie) {
            System.err.println("Computation interrupted!");
        }
    }

    private void buildAccounts() {

        for (int i = 0; i < accountCount; i++) {

            int funds = randomFunds();
            addAccount(randomName(),funds);
            expected[i] = funds;
        }
    }

    public void addAccount(String name, int funds){
        accounts.add(new Account(name,funds));
    }

    private void buildTransactions() {

        for (int k = 0; k < transactionCount; k++) {
            int i = random.nextInt(accountCount);
            int j = random.nextInt(accountCount);
            int fundsTransfer = randomFundsTransfer();
            transactions.add(new Transaction(accounts.get(i), accounts.get(j), fundsTransfer));

            expected[i] -= fundsTransfer;
            expected[j] += fundsTransfer;
        }
    }

    private int randomFunds() {
        return 1000000;
    }

    private int randomFundsTransfer() {
        return random.nextInt(100);
    }

    private String randomName() {
        return "User" + (Account.getCount());
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public boolean isTransactionPassed() {
        return transactionPassed;
    }

    public void printActualAmounts() {

        for (int i = 0; i < accountCount; i++) {

            System.out.print(accounts.get(i).getAmount() + " ");
        }

        System.out.println();
    }

    public void printExpectedAmounts() {

        for (int i = 0; i < accountCount; i++) {

            System.out.print(expected[i] + " ");
        }

        System.out.println();
    }

    public void printTransactions() {

        for (Transaction transaction :
                transactions) {
            System.out.println(transaction);
        }
    }
}
