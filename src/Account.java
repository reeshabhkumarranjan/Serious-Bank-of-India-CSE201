public class Account {

    private static int count = 0;
    private final int id;
    private String holderName;
    private int amount;

    public Account(String holderName, int amount) {
        this.holderName = holderName;
        this.amount = amount;
        this.id = count++;
    }

    public static int getCount() {
        return count;
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

    public void debit(int delta) throws InsufficientFundsException {

        if (delta > amount) {
            throw new InsufficientFundsException("Account No. " + id + " has insufficient funds.");
        } else {
            amount = amount - delta;
        }

    }

    public void credit(int delta) {
        amount = amount + delta;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                '}';
    }
}
