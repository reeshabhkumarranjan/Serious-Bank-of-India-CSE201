public class Account {

    private String holderName;
    private double amount;
    private final int id;
    private static int count=0;

    public Account(String holderName, double amount) {
        this.holderName = holderName;
        this.amount = amount;
        this.id=++count;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public static int getCount() {
        return count;
    }

    public void debit(double delta) throws InsufficientFundsException{

        if(delta<amount){
            throw new InsufficientFundsException("Account No. "+id+" has insufficient funds.");
        }

        else{
            amount=amount-delta;
        }

    }

    public void credit(double delta){
        amount=amount+delta;
    }
}
