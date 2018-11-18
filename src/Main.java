public class Main {

    public static int passed=0;
    public static int failed=0;

    public static void main(String[] args) {

//        Bank bank=new Bank(10,4,10);
//        bank.printActualAmounts();
//        bank.printExpectedAmounts();

        long startTime=System.currentTimeMillis();
        runAverageSimulation();
        long endTime=System.currentTimeMillis();

        double totalTime=endTime-startTime;
        double timeInSeconds=totalTime/1000;

        System.out.println("Time taken for benchmarking: "+timeInSeconds+" seconds");
    }
    
    public static void runAverageSimulation(){
        int accountCount=10000;
        int iterations=1;
        boolean isPassed=true;

        System.out.println("\nCalculating average performance. Please wait. (Takes about 20-30 seconds on Intel Core i5 7200U)\n");
        boolean cond1=averageSimulation(1,accountCount,iterations);
        boolean cond2=averageSimulation(2,accountCount,iterations);
        boolean cond3=averageSimulation(3,accountCount,iterations);
        boolean cond4=averageSimulation(4,accountCount,iterations);

        isPassed=cond1&&cond2&&cond3&&cond4;
        System.out.println();
        System.out.println(isPassed?"Processing PASSED":"Processing FAILED");
        System.out.println();
    }

    public static boolean averageSimulation(int threads, int accountCount, int iterations){

        double totalTime=0;
        boolean allPassed=true;

        for (int i = 0; i < iterations; i++) {
            Bank bank=new Bank(accountCount,threads,10000000);
            totalTime+=bank.getTimeTaken();
            allPassed=allPassed&&bank.isTransactionPassed();
        }

        double averageTime=  (totalTime/iterations);

        System.out.printf("%d Thread(s): %f seconds\n",threads,averageTime);

        return allPassed;
    }
}
