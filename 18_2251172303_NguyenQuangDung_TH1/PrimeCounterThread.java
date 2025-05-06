import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PrimeCounterThread extends Thread {
    private int id, start, end, primeCount;

    public PrimeCounterThread(int id, int start, int end) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.primeCount = 0;
    }

    @Override
    public void run() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH'h'mm'm'ss's'SSS");
        String formattedTime = LocalTime.now().format(formatter);

        for (int i = start; i < end; i++) {
            if (isPrime(Main.A[i])) {
                primeCount++;
                System.out.println("T" + id + ": " + Main.A[i] + " : " + formattedTime);
            }
        }// System.out.println("T" + id + ": " + primeCount + " : " + formattedTime);
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(n); i += 2)
            if (n % i == 0) return false;
        return true;
    }

    public int getPrimeCount() {
        return primeCount;
    }
}
