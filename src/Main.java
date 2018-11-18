public class Main {

    public static int passed=0;
    public static int failed=0;

    public static void main(String[] args) {

        runAverageSimulation();
    }
    
    public static void runAverageSimulation(){
        int accountCount=10000;
        int iterations=10;

        System.out.println("\nCalculating average performance. Please wait. (Takes about 20-30 seconds on Intel Core i5 7200U)\n");
        double oneThreadPerformance=averageSimulation(1,accountCount,iterations);
        double twoThreadPerformance=averageSimulation(2,accountCount,iterations);
        double threeThreadPerformance=averageSimulation(3,accountCount,iterations);
        double fourThreadPerformance=averageSimulation(4,accountCount,iterations);

        System.out.println("One thread: "+oneThreadPerformance+" seconds");
        System.out.println("Two threads: "+twoThreadPerformance+" seconds");
        System.out.println("Three threads: "+threeThreadPerformance+" seconds");
        System.out.println("Four threads: "+fourThreadPerformance+" seconds");
    }

    public static double averageSimulation(int threads, int accountCount, int iterations){

        double totalTime=0;

        for (int i = 0; i < iterations; i++) {
            Bank bank=new Bank(accountCount,threads);
            totalTime+=bank.getTimeTaken();
        }

        return (totalTime/iterations);
    }

    public static void runSimulation(){
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
            passed++;
        }
        else{
            System.out.println("Processing FAILED");
            failed++;
        }

        System.out.println("Success rate: "+((double)passed/(passed+failed)*100)+"\n");
    }
}
