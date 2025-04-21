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

    public Order viewOrder(int order_id) throws SQLException {
        Order order = null;
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM orders WHERE order_id = ?")) {
            selectStmt.setInt(1, order_id);
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("order_date")
                );
            }
        }
        return order;
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
        try (Connection conn = DriverManager.getConnection(URL)) {
            System.out.println("Adding product: " + product.getName() + " (ID: " + product.getProduct_id() + ") to order " + order_id);

            // First add product to order
            try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO orders_products " +
                    "(order_id, product_id, quantity, unit_price) " +
                    "VALUES (?, ?, ?, ?)")) {
                insertStmt.setInt(1, order_id);
                insertStmt.setInt(2, product.getProduct_id());
                insertStmt.setInt(3, quantity);
                insertStmt.setDouble(4, product.getPrice());

                System.out.println("Executing insert with values: " + order_id + ", " + product.getProduct_id() + ", " + quantity + ", " + product.getPrice());

                int rowsAffected = insertStmt.executeUpdate();
                System.out.println("Insert affected " + rowsAffected + " rows");

                if (rowsAffected <= 0) {
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Error during insert: " + e.getMessage());
                throw e;
            }

            // Then update product stock
            try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE products " +
                    "SET stock_quantity = stock_quantity - ? " +
                    "WHERE product_id = ?")) {
                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, product.getProduct_id());

                System.out.println("Executing update with values: " + quantity + ", " + product.getProduct_id());

                int updateRows = updateStmt.executeUpdate();
                System.out.println("Update affected " + updateRows + " rows");

                return updateRows > 0;
            } catch (SQLException e) {
                System.out.println("Error during update: " + e.getMessage());
                throw e;
            }
        }
    }

    public double viewTotalOrderPrice(int order_id) throws SQLException {
        double totalPrice = 0.0;
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement selectStmt = conn.prepareStatement("SELECT *\n" +
                     "FROM orders_products\n" +
                     "JOIN products ON products.product_id = orders_products.order_product_id\n" +
                     "WHERE order_id = ?")) {
            selectStmt.setInt(1, order_id);
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                totalPrice += rs.getDouble("price") * rs.getInt("quantity");
            }
        }
        return totalPrice;
    }

    public ArrayList<Product> getOrderProducts(int order_id) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement selectStmt = conn.prepareStatement("SELECT *\n" +
                     "FROM orders_products\n" +
                     "JOIN products ON products.product_id = orders_products.order_product_id\n" +
                     "WHERE order_id = ?")) {
            selectStmt.setInt(1, order_id);
            ResultSet rs = selectStmt.executeQuery();
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                System.out.println("Found product: " + rs.getString("name") + ", quantity: " + rs.getInt("quantity"));
                Product product = new Product(
                        rs.getString("name"),
                        rs.getInt("manufacturer_id"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"),
                        rs.getString("description")
                );
                System.out.println("Total products found: " + rowCount);
                product.setQuantity(rs.getInt("quantity"));
                products.add(product);
            }
        }
        return products;
    }

}
