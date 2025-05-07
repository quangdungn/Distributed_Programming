import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int[] A;

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int N, K;

        while (true) {
            System.out.print("Nhập số phần tử mảng N (> 0): ");
            N = scanner.nextInt();

            System.out.print("Nhập số luồng K (> 0 và ≤ N): ");
            K = scanner.nextInt();

            if (N > 0 && K > 0 && K <= N) break;

            System.out.println("Giá trị không hợp lệ! Vui lòng nhập lại.\n");
        }

        A = new int[N];
        Random random = new Random();
        System.out.println("\n>>> Mảng A: ");
        for (int i = 0; i < N; i++) {
            A[i] = random.nextInt(10) + 1;
            System.out.print(A[i] + " ");
        }
        System.out.println("\n\n");
        A[N - 1] = 3;

        PrimeCounterThread[] threads = new PrimeCounterThread[K];
        int segmentSize = N / K;
        int remainder = N % K;

        int currentStart = 0;
        for (int i = 0; i < K; i++) {
            int currentEnd = currentStart + segmentSize + (i < remainder ? 1 : 0);
            threads[i] = new PrimeCounterThread(i + 1, currentStart, currentEnd);
            threads[i].start();
            currentStart = currentEnd;
        }

        int totalPrimeCount = 0;
        for (int i = 0; i < K; i++) {
            threads[i].join();
            totalPrimeCount += threads[i].getPrimeCount();
        }

        System.out.println("\n>>> Tổng số số nguyên tố trong mảng: " + totalPrimeCount);
    }
}
