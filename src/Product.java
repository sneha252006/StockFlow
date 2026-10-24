
public class Product {
    private int productId;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private int minimumStock;

    public Product(int productId, String name, String category,
                   double price, int quantity, int minimumStock) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.minimumStock = minimumStock;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
    this.quantity = quantity;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    public void displayProduct() {
        System.out.println("Product ID: " + productId);
        System.out.println("Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Price: Rs. " + price);
        System.out.println("Available Quantity: " + quantity);
        System.out.println("Minimum Stock: " + minimumStock);
    }
}
