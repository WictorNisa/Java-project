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

//Things to do
//Check addneworder and why its acting funky monkey
// Customer phone number is null when viewing all customers
//

