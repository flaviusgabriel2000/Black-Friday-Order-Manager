public class Order {
    private final String orderId;
    private final int count;

    public Order(String orderId, int count) {
        this.orderId = orderId;
        this.count = count;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getCount() {
        return count;
    }
}
