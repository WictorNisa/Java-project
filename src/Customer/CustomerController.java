package Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

// Here in the menu i add the different things i want to do, ex fetch all customers, update customerdata and so on
public class CustomerController {
    CustomerService customerService = new CustomerService();

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the digit of the action you would like to perform:");
        System.out.println("1. Fetch all customers");
        System.out.println("2. Add a new customer to the database");
        System.out.println("3. Fetch customer by id");
        System.out.println("4. Update a customers email");
        String select = scanner.nextLine();
        switch (select) {
            case "1":
                ArrayList<Customer> customers = customerService.getAllCustomers();
                for (Customer c : customers) {
                    System.out.println("CustomerId: " + c.getCustomer_id());
                    System.out.println("Name: " + c.getName());
                }
                break;

            case "2":
                System.out.println("Enter the customers name");
                String customerName = scanner.nextLine();
                System.out.println("Enter the customers email");
                String customerEmail = scanner.nextLine();
                System.out.println("Enter the customers phone number");
                String customerPhone = scanner.nextLine();
                System.out.println("Enter the customers adress");
                String customerAdress = scanner.nextLine();
                System.out.println("Enter the customers password");
                String customerPassword = scanner.nextLine();
                Customer newCustomerToAdd = new Customer(customerName, customerEmail, customerPhone, customerAdress, customerPassword);
                Customer newCustomer = customerService.addCustomer(newCustomerToAdd);
                System.out.println("newCustomer = " + newCustomer);
                break;

            case "3":
                System.out.println("Enter the id of the the customer");
                int customerId = scanner.nextInt();
                Customer customer = customerService.getCustomerById((customerId));
                System.out.println(customer);
                break;
            case "4":
                System.out.println("Enter the id of the customer whose email you want to update");
                int id = scanner.nextInt();
                scanner.nextLine(); // Consumes the newLine
                System.out.println("Enter the new email address");
                String newEmail = scanner.nextLine();
                Customer updatedCustomer = customerService.updateCustomerEmail(newEmail, id);
                System.out.println(updatedCustomer.getName());
                break;
        }
    }
}
