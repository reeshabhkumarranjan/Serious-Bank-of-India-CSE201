public class Transaction implements Runnable{

    private volatile Account sender;
    private volatile Account receiver;
    private double fundTransfer;
    private boolean successful;

    public Transaction(Account sender, Account receiver, double fundTransfer) {
        this.sender = sender;
        this.receiver = receiver;
        this.fundTransfer=fundTransfer;
    }

    private void performTransaction(){

        try {
            synchronized (this){
                sender.debit(fundTransfer);
                receiver.credit(fundTransfer);
            }
            successful=true;
        } catch (InsufficientFundsException e) {
            successful=false;
        }
    }

    @Override
    public void run() {
        performTransaction();
    }
}
