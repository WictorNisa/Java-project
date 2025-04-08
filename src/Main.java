import Customer.CustomerController;
import Product.ProductController;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        //CustomerController customerController = new CustomerController();
        //customerController.runMenu();

        ProductController productController = new ProductController();
        productController.runMenu();
    }
}