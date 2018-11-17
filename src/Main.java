public class Main {

    public static void main(String[] args) {
	// write your code here

        Bank bank1=new Bank(10000,1);
        Bank bank2=new Bank(10000,2);
        Bank bank3=new Bank(10000,3);
        Bank bank4=new Bank(10000,4);

        System.out.println("One thread: "+bank1.getTimeTaken());
        System.out.println("Two threads: "+bank2.getTimeTaken());
        System.out.println("Three threads: "+bank3.getTimeTaken());
        System.out.println("Four threads: "+bank4.getTimeTaken());

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
