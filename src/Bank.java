import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Bank {

    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;
    private Random random;
    private LocalDateTime localDateTime;
    private ExecutorService executorService;
    private final long startTime;
    private final long endTime;
    private double timeTaken;
    private final int accountCount;
    private ArrayList<TransactionSet> transactionSets;
    private final int transactionCount=1000000;

    public Bank(int accountCount, int threads){

        accounts=new ArrayList<>();
        transactions=new ArrayList<>();
        random=new Random();
        executorService=Executors.newFixedThreadPool(threads);
        this.accountCount=accountCount;
        this.transactionSets=new ArrayList<>();

        buildAccounts();
        buildTransactions();
        buildTransactionSets();

        startTime=System.currentTimeMillis();
        executeTransactions();
        endTime=System.currentTimeMillis();
        timeTaken=endTime-startTime;
        timeTaken=(timeTaken/1000);
    }

    private void buildTransactionSets() {

        TransactionSet currentTransactionSet=new TransactionSet();
        for (int i = 0; i < transactionCount; i++) {

            int numberOfTasks=100;

            if(i%(transactionCount/numberOfTasks)==0){
                transactionSets.add(new TransactionSet());
                currentTransactionSet=transactionSets.get(transactionSets.size()-1);
            }

            currentTransactionSet.addTransaction(transactions.get(i));
        }
    }

    private void executeTransactions(){
        try{
            for(TransactionSet transactionSet: transactionSets){
                executorService.execute(transactionSet);
            }

            if(!executorService.isTerminated()){
                executorService.shutdown();
                executorService.awaitTermination(5L, TimeUnit.SECONDS);
            }
        }

        catch (InterruptedException ie){
            System.err.println("Computation interrupted!");
        }
    }

    private void buildAccounts() {

        for (int i = 0; i < accountCount; i++) {
            accounts.add(new Account(randomName(),randomFunds()));
        }
    }

    private void buildTransactions(){

        for (int k = 0; k < 1000000; k++) {
            int i=random.nextInt(accountCount);
            int j=random.nextInt(accountCount);
            transactions.add(new Transaction(accounts.get(i),accounts.get(j),randomFundsTransfer()));
        }
    }

    private double randomFunds() {
        return (1000+9000*random.nextDouble());
    }

    private String randomName() {
        return "User"+(Account.getCount()+1);
    }

    private double randomFundsTransfer(){
        return (500+4000*random.nextDouble());
    }

    public double getTimeTaken(){
        return timeTaken;
    }
}
