public class Account {

    private String holderName;
    private int amount;
    private final int id;
    private static int count=0;

    public Account(String holderName, int amount) {
        this.holderName = holderName;
        this.amount = amount;
        this.id=++count;
    }

    public String getHolderName() {
        return holderName;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public static int getCount() {
        return count;
    }

    public void debit(int delta) throws InsufficientFundsException{

        if(delta<amount){
            throw new InsufficientFundsException("Account No. "+id+" has insufficient funds.");
        }

        else{
            amount=amount-delta;
        }

    }

    public void credit(int delta){
        amount=amount+delta;
    }
}
