public class Transaction {

    private volatile Account sender;
    private volatile Account receiver;
    private int fundTransfer;
    private boolean successful;

    public Transaction(Account sender, Account receiver, int fundTransfer) {
        this.sender = sender;
        this.receiver = receiver;
        this.fundTransfer = fundTransfer;
    }

    public void performTransaction() {
        try {

            Account first = sender;
            Account second = receiver;

            if (first.getId() > second.getId()) {
                Account temp = first;
                first = second;
                second = temp;
            }


            synchronized (first) {
                synchronized (second) {

                    if (sender.getAmount() >= fundTransfer) {
                        sender.debit(fundTransfer);
                        receiver.credit(fundTransfer);
                        successful = true;
                    } else {
                        successful = false;
                    }
                }
            }
        } catch (InsufficientFundsException e) {
            successful = false;
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", fundTransfer=" + fundTransfer +
                '}';
    }
}
