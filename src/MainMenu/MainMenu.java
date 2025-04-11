package MainMenu;

import Customer.CustomerController;
import Orders.OrderController;
import Product.ProductController;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner;
    private final CustomerController customerController;
    private final ProductController productController;
    private final OrderController orderController;


    public MainMenu() {
        scanner = new Scanner(System.in);
        customerController = new CustomerController();
        productController = new ProductController();
        orderController = new OrderController();
    }

    public void mainMenu() throws SQLException {
        boolean running = true;


        while (running) {
            clearScreen();
            displayHeader();
            displayOptions();
            System.out.print("\nEnter your choice (or 'q' to quit): ");
            String select = scanner.nextLine().trim();
            switch (select.toLowerCase()) {
                case "1":
                    customerController.runMenu();
                    break;
                case "2":
                    productController.runMenu();
                    break;
                case "3":
                    orderController.runMenu();
                    break;
                case "q":
                case "exit":
                case "quit":
                    running = false;
                    System.out.println("\nThank you for using the Webstore Management System. Goodbye!");
                    break;
                default:
                    System.out.println("\n⚠️ Invalid selection. Please try again.");
                    waitForEnter();
            }
        }
    }

    private void displayHeader() {
        System.out.println("+" + "-".repeat(50) + "+");
        System.out.println("|" + " ".repeat(15) + "WEBSTORE MANAGEMENT SYSTEM" + " ".repeat(15) + "|");
        System.out.println("+" + "-".repeat(50) + "+");
        System.out.println();
    }

    private void displayOptions() {
        System.out.println("MAIN MENU");
        System.out.println("-".repeat(30));
        System.out.println("1. Customer Management");
        System.out.println("2. Product Management");
        System.out.println("3. Order Management");
        System.out.println("q. Exit System");
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
