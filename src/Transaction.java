public class Transaction {

    private volatile Account sender;
    private volatile Account receiver;
    private int fundTransfer;
    private boolean successful;

    public Transaction(Account sender, Account receiver, int fundTransfer) {
        this.sender = sender;
        this.receiver = receiver;
        this.fundTransfer=fundTransfer;
    }

    public void performTransaction(){

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
}
