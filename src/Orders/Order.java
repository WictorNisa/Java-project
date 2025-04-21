package Orders;

import java.util.Date;

public class Order {
    private int order_id;
    private int customer_id;
    private Date date_time;


    public Order(int order_id, int customer_id, Date date_time) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.date_time = date_time;
    }

    public Order(int order_id) {
        this.order_id = order_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public java.sql.Date getDate_time() {
        return (java.sql.Date) date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }
}
