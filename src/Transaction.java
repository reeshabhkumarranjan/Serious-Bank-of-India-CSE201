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

//    public void performTransaction(){
//
//        try {
//            synchronized (sender){
//                sender.debit(fundTransfer);
//            }
//
//            synchronized (receiver){
//                receiver.credit(fundTransfer);
//            }
//            successful=true;
//        } catch (InsufficientFundsException e) {
//            successful=false;
//        }
//    }

    public void performTransaction(){
//        System.out.println("MAI GHADHA HU");
        try {

            Account first=null;
            Account second=null;

            if(sender.getId()>receiver.getId()){
//                Account temp=first;
//                first=second;
//                second=temp;
                first = sender;
                second = receiver;
            } else {
                first = receiver;
                second = sender;
            }

            synchronized (first){
                synchronized (second){

//                    System.out.println(sender.getAmount());
//                    System.out.println(fundTransfer);
                    if(sender.getAmount()>=fundTransfer){
//                        System.out.println("YUMMY");
                        sender.debit(fundTransfer);
                        receiver.credit(fundTransfer);
                        successful=true;
                    }
                    else{
                        successful=false;
                    }
                }
            }
        } catch (InsufficientFundsException e) {
            successful=false;
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
