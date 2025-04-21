package Orders;

import Customer.Customer;
import Product.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderService {

    OrderRepository orderRepository = new OrderRepository();


    public Order getOrderId(int order_id) throws SQLException {
        return orderRepository.viewOrder(order_id);
    }

    public ArrayList<Product> getOrderProducts(int order_id) throws SQLException {
        return orderRepository.getOrderProducts(order_id);
    }

    public Customer getCustomerOrderHistory(int customer_id) throws SQLException {
        return orderRepository.viewCustomersOrders(customer_id);
    }

    public Order addNewOrder(Order order) throws SQLException {
        return orderRepository.addNewOrder(order);
    }

    public boolean addProductsToOrder(int order_id, Product product, int quantity) throws SQLException {
        return orderRepository.adddMultipleProductsToAnOrder(order_id, product, quantity);
    }


    public double getTotalPrice(int order_id) throws SQLException {
        return orderRepository.viewTotalOrderPrice(order_id);
    }
}
