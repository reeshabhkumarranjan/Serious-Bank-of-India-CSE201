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
    private final long timeTaken;
    private final int accountCount;
    private double[] expected;

    public Bank(int accountCount){

        accounts=new ArrayList<>();
        transactions=new ArrayList<>();
        expected=new double[accountCount];
        random=new Random();
        executorService=Executors.newFixedThreadPool(10);
        this.accountCount=accountCount;

        buildAccounts();
        buildTransactions();

        System.out.println("Starting simulation.");
        startTime=System.currentTimeMillis();
        executeTransactions();
        endTime=System.currentTimeMillis();
        System.out.println("Simulation ended.");
        timeTaken=endTime-startTime;

        System.out.println("Time taken (seconds): "+((double)timeTaken/1000));
    }

    private void executeTransactions(){
        try{
            for(Transaction transaction: transactions){
                executorService.execute(transaction);
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

        for (int i = 0; i < accountCount; i++) {

            for (int j = 0; j < accountCount; j++) {

                if(i!=j){
                    transactions.add(new Transaction(accounts.get(i),accounts.get(j), randomFundsTransfer()));
                }
            }
        }
    }

    private double randomFunds() {

        return (1000+9000*random.nextDouble());
    }

    private String randomName() {

//        return LocalDateTime.now().toString();
        return "User"+(Account.getCount()+1);
    }

    private double randomFundsTransfer(){

        return (1000+4000*random.nextDouble());
    }
}
