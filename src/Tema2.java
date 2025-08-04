import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Tema2 {
    public static String inputFolder;
    public static int P;

    /**
     * Available level 2 threads
     */
    public static AtomicInteger levelTwoThreads;

    /**
     * Which level 1 thread should read the current order from the input file
     */
    public static AtomicInteger turn;

    public static File ordersIn;
    /**
     * The "orders.txt" file scanner shared by the level 1 threads
     */
    public static Scanner ordersInScanner;

    /**
     * The "orders_out.txt" file writer shared by the level 1 threads
     */
    public static FileWriter ordersOut;

    public static File orderProductsIn;
    /**
     * The "order_products_out.txt" file writer shared by the level 2 threads
     */
    public static FileWriter orderProductsOut;


    public static void main(String[] args) throws IOException {
        inputFolder = args[0];
        P = Integer.parseInt(args[1]);
        levelTwoThreads = new AtomicInteger(P);
        turn = new AtomicInteger(0);
        ordersIn = new File(inputFolder + "/orders.txt");
        ordersInScanner = new Scanner(ordersIn);
        ordersOut = new FileWriter("orders_out.txt");
        orderProductsIn = new File(inputFolder + "/order_products.txt");
        orderProductsOut = new FileWriter("order_products_out.txt");
        Thread[] threads = new Thread[P];

        for (int i = 0; i < P; i++) {
            threads[i] = new Thread(new OrderThread(i));
            threads[i].start();
        }
        for (int i = 0; i < P; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ordersInScanner.close();
        ordersOut.close();
        orderProductsOut.close();
    }
}
