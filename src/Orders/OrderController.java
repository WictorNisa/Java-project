package Orders;

import Customer.CustomerService;
import Customer.Customer;
import Product.Product;
import Product.ProductService;

import java.sql.Array;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.*;

public class OrderController {
    OrderService orderService = new OrderService();
    CustomerService customerService = new CustomerService();
    ProductService productService = new ProductService();

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the digit of the action you would like to perform:");
        System.out.println("1. View order history for a customer by their id");
        System.out.println("2. Add a new order");
        System.out.println("3. Add multiple products to a order");
        String select = scanner.nextLine();
        switch (select) {
            case "1":
                System.out.println("Enter the id of the customer you would like to view:");
                int customerId = scanner.nextInt();
                Customer customerOrders = orderService.getCustomerOrderHistory(customerId);

                if (customerOrders == null) {
                    System.out.println("\n===== CUSTOMER NOT FOUND =====");
                    System.out.println("No customer with ID " + customerId + " exists in the database.");
                } else {
                    System.out.println("\n===== ORDER HISTORY =====");
                    System.out.println("Customer: " + customerOrders.getName() + " (ID: " + customerId + ")");
                    System.out.println("Email: " + customerOrders.getEmail());
                    System.out.println("\nORDERS:");

                    List<Order> orders = customerOrders.getOrders();
                    if (orders.isEmpty()) {
                        System.out.println("This customer has no order history.");
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.printf("%-10s %-20s %s\n", "ORDER ID", "DATE", "ITEMS");
                        System.out.println("--------------------------------------------------");

                        for (Order order : orders) {
                            System.out.printf("%-10d %-20s %s\n",
                                    order.getOrder_id(),
                                    order.getDate_time(),
                                    "Items can be added here if available");
                        }
                        System.out.println("--------------------------------------------------");
                        System.out.println("Total orders: " + orders.size());
                    }
                }
                break;
            case "2":
                System.out.println("\n===== CREATE NEW ORDER =====");
                System.out.println("Enter the ID of the customer placing this order:");
                int customerid = scanner.nextInt();
                scanner.nextLine(); // Consume the leftover newline

                // First verify if the customer exists
                Customer orderCustomer = customerService.getCustomerById(customerid);

                if (orderCustomer == null) {
                    System.out.println("\n⚠️ CUSTOMER NOT FOUND ⚠️");
                    System.out.println("No customer with ID " + customerid + " exists in our database.");
                    System.out.println("Please check the ID and try again.");
                } else {
                    // Show customer information for confirmation
                    System.out.println("\nCustomer Information:");
                    System.out.println("Name: " + orderCustomer.getName());
                    System.out.println("Email: " + orderCustomer.getEmail());

                    // Here you would typically add code to let the user add products to the order
                    // For simplicity, we'll just create an order with today's date

                    System.out.println("\nCreating new order for " + orderCustomer.getName() + "...");

                    // Create a new order with just the customer ID
                    Order newOrder = new Order(0, customerid, new java.sql.Date(System.currentTimeMillis()));
                    Order createdOrder = orderService.addNewOrder(newOrder);

                    if (createdOrder != null) {
                        System.out.println("\n✅ ORDER CREATED SUCCESSFULLY ✅");
                        System.out.println("--------------------------------------------------");
                        System.out.println("Order Details:");
                        System.out.println("Customer: " + orderCustomer.getName());
                        System.out.println("Date: " + createdOrder.getDate_time());
                        System.out.println("--------------------------------------------------");
                        System.out.println("The order has been recorded in the system.");
                        System.out.println("You can now add products to this order using option X in the main menu.");
                    } else {
                        System.out.println("\n❌ ERROR CREATING ORDER ❌");
                        System.out.println("There was a problem creating this order. Please try again.");
                    }
                }
                break;
            case "3":
                boolean exit = true;
                ArrayList<Product> tempProducts = new ArrayList<>();
                ArrayList<Product> allProducts = productService.getAllProducts();
                Map<Product, Integer> productQuantities = new HashMap<>();
                double orderTotal = 0.0;

                System.out.println("\n" + "=".repeat(50));
                System.out.println("           CREATE NEW ORDER");
                System.out.println("=".repeat(50));

                System.out.println("\n📋 STEP 1: CUSTOMER SELECTION");
                System.out.print("Enter customer ID: ");
                int newCustomerid = scanner.nextInt();
                scanner.nextLine(); // Consume the leftover newline

                // First verify if the customer exists
                Customer customerExists = customerService.getCustomerById(newCustomerid);

                if (customerExists == null) {
                    System.out.println("\n⚠️  CUSTOMER NOT FOUND  ⚠️");
                    System.out.println("-".repeat(50));
                    System.out.println("No customer with ID " + newCustomerid + " exists in our database.");
                    System.out.println("Please check the ID and try again.");
                } else {
                    // Show customer information for confirmation
                    System.out.println("\n✅ Customer found!");
                    System.out.println("-".repeat(30));
                    System.out.println("ID:    " + customerExists.getCustomer_id());
                    System.out.println("Name:  " + customerExists.getName());
                    System.out.println("Email: " + customerExists.getEmail());

                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("📋 STEP 2: PRODUCT SELECTION");
                    System.out.println("=".repeat(50));

                    System.out.println("\nAVAILABLE PRODUCTS:");
                    System.out.println("-".repeat(70));
                    System.out.printf("%-5s %-25s %-10s %-10s %s\n", "ID", "PRODUCT NAME", "PRICE", "STOCK", "CATEGORY");
                    System.out.println("-".repeat(70));

                    for (Product p : allProducts) {
                        System.out.printf("%-5d %-25s $%-9.2f %-10d %s\n",
                                p.getProduct_id(),
                                p.getName(),
                                p.getPrice(),
                                p.getStock_quantity(),
                                p.getCategory());
                    }

                    System.out.println("\nPlease select products to add to this order.");
                    System.out.println("You can add multiple products, one at a time.");

                    while (exit) {
                        System.out.println("\n-".repeat(40));
                        System.out.print("Enter product name: ");
                        String productName = scanner.nextLine();

                        Product selectedProduct = productService.getProductByName(productName);
                        if (selectedProduct != null) {
                            System.out.println("  Selected: " + selectedProduct.getName() + " - $" + selectedProduct.getPrice());

                            System.out.print("Enter quantity: ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            if (quantity <= 0) {
                                System.out.println("❌ Invalid quantity. Please enter a positive number.");
                                continue;
                            }

                            if (quantity > selectedProduct.getStock_quantity()) {
                                System.out.println("⚠️ Warning: Requested quantity exceeds available stock (" +
                                        selectedProduct.getStock_quantity() + ")");
                                System.out.print("Proceed anyway? (Y/N): ");
                                if (!scanner.nextLine().equalsIgnoreCase("Y")) {
                                    continue;
                                }
                            }

                            productQuantities.put(selectedProduct, quantity);
                            tempProducts.add(selectedProduct);
                            orderTotal += selectedProduct.getPrice() * quantity;

                            System.out.println("✅ Product added to order!");
                        } else {
                            System.out.println("❌ Product not found. Please check the name and try again.");
                            continue;
                        }

                        System.out.print("\nAdd more products? (Y/N): ");
                        String answer = scanner.nextLine();
                        if (!answer.equalsIgnoreCase("Y")) {
                            exit = false;
                        }
                    }

                    // Display order summary
                    if (tempProducts.isEmpty()) {
                        System.out.println("\n❌ No products selected. Order cancelled.");
                        break;
                    }

                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("📋 STEP 3: ORDER SUMMARY");
                    System.out.println("=".repeat(50));

                    System.out.println("\nCustomer: " + customerExists.getName());
                    System.out.println("Date: " + new java.sql.Date(System.currentTimeMillis()));
                    System.out.println("\nSELECTED PRODUCTS:");
                    System.out.println("-".repeat(70));
                    System.out.printf("%-25s %-10s %-10s %s\n", "PRODUCT", "PRICE", "QUANTITY", "SUBTOTAL");
                    System.out.println("-".repeat(70));

                    for (Product product : tempProducts) {
                        int qty = productQuantities.get(product);
                        double subtotal = product.getPrice() * qty;
                        System.out.printf("%-25s $%-9.2f %-10d $%.2f\n",
                                product.getName(),
                                product.getPrice(),
                                qty,
                                subtotal);
                    }

                    System.out.println("-".repeat(70));
                    System.out.printf("%-47s $%.2f\n", "TOTAL:", orderTotal);

                    System.out.print("\nConfirm order? (Y/N): ");
                    if (!scanner.nextLine().equalsIgnoreCase("Y")) {
                        System.out.println("\nOrder cancelled by user.");
                        break;
                    }

                    System.out.println("\nProcessing order...");

                    // Create the order
                    Order newOrder = new Order(0, newCustomerid, new java.sql.Date(System.currentTimeMillis()));
                    Order createdOrder = orderService.addNewOrder(newOrder);

                    if (createdOrder == null) {
                        System.out.println("\n❌ ERROR CREATING ORDER ❌");
                        System.out.println("There was a problem creating this order. Please try again.");
                        break;
                    }

                    int orderId = createdOrder.getOrder_id();
                    int successCount = 0;

                    // Add products to the order
                    for (Product product : tempProducts) {
                        int quantity = productQuantities.get(product);
                        boolean success = orderService.addProductsToOrder(orderId, product, quantity);
                        if (success) {
                            successCount++;
                        } else {
                            System.out.println("⚠️ Could not add " + product.getName() + " to the order.");
                        }
                    }

                    // Final confirmation
                    if (successCount == tempProducts.size()) {
                        System.out.println("\n" + "=".repeat(50));
                        System.out.println("✅ ORDER #" + orderId + " COMPLETED SUCCESSFULLY ✅");
                        System.out.println("=".repeat(50));
                        System.out.println("\nThank you for your order! A confirmation has been sent to:");
                        System.out.println(customerExists.getEmail());
                        System.out.println("\nYour order will be processed shortly.");
                    } else {
                        System.out.println("\n⚠️ ORDER PARTIALLY COMPLETED ⚠️");
                        System.out.println("Some products could not be added to your order.");
                        System.out.println("Please contact customer support for assistance.");
                    }
                }
                break;


        }
    }
}
