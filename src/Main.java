public class Main {

    public static void main(String[] args) {
	// write your code here

        int accountCount=10000;
        Bank bank1=new Bank(accountCount,1);
        Bank bank2=new Bank(accountCount,2);
        Bank bank3=new Bank(accountCount,3);
        Bank bank4=new Bank(accountCount,4);

        System.out.println("One thread: "+bank1.getTimeTaken()+" seconds");
        System.out.println("Two threads: "+bank2.getTimeTaken()+" seconds");
        System.out.println("Three threads: "+bank3.getTimeTaken()+" seconds");
        System.out.println("Four threads: "+bank4.getTimeTaken()+" seconds");

        boolean cond1=bank1.getTimeTaken()>=bank2.getTimeTaken();
        boolean cond2=bank2.getTimeTaken()>=bank3.getTimeTaken();
        boolean cond3=bank3.getTimeTaken()>=bank4.getTimeTaken();

        if(cond1&&cond2&&cond3){
            System.out.println("Processing PASSED");
        }
        else{
            System.out.println("Processing FAILED");
        }
    }
}
