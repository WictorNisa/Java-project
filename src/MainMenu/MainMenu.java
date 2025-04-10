package MainMenu;

import Customer.CustomerController;
import Orders.OrderController;
import Product.ProductController;

import java.sql.SQLException;

public class MainMenu {

    public void mainMenu() throws SQLException {
        OrderController orderController = new OrderController();
        CustomerController customerController = new CustomerController();
        ProductController productController = new ProductController();

    }
}
