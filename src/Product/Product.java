package Product;

public class Product {
    private String name;
    private int manufacturer_id;
    private double price;
    private int stock_quantity;
    private String category;
    private String description;


    public Product(String name, int manufacturer_id, double price, int quantity, String description) {
        this.name = name;
        this.manufacturer_id = manufacturer_id;
        this.price = price;
        this.stock_quantity = quantity;
        this.description = description;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
