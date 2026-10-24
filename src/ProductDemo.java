
public class ProductDemo {
    public static void main(String[] args) {
        Product p1 = new Product(
            101, "Notebook", "Stationery", 40.0, 50, 10
        );

        System.out.println("===== STOCKFLOW PRODUCT DETAILS =====");
        p1.displayProduct();
    }
}
