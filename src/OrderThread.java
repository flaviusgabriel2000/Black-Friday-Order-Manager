import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderThread implements Runnable {
    private final int id;
    private final List<Order> orders;

    public OrderThread(int id) {
        this.id = id;
        this.orders = new ArrayList<>();
    }

    @Override
    public void run() {
        String line;
        String[] temp;
        while (true) {
            synchronized (Tema2.class) {
                if (!Tema2.ordersInScanner.hasNextLine()) {
                    break;
                }
                if (Tema2.turn.get() == id) {
                    line = Tema2.ordersInScanner.nextLine();
                    temp = line.split(",");
                    if (Integer.parseInt(temp[1]) > 0) {
                        orders.add(new Order(temp[0], Integer.parseInt(temp[1])));
                    }
                    Tema2.turn.set((id + 1) % Tema2.P);
                }
            }
        }

        int orderProductsThreads = 0;
        while (true) {
            synchronized (OrderThread.class) {
                if (Tema2.levelTwoThreads.get() > 0) {
                    Tema2.levelTwoThreads.decrementAndGet();
                    orderProductsThreads++;
                } else if (Tema2.levelTwoThreads.get() == 0 && orderProductsThreads > 0) {
                    break;
                }
            }
        }
        Thread[] threads = new Thread[orderProductsThreads];
        for (int i = 0; i < orderProductsThreads; i++) {
            threads[i] = new Thread(new OrderProductsThread(i, orders, orderProductsThreads));
            threads[i].start();
        }
        for (int i = 0; i < orderProductsThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Order o : orders) {
            synchronized (Tema2.class) {
                try {
                    Tema2.ordersOut.write(o.getOrderId() + "," + o.getCount() + ",shipped\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
