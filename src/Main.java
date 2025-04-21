import Customer.CustomerController;
import MainMenu.MainMenu;
import Orders.OrderController;
import Product.ProductController;


import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        MainMenu menu = new MainMenu();
        menu.mainMenu();
    }
}



//Things left to do
// Make sure that when an order is placed the stock quantity is reduced
// Double check naming and method names to make sure that things make sense
// Check that the program works and that errors are caught properly