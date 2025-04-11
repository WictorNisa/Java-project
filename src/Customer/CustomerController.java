package Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


public class CustomerController {
    private final CustomerService customerService = new CustomerService();
    private final Scanner scanner = new Scanner(System.in);

    public void runMenu() throws SQLException {
        boolean returnToMain = false;

        while (!returnToMain) {
            displayHeader("CUSTOMER MANAGEMENT");
            System.out.println("1. View All Customers");
            System.out.println("2. Add New Customer");
            System.out.println("3. Find Customer by ID");
            System.out.println("4. Update Customer Email");
            System.out.println("0. Return to Main Menu");


            System.out.print("\nSelect an option: ");
            String select = scanner.nextLine().trim();


            switch (select) {
                case "1":
                    viewAllCustomers();
                    break;
                case "2":
                    addNewCustomer();
                    break;
                case "3":
                    findCustomerById();
                    break;
                case "4":
                    updateCustomerEmail();
                    break;
                case "0":
                    returnToMain = true;
                    break;
                default:
                    System.out.println("\n⚠️ Invalid option. Please try again.");
            }
            if (!returnToMain) {
                promptToContinue();
            }
        }
    }


    private void displayHeader(String title) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(" ".repeat((50 - title.length()) / 2) + title);
        System.out.println("=".repeat(50) + "\n");
    }

    private void viewAllCustomers() throws SQLException {
        displayHeader("CUSTOMER LIST");
        ArrayList<Customer> customers = customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found in the database.");
            return;
        }

        System.out.println("-".repeat(70));
        System.out.printf("%-5s %-25s %-30s %-10s\n", "ID", "NAME", "EMAIL", "PHONE");
        System.out.println("-".repeat(70));

        for (Customer c : customers) {
            System.out.printf("%-5d %-25s %-30s %-10s\n",
                    c.getCustomer_id(),
                    c.getName(),
                    c.getEmail(),
                    c.getPhone());
        }

        System.out.println("-".repeat(70));
        System.out.println("Total customers: " + customers.size());
    }


    private void addNewCustomer() throws SQLException {
        displayHeader("ADD NEW CUSTOMER");

        try {
            System.out.println("Enter customers name: ");
            String customerName = scanner.nextLine();
            if (customerName.trim().isEmpty()) {
                System.out.println("⚠️ Name cannot be empty!");
                return;
            }

            System.out.println("Enter customer email: ");
            String customerEmail = scanner.nextLine();
            if (!isValidEmail(customerEmail)) {
                System.out.println("⚠️ Invalid email format!");
                return;
            }

            System.out.println("Enter customers phone number: ");
            String customerPhone = scanner.nextLine();

            System.out.println("Enter customer address: ");
            String customerAddress = scanner.nextLine();

            System.out.println("Enter customers password: ");
            String customerPassword = scanner.nextLine();
            if (customerPassword.trim().isEmpty()) {
                System.out.println("⚠️ Password cannot be empty!");
                return;
            }

            Customer newCustomerToAdd = new Customer(customerName, customerEmail, customerPhone, customerAddress, customerPassword);
            Customer newCustomer = customerService.addCustomer(newCustomerToAdd);


            if (newCustomer != null) {
                System.out.println("\n✅ Customer added successfully!");
                System.out.println("-".repeat(40));
                System.out.println("ID:      " + newCustomer.getCustomer_id());
                System.out.println("Name:    " + newCustomer.getName());
                System.out.println("Email:   " + newCustomer.getEmail());
                System.out.println("Phone:   " + newCustomer.getPhone());
                System.out.println("Address: " + newCustomer.getAddress());
            } else {
                System.out.println("\n❌ Failed to add customer. Please try again.");
            }
        } catch (SQLException e) {
            System.out.println("\n❌ Database error: " + e.getMessage());
        }
    }


    private void findCustomerById() throws SQLException {
        displayHeader("FIND CUSTOMER");

        try {
            System.out.println("Enter customer id: ");
            int customerId = Integer.parseInt(scanner.nextLine());

            if (customerId <= 0) {
                System.out.println("⚠️ ID must be a positive number!");
                return;
            }

            Customer customer = customerService.getCustomerById(customerId);

            if (customer != null) {
                System.out.println("\n✅ Customer found!");
                System.out.println("-".repeat(40));
                System.out.println("ID:      " + customer.getCustomer_id());
                System.out.println("Name:    " + customer.getName());
                System.out.println("Email:   " + customer.getEmail());
                System.out.println("Phone:   " + customer.getPhone());
                System.out.println("Address: " + customer.getAddress());
            } else {
                System.out.println("\n❌ No customer found with ID: " + customerId);
            }

        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid ID format. Please enter a number.");
        }
    }

    private void updateCustomerEmail() throws SQLException {
        displayHeader("UPDATE CUSTOMER EMAIL: ");

        try {
            System.out.println("Enter customer ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            Customer customer = customerService.getCustomerById(id);
            if (customer == null) {
                System.out.println("\n❌ No customer found with ID: " + id);
                return;
            }
            System.out.println("\nCurrent email: " + customer.getEmail());
            System.out.print("Enter new email address: ");
            String newEmail = scanner.nextLine();

            if (!isValidEmail(newEmail)) {
                System.out.println("⚠️ Invalid email format!");
                return;
            }

            Customer updatedCustomer = customerService.updateCustomerEmail(newEmail, id);

            if (updatedCustomer != null) {
                System.out.println("\n✅ Email updated successfully!");
                System.out.println("-".repeat(40));
                System.out.println("Customer: " + updatedCustomer.getName());
                System.out.println("New email: " + updatedCustomer.getEmail());
            } else {
                System.out.println("\n❌ Failed to update email. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid ID format. Please enter a number.");
        } catch (SQLException e) {
            System.out.println("\n❌ Database error: " + e.getMessage());
        }
    }


    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && !email.trim().isEmpty();
    }

    private void promptToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

}

