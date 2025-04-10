package Orders;

import Customer.Customer;
import Product.Product;

import java.sql.*;
import java.util.ArrayList;

public class OrderRepository {

    public static final String URL = "jdbc:sqlite:webstore.db";

    public Customer viewCustomersOrders(int customer_id) throws SQLException {
        Customer customer = null;
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement selectStmt = conn.prepareStatement(
                     "SELECT customers.customer_id, customers.name, customers.email, \n" +
                             "       orders.order_id, orders.customer_id AS order_customer_id, orders.order_date\n" +
                             "FROM orders\n" +
                             "JOIN customers ON customers.customer_id = orders.customer_id\n" +
                             "WHERE customers.customer_id = ?")) {
            selectStmt.setInt(1, customer_id);
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                if (customer == null) {
                    customer = new Customer(
                            rs.getInt("customer_id"),
                            rs.getString("name"),
                            rs.getString("email")
                    );
                }

                //For each row, this will create a new order and add it to the customer
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("order_customer_id"),
                        rs.getDate("order_date")
                );
                customer.addOrder(order);
            }
        }
        return customer;
    }

    public Order addNewOrder(Order order) throws SQLException {

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO orders (customer_id, order_date) VALUES (?, ?)")) {
            insertStmt.setInt(1, order.getCustomer_id());
            insertStmt.setDate(2, order.getDate_time());
            int rowsAffected = insertStmt.executeUpdate();

            if (rowsAffected > 0) {
                    //Retrive auto-generated key (order_id)
                try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        order.setOrder_id(generatedId); // Update the order object with the new ID
                    }
                }
                System.out.println("Order successfully added to database!");
                return order;
            } else {
                return null;
            }
        }

    }

    public boolean adddMultipleProductsToAnOrder(int order_id, Product product, int quantity) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO orders_products " +
                     "(order_id, product_id, quantity, unit_price) " +
                     "VALUES (?, ?, ?, ?)")) {
            insertStmt.setInt(1, order_id);
            insertStmt.setInt(2, product.getProduct_id());
            insertStmt.setInt(3, quantity);
            insertStmt.setDouble(4, product.getPrice());
            int rowsAffected = insertStmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
