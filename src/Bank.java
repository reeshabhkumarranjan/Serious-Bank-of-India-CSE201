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
    private final int transactionCount;
    private boolean transactionPassed;
    private int[] expected;

    private void verifyTransactions(){

        transactionPassed=true;

        for (int i = 0; i < accountCount; i++) {

            if(accounts.get(i).getAmount()!=expected[i]){
                transactionPassed=false;
                break;
            }
        }
    }

    public Bank(int accountCount, int threads, int transactionCount){

        accounts=new ArrayList<>();
        transactions=new ArrayList<>();
        expected=new int[accountCount];
        random=new Random();
        executorService=Executors.newFixedThreadPool(threads);
        this.accountCount=accountCount;
        this.transactionSets=new ArrayList<>();
        this.transactionCount=transactionCount;

        buildAccounts();
        buildTransactions();
        buildTransactionSets();

        startTime=System.currentTimeMillis();
        executeTransactions();
        endTime=System.currentTimeMillis();

        timeTaken=endTime-startTime;
        timeTaken=(timeTaken/1000);

        verifyTransactions();
    }

    private void buildTransactionSets() {

        TransactionSet currentTransactionSet=new TransactionSet();
        for (int i = 0; i < transactionCount; i++) {

            // int numberOfTasks=transactionCount<=1000?(int)Math.sqrt(transactionCount):1000;
            int numberOfTasks=100;
//            int numberOfTasks=(int)Math.sqrt(transactionCount);
            if(transactionCount<100 || i%(transactionCount/numberOfTasks)==0){
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

            int funds=randomFunds();
            accounts.add(new Account(randomName(),funds));
            expected[i]=funds;
        }
    }

    private void buildTransactions(){

        for (int k = 0; k < transactionCount; k++) {
            int i=random.nextInt(accountCount);
            int j=random.nextInt(accountCount);
            int fundsTransfer=randomFundsTransfer();
            transactions.add(new Transaction(accounts.get(i),accounts.get(j),fundsTransfer));

            expected[i]-=fundsTransfer;
            expected[j]+=fundsTransfer;
        }
    }

//    private int randomFunds() {
//        return (10000+90000*random.nextDouble());
//    }

    private int randomFunds(){
        //return (10000+random.nextInt(90000));
        return 10000;
    }

    private int randomFundsTransfer(){
        return random.nextInt(10);
    }

    private String randomName() {
        return "User"+(Account.getCount());
    }

//    private int randomFundsTransfer(){
//        return (400*random.nextDouble());
//    }

    public double getTimeTaken(){
        return timeTaken;
    }

    public boolean isTransactionPassed(){
        return transactionPassed;
    }

    public void printActualAmounts(){

        for (int i = 0; i < accountCount; i++) {

            System.out.print(accounts.get(i).getAmount()+" ");
        }

        System.out.println();
    }

    public void printExpectedAmounts(){

        for (int i = 0; i < accountCount; i++) {

            System.out.print(expected[i]+" ");
        }

        System.out.println();
    }

    public void printTransactions(){

        for (Transaction transaction :
                transactions) {
            System.out.println(transaction);
        }
    }
}
