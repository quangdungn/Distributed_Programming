
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class BoundedBufferTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập N: ");
        int N = sc.nextInt();
        System.out.print("Nhập k: ");
        int k = sc.nextInt();
        System.out.print("Nhập h: ");
        int h = sc.nextInt();
        sc.close();

        BufferMonitor monitor = new BufferMonitor(N);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        for (int i = 1; i <= k; i++) {
            final int id = i;
            new Thread(() -> {
                Random rand = new Random();
                while (true) {
                    try {
                        Thread.sleep(rand.nextInt(1000));
                        int value = rand.nextInt(1000) + 1;
                        monitor.put(value);
                        String time = LocalTime.now().format(fmt);
                        System.out.printf("P%d: %d - %s%n", id, value, time);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break; 
                    }
                }
            }, "Producer-" + i).start();
        }

        for (int i = 1; i <= h; i++) {
            final int id = i;
            new Thread(() -> {
                Random rand = new Random();
                while (true) {
                    try {
                        Thread.sleep(rand.nextInt(1000));
                        int value = monitor.get();
                        boolean isPrime = checkPrime(value);
                        String result = isPrime ? "prime=true" : "prime=false";
                        String time = LocalTime.now().format(fmt);
                        System.out.printf("C%d: %d - %s - %s%n", id, value, result, time);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }, "Consumer-" + i).start();
        }
    }

    private static boolean checkPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        int r = (int) Math.sqrt(n);
        for (int i = 3; i <= r; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
