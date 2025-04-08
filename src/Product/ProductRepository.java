package Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductRepository {

    public static final String URL = "jdbc:sqlite:webstore.db";

    public ArrayList<Product> getAll() throws SQLException {

        ArrayList<Product> products = new ArrayList<Product>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            while (rs.next()) {
                Product product = new Product(rs.getString("name"), rs.getDouble("price"));
                products.add(product);
            }
        }
        return products;
    }


    public Product getProduct(String productName) throws SQLException {
        Product product = null;
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM products WHERE LOWER(name) LIKE LOWER(?)")) {
            selectStmt.setString(1, "%" + productName.toLowerCase() + "%");
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                product = new Product(rs.getString("name"), rs.getDouble("price"));
                System.out.println(product.getName() + " " + product.getPrice() + "€");

            }
        }
        return product;
    }

    public ArrayList<Product> getProductsByCategory(String category) throws SQLException {
        ArrayList<Product> products = new ArrayList<Product>();
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement selectStmt = conn.prepareStatement("SELECT products.name AS productName, categories.name AS categoryName, price \n" +
                     "FROM products\n" +
                     "JOIN products_categories ON products.product_id = products_categories.product_id\n" +
                     "JOIN categories ON categories.category_id = products_categories.category_id\n" +
                     "WHERE LOWER (categories.name) = LOWER (?) ")) {
            selectStmt.setString(1, category.toLowerCase());
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getString("productName"), rs.getDouble("price"));
                products.add(product);

            }
        }
        return products;
    }

    public Product updateProductPrice(String name, double price) throws SQLException {
        Product updatedProduct = null;
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement updateStmt = conn.prepareStatement("UPDATE products SET price = ? WHERE LOWER(products.name) LIKE  LOWER(?)")) {
            updateStmt.setString(2, "%" + name.toLowerCase() + "%");
            updateStmt.setDouble(1, price);
            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected == 0) {
                return null;
            }
            try (PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM products WHERE LOWER(name) LIKE LOWER(?)")) {
                selectStmt.setString(1, "%" + name.toLowerCase() + "%");
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    updatedProduct = new Product(rs.getString("name"), rs.getDouble("price"));
                    System.out.println("The product: " + updatedProduct.getName() + " price was updated successfully");
                }
            }
            ;

        }
        return updatedProduct;
    }

    public Product UpdateProductStock(String productName, int stock) throws SQLException {
        Product updatedProduct = null;
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement updateStmt = conn.prepareStatement("UPDATE products\n" +
                     "SET stock_quantity = ?\n" +
                     "WHERE LOWER (products.name) = LOWER(?)")) {
            System.out.println("Updating product: " + productName + " with stock: " + stock);
            updateStmt.setInt(1, stock);
            updateStmt.setString(2, productName);
            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected == 0) {
                return null;
            }

            try (PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM products WHERE LOWER(name) = LOWER(?)")) {
                selectStmt.setString(1, productName.toLowerCase());
                ResultSet rs = selectStmt.executeQuery();
                System.out.println("Selected query executed");
                if (rs.next()) {
                    updatedProduct = new Product(rs.getString("name"), rs.getDouble("price"));
                    updatedProduct.setStock_quantity(rs.getInt("stock_quantity"));
                    System.out.println("Product created with stock: " + updatedProduct.getStock_quantity());
                }
            }

        }
        return updatedProduct;
    }


    public Product addNewProduct(String name, int manufacturer_id, double price, int quantity, String description) throws SQLException {
        Product newProduct = new Product(name, manufacturer_id, price, quantity, description);
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO products (manufacturer_id, name, description, price, stock_quantity) VALUES (?, ?, ?, ?, ?)")) {
            insertStmt.setInt(1, manufacturer_id);
            insertStmt.setString(2, name);
            insertStmt.setString(3, description);
            insertStmt.setDouble(4, price);
            insertStmt.setInt(5, quantity);
            int rowsAffected = insertStmt.executeUpdate();
            if (rowsAffected == 0) {
                return null;
            }

            try (PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM products WHERE LOWER(name) = LOWER(?)")) {
                selectStmt.setString(1, name.toLowerCase());
                ResultSet rs = selectStmt.executeQuery();
                System.out.println("Selected query executed");
                if (rs.next()) {
                    newProduct = new Product(
                            rs.getString("name"),
                            rs.getInt("manufacturer_id"),
                            rs.getDouble("price"),
                            rs.getInt("stock_quantity"),
                            rs.getString("description")
                    );
                    System.out.println("New product created! : " + newProduct.getName() + newProduct.getPrice() + newProduct.getStock_quantity() + newProduct.getDescription());

                }
            }


        }
        return newProduct;
    }

}
