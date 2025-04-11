package Product;

import Customer.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {
    private final ProductService productService = new ProductService();
    private final Scanner scanner = new Scanner(System.in);

    public void runMenu() throws SQLException {
        boolean returnToMenu = false;


        while (!returnToMenu) {
            displayHeader("PRODUCT MANAGEMENT SYSTEM");

            System.out.println("1. Browse All Products");
            System.out.println("2. Search Products by Name");
            System.out.println("3. Browse Products by Category");
            System.out.println("4. Update Product Price");
            System.out.println("5. Update Product Stock");
            System.out.println("6. Add New Product");
            System.out.println("0. Return to Main Menu");


            System.out.print("\nEnter your choice: ");
            String select = scanner.nextLine().trim();


            try {
                switch (select) {
                    case "1":
                        viewAllProducts();
                        break;
                    case "2":
                        searchProductByName();
                        break;
                    case "3":
                        browseProductsByCategory();
                        break;
                    case "4":
                        updateProductPrice();
                        break;
                    case "5":
                        updateProductStock();
                        break;
                    case "6":
                        addNewProduct();
                        break;
                    case "0":
                        returnToMenu = true;
                        break;
                    default:
                        System.out.println("\n⚠️ Invalid option. Please select from the available choices.");
                }
            } catch (SQLException e) {
                System.out.println("\n❌ Database error occurred: " + translateErrorMessage(e));
                System.out.println("Technical details: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n❌ An error occurred: " + e.getMessage());
            }

            if (!returnToMenu) {
                promptToContinue();
            }
        }
    }

    private void displayHeader(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" ".repeat((60 - title.length()) / 2) + title);
        System.out.println("=".repeat(60));
    }


    private void viewAllProducts() throws SQLException {
        displayHeader("PRODUCT CATALOG");

        ArrayList<Product> products = productService.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("No products found in the database.");
            return;
        }

        displayProductTable(products);

    }

    private void searchProductByName() throws SQLException {
        displayHeader("PRODUCT SEARCH");

        System.out.println("Enter product name or keyword: ");
        String productName = scanner.nextLine().trim();

        if (productName.isEmpty()) {
            System.out.println("⚠️ Search term cannot be empty.");
            return;
        }


        Product product = productService.getProductByName(productName);

        if (product != null) {
            System.out.println("\n✅ Product found:");
            System.out.println("-".repeat(60));
            System.out.println("ID:          " + product.getProduct_id());
            System.out.println("Name:        " + product.getName());
            System.out.println("Price:       $" + String.format("%.2f", product.getPrice()));
            System.out.println("Stock:       " + product.getStock_quantity() + " units");
            System.out.println("\nDescription: " + product.getDescription());
        } else {
            System.out.println("\n❌ No products found matching: \"" + productName + "\"");
        }
    }


    private void browseProductsByCategory() throws SQLException {
        displayHeader("BROWSE BY CATEGORY");

        System.out.println("Available Categories:");
        System.out.println("- Smartphones");
        System.out.println("- Laptops");
        System.out.println("- Tablets");
        System.out.println("- Audio");
        System.out.println("- Television");

        System.out.print("\nEnter category name: ");
        String category = scanner.nextLine().trim();


        if (category.isEmpty()) {
            System.out.println("⚠️ Category name cannot be empty.");
            return;
        }

        ArrayList<Product> productsByCategory = productService.getProductsByCategory(category);

        if (productsByCategory.isEmpty()) {
            System.out.println("\n❌ No products found in category: \"" + category + "\"");
            return;
        }

        displayHeader("PRODUCTS IN " + category.toUpperCase());
        displayProductTable(productsByCategory);
    }

    private void updateProductPrice() throws SQLException {
        displayHeader("UPDATE PRODUCT PRICE");
        System.out.println("Enter product name: ");
        String productName = scanner.nextLine().trim();
        if (productName.isEmpty()) {
            System.out.println("⚠️ Product name cannot be empty.");
            return;
        }

        // Verify that the product exists in the database
        Product product = productService.getProductByName(productName);
        if (product == null) {
            System.out.println("\n❌ Product not found: \"" + productName + "\"");
            return;
        }

        System.out.println("\nCurrent price: $" + String.format("%.2f", product.getPrice()));
        System.out.print("Enter new price: $");


        try {
            double newPrice = Double.parseDouble(scanner.nextLine().trim());

            //Validate the price
            if (newPrice <= 0) {
                System.out.println("⚠️ Price must be greater than zero.");
                return;
            }

            Product updatedProduct = productService.updateProductsPrice(productName, newPrice);

            if (updatedProduct != null) {
                System.out.println("\n✅ Price updated successfully!");
                System.out.println("Product: " + updatedProduct.getName());
                System.out.println("New price: $" + String.format("%.2f", updatedProduct.getPrice()));
            } else {
                System.out.println("\n❌ Failed to update price. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid price format. Please enter a valid number.");
        }
    }


    private void updateProductStock() throws SQLException {
        displayHeader("UPDATE PRODUCT STOCK");
        System.out.println("Enter product name: ");
        String productName = scanner.nextLine().trim();


        if (productName.isEmpty()) {
            System.out.println("⚠️ Product name cannot be empty.");
            return;
        }

        //Verify that the product exists in the db

        Product product = productService.getProductByName(productName);
        if (product == null) {
            System.out.println("\n❌ Product not found: \"" + productName + "\"");
            return;
        }

        System.out.println("\nCurrent stock: " + product.getStock_quantity() + " units");
        System.out.print("Enter new stock quantity: ");

        try {
            int newStockQuantity = Integer.parseInt(scanner.nextLine().trim());

            //Validate the stock in the DB
            if (newStockQuantity < 0) {
                System.out.println("⚠️ Stock quantity cannot be negative.");
                return;
            }

            Product updatedProduct = productService.updateProductsStock(productName, newStockQuantity);

            if (updatedProduct != null) {
                System.out.println("\n✅ Stock updated successfully!");
                System.out.println("Product: " + updatedProduct.getName());
                System.out.println("New stock: " + updatedProduct.getStock_quantity() + " units");
            } else {
                System.out.println("\n❌ Failed to update stock. Please try again.");
            }
        } catch (
                NumberFormatException e) {
            System.out.println("⚠️ Invalid quantity format. Please enter a whole number.");
        }
    }


    private void addNewProduct() throws SQLException {
        displayHeader("ADD NEW PRODUCT");

        //Manufacturer selection
        System.out.println("MANUFACTURER SELECTION");
        System.out.println("-".repeat(30));
        System.out.println("1. Apple");
        System.out.println("2. Samsung");
        System.out.println("3. Sony");
        System.out.println("4. Dell");
        System.out.println("5. LG");

        System.out.print("\nSelect manufacturer (1-5): ");

        int manufacturerId;

        try {
            manufacturerId = Integer.parseInt(scanner.nextLine().trim());
            if (manufacturerId < 1 || manufacturerId > 5) {
                System.out.println("⚠️ Invalid selection. Please choose between 1 and 5.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid input. Please enter a number between 1 and 5.");
            return;
        }

        //Product detauils
        System.out.print("\nEnter product name: ");
        String productName = scanner.nextLine().trim();

        if (productName.isEmpty()) {
            System.out.println("⚠️ Product name cannot be empty.");
            return;
        }

        System.out.print("Enter product description: ");
        String description = scanner.nextLine().trim();

        double price;
        try {
            System.out.println("Enter product price: $");
            price = Double.parseDouble(scanner.nextLine().trim());
            if (price <= 0) {
                System.out.println("⚠️ Price must be greater than zero.");
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid price format. Please enter a valid number.");
            return;
        }

        int stock;
        try {
            System.out.println("Enter inintial stock quantity: ");
            stock = Integer.parseInt(scanner.nextLine().trim());
            if (stock <= 0) {
                System.out.println("⚠️ Stock quantity cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid quantity format. Please enter a whole number.");
            return;
        }

        //Add the product

        Product newProduct = productService.addNewProduct(
                productName,
                manufacturerId,
                price,
                stock,
                description
        );


        if (newProduct != null) {
            System.out.println("\n✅ Product added successfully!");
            System.out.println("-".repeat(60));
            System.out.println("ID:          " + newProduct.getProduct_id());
            System.out.println("Name:        " + newProduct.getName());
            System.out.println("Price:       $" + String.format("%.2f", newProduct.getPrice()));
            System.out.println("Stock:       " + newProduct.getStock_quantity() + " units");
            System.out.println("Manufacturer: " + getManufacturerName(newProduct.getManufacturer_id()));
            System.out.println("\nDescription: " + newProduct.getDescription());
        } else {
            System.out.println("\n❌ Failed to add product. Please try again.");
        }
    }

    private void displayProductTable(ArrayList<Product> products) throws SQLException {
        System.out.println("-".repeat(80));
        System.out.printf("%-5s %-30s %-12s %-10s %-15s\n", "ID", "NAME", "PRICE", "STOCK", "MANUFACTURER");
        System.out.println("-".repeat(80));

        for (Product p : products) {
            System.out.printf("%-5s %-30s $%-11.2f %-10s %-15s\n",
                    p.getProduct_id(),
                    p.getName(),
                    p.getPrice(),
                    p.getStock_quantity(),
                    getManufacturerName(p.getManufacturer_id()));
        }

        System.out.println("-".repeat(80));
        System.out.println("Total products: " + products.size());

    }

    private String getManufacturerName(int manufacturerId) {
        switch (manufacturerId) {
            case 1:
                return "Apple";
            case 2:
                return "Samsung";
            case 3:
                return "Sony";
            case 4:
                return "Dell";
            case 5:
                return "LG";
            default:
                return "Unknown";
        }
    }

    private String translateErrorMessage(SQLException e) {
        String message = e.getMessage();
        if (message.contains("UNIQUE constraint failed")) {
            return "A product with this name already exists.";
        } else if (message.contains("NOT NULL constraint failed")) {
            return "Missing required information.";
        } else {
            return "Database operation failed.";
        }
    }

    private void promptToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

}
