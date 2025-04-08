package Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {
    ProductRepository productRepository = new ProductRepository();


    public ArrayList<Product> getAllProducts() throws SQLException {
        return productRepository.getAll();
    }

    public Product getProductByName(String name) throws SQLException {
        return productRepository.getProduct(name);
    }

    public ArrayList<Product> getProductsByCategory(String category) throws SQLException {
        return productRepository.getProductsByCategory(category);
    }

    public Product updateProductsPrice(String name, double price) throws SQLException {
        return productRepository.updateProductPrice(name, price);
    }

    public Product updateProductsStock(String name, int stock) throws SQLException {
        return productRepository.UpdateProductStock(name, stock);
    }

    public Product addNewProduct(String name, int manufacturer_id, double price, int quantity, String description) throws SQLException {
        return productRepository.addNewProduct(name, manufacturer_id, price, quantity, description);
    }
}
