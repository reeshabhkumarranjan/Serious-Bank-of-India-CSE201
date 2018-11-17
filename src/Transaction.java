public class Transaction {

    private Account sender;
    private Account receiver;
    private double fundTransfer;
    private boolean successful;

    public Transaction(Account sender, Account receiver, double fundTransfer) {
        this.sender = sender;
        this.receiver = receiver;
        this.fundTransfer=fundTransfer;
    }

    public void performTransaction(){

        try {

            sender.debit(fundTransfer);
            receiver.credit(fundTransfer);
            successful=true;
        } catch (InsufficientFundsException e) {
            successful=false;
        }
    }
}
