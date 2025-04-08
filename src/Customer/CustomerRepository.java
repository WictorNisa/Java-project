package Customer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerRepository {

    public static final String URL = "jdbc:sqlite:webstore.db";

    public ArrayList<Customer> getAll() throws SQLException {

        ArrayList<Customer> customers = new ArrayList<Customer>();
        System.out.println("Detta är metoden för att hämta alla customers getAll()");
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

            while (rs.next()) {

                customers.add(new Customer(rs.getInt("customer_id"), rs.getString("name"), rs.getString("email")));
                System.out.println(rs.getInt("customer_id"));
                System.out.println(rs.getString("name"));
                System.out.println(rs.getString("email"));
                System.out.println(rs.getString("phone"));
                System.out.println(rs.getString("address"));
                System.out.println(rs.getString("password"));
            }
        }
        return customers;
    }


    public Customer getCustomerById(int id) throws SQLException {

        ArrayList<Customer> customers = new ArrayList<Customer>();
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers WHERE customer_id = " + id)) {

            while (rs.next()) {

                customers.add(new Customer(rs.getInt("customer_id"), rs.getString("name"), rs.getString("email")));
                System.out.println(rs.getInt("customer_id"));
                System.out.println(rs.getString("name"));
                System.out.println(rs.getString("email"));
                System.out.println(rs.getString("phone"));
                System.out.println(rs.getString("address"));
                System.out.println(rs.getString("password"));
            }
        }
        return customers.getFirst();
    }

    public Customer updateCustomersEmail(String email, int id) throws SQLException {
        Customer updatedCustomer = null;
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement updateStmt = conn.prepareStatement("UPDATE customers SET email = ? WHERE customer_id = ?")) {
            updateStmt.setString(1, email);
            updateStmt.setInt(2, id);

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected == 0) {
                return null;
            }

            try (PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM customers WHERE customer_id = ?")) {
                selectStmt.setInt(1, id);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        updatedCustomer = new Customer(rs.getInt("customer_id"), rs.getString("name"), rs.getString("email"));
                        System.out.println("Success! Your email was updated!");
                    }
                }
            }

        }
        return updatedCustomer;
    }


    public Customer AddCustomer(Customer customer) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement addStmt = conn.prepareStatement("INSERT INTO customers(name, email, phone, address, password) VALUES(?, ?, ?, ?, ?)")) {
            addStmt.setString(1, customer.getName());
            addStmt.setString(2, customer.getEmail());
            addStmt.setString(3, customer.getPhone());
            addStmt.setString(4, customer.getAddress());
            addStmt.setString(5, customer.getPassword());
            int rowsAffected = addStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Success! Customer was added!");
            }
        }
        return customer;
    }


}

