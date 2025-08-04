import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class OrderProductsThread implements Runnable {
    private final int id;
    private final List<Order> orders;
    private final int orderProductsThreads;

    public OrderProductsThread(int id, List<Order> orders, int orderProductsThreads) {
        this.id = id;
        this.orders = orders;
        this.orderProductsThreads = orderProductsThreads;
    }

    @Override
    public void run() {
        int start = (int)(id * (double)orders.size() / orderProductsThreads);
        int end = (int)(Math.min((id + 1) * (double)orders.size() / orderProductsThreads, orders.size()));

        try {
            Scanner s = null;
            String line;
            String[] temp;
            for (int i = start; i < end; i++) {
                Order order = orders.get(i);
                s = new Scanner(Tema2.orderProductsIn);
                while (s.hasNextLine()) {
                    line = s.nextLine();
                    temp = line.split(",");
                    if (temp[0].equals(order.getOrderId())) {
                        synchronized (Tema2.class) {
                            Tema2.orderProductsOut.write(line + ",shipped\n");
                        }

                    }
                }
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tema2.levelTwoThreads.incrementAndGet();
    }
}
