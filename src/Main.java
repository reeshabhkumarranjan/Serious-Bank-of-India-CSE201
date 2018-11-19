public class Main {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        runAverageSimulation();
        long endTime = System.currentTimeMillis();

        double totalTime = endTime - startTime;
        double timeInSeconds = totalTime / 1000;

        System.out.println("Time taken for benchmarking: " + timeInSeconds + " seconds");
    }

    public static void runAverageSimulation() {
        int accountCount = 10000;
        int iterations = 1;

        System.out.println("\nCalculating average performance. Please wait. (Takes about 20-30 seconds on Intel Core i5 7200U)\n");
        averageSimulation(1, accountCount, iterations);
        averageSimulation(2, accountCount, iterations);
        averageSimulation(3, accountCount, iterations);
        averageSimulation(4, accountCount, iterations);
    }

    public static void averageSimulation(int threads, int accountCount, int iterations) {

        double totalTime = 0;
        boolean allPassed = true;

        for (int i = 0; i < iterations; i++) {
            Bank bank = new Bank(accountCount, threads, 10000000);
            totalTime += bank.getTimeTaken();
            allPassed = allPassed && bank.isTransactionPassed();
        }

        double averageTime = (totalTime / iterations);

        System.out.printf("%d Thread(s): %f seconds\n", threads, averageTime);
        System.out.println(allPassed ? "Processing PASSED" : "Processing FAILED");
    }
}
