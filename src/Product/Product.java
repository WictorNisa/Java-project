package Product;

public class Product {
    private String name;
    private int product_id;
    private int manufacturer_id;
    private double price;
    private int stock_quantity;
    private int quantity;
    private String category;
    private String description;



    public Product(String name,int manufacturer_id, double price, int quantity, String description) {
        this.name = name;
        this.manufacturer_id = manufacturer_id;
        this.price = price;
        this.stock_quantity = quantity;
        this.description = description;
    }

    public Product(String name, double price, int manufacturer_id, int stock_quantity, String description) {
        this.name = name;
        this.price = price;
        this.manufacturer_id = manufacturer_id;
        this.stock_quantity = stock_quantity;
        this.description = description;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Product(String name, int productId, int manufacturerId, double price, int stockQuantity, String description) {
        this.name = name;
        this.product_id = productId;
        this.manufacturer_id = manufacturerId;
        this.price = price;
        this.stock_quantity = stockQuantity;
        this.description = description;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(int manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
