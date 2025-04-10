package Product;

import Customer.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {
    ProductService productService = new ProductService();

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the digit of the action you would like to perform:");
        System.out.println("1. Fetch all products");
        System.out.println("2. Search for a product by name");
        System.out.println("3. Search for products by category");
        System.out.println("4. Update a products price by name");
        System.out.println("5. Update a products stock by name");
        System.out.println("6. Add a new product");

        String select = scanner.nextLine();
        switch (select) {
            case "1":
                ArrayList<Product> products = productService.getAllProducts();
                for (Product p : products) {
                    System.out.println("Name: " + p.getName());
                    System.out.println("Price: " + p.getPrice());
                }
            case "2":
                System.out.println("Enter the name of the product");
                String productName = scanner.nextLine();
                Product searchedProduct = productService.getProductByName(productName);
                System.out.println("Searched product = " + searchedProduct);
                break;
            case "3":
                System.out.println("Enter the category of the product");
                String productCategory = scanner.nextLine();
                ArrayList<Product> productsByCategory = productService.getProductsByCategory(productCategory);
                for (Product p : productsByCategory) {
                    System.out.println("Name: " + p.getName());
                    System.out.println("Price: " + p.getPrice());

                }
                break;
            case "4":
                System.out.println("Enter the name of the product");
                String updatedName = scanner.nextLine();
                System.out.println("Enter the new price");
                double updatedPrice = scanner.nextDouble();
                Product updatedProduct = productService.updateProductsPrice(updatedName, updatedPrice);
                System.out.println("Updated product = " + updatedProduct.getName());
                break;
            case "5":
                System.out.println("Enter the name of the product");
                String updatedProductName = scanner.nextLine();
                System.out.println("Enter the new stock");
                int stock = scanner.nextInt();
                Product updatedStock = productService.updateProductsStock(updatedProductName, stock);
                System.out.println("Updated product = " + updatedStock);
                break;
            case "6":
                System.out.println("=== MANUFACTURER SELECTION ===");
                System.out.println("Please select a manufacturer by entering its ID number:");
                System.out.println("1. Apple");
                System.out.println("2. Samsung");
                System.out.println("3. Sony");
                System.out.println("4. Dell");
                System.out.println("5. LG");
                System.out.println("Enter manufacturer ID (1-5): ");
                int manufacturerID = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter the name of the product");
                String newProudctName = scanner.nextLine();
                System.out.println("Enter the description of the product");
                String newProudctDescription = scanner.nextLine();
                System.out.println("Enter the price of the product");
                double newProudctPrice = scanner.nextDouble();
                System.out.println("Enter the stock of the product");
                int newStock = scanner.nextInt();
                scanner.nextLine();
                Product newProduct = productService.addNewProduct(
                        newProudctName,
                        manufacturerID,
                        newProudctPrice,
                        newStock,
                        newProudctDescription
                );
                System.out.println("New product = " + newProduct);
                break;

        }
    }
}
