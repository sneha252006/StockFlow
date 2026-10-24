
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryDemo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Product> products = new ArrayList<>();

        int choice;

        do {
            System.out.println("\n===== STOCKFLOW INVENTORY =====");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Search Product by ID");
            System.out.println("4. Update Stock Quantity");
            System.out.println("5. Record Sale");
            System.out.println("6. Delete Product");
            System.out.println("7. Stock Report");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Product ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Product Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Category: ");
                    String category = sc.nextLine();

                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();

                    System.out.print("Enter Quantity: ");
                    int quantity = sc.nextInt();

                    System.out.print("Enter Minimum Stock: ");
                    int minimumStock = sc.nextInt();

                    if (price < 0 || quantity < 0 || minimumStock < 0) {
                        System.out.println("Values cannot be negative!");
                        break;
                    }

                    boolean exists = false;

                    for (Product p : products) {
                        if (p.getProductId() == id) {
                            exists = true;
                            break;
                        }
                    }

                    if (exists) {
                        System.out.println("Product ID already exists!");
                    } else {
                        Product newProduct = new Product(
                            id, name, category, price,
                            quantity, minimumStock
                        );

                        products.add(newProduct);
                        System.out.println("Product added successfully!");
                        checkLowStock(newProduct);
                    }
                    break;

                case 2:
                    if (products.isEmpty()) {
                        System.out.println("No products available.");
                    } else {
                        System.out.println("\n----- PRODUCT LIST -----");

                        for (Product p : products) {
                            p.displayProduct();
                            checkLowStock(p);
                            System.out.println("------------------------");
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter Product ID to search: ");
                    int searchId = sc.nextInt();

                    Product searchProduct = null;

                    for (Product p : products) {
                        if (p.getProductId() == searchId) {
                            searchProduct = p;
                            break;
                        }
                    }

                    if (searchProduct == null) {
                        System.out.println("Product not found.");
                    } else {
                        searchProduct.displayProduct();
                        checkLowStock(searchProduct);
                    }
                    break;

                case 4:
                    System.out.print("Enter Product ID: ");
                    int updateId = sc.nextInt();

                    Product selected = null;

                    for (Product p : products) {
                        if (p.getProductId() == updateId) {
                            selected = p;
                            break;
                        }
                    }

                    if (selected == null) {
                        System.out.println("Product not found.");
                    } else {
                        System.out.print("Enter new quantity: ");
                        int newQuantity = sc.nextInt();

                        if (newQuantity < 0) {
                            System.out.println(
                                "Quantity cannot be negative!"
                            );
                        } else {
                            selected.setQuantity(newQuantity);
                            System.out.println(
                                "Stock updated successfully!"
                            );
                            checkLowStock(selected);
                        }
                    }
                    break;

                case 5:
                    System.out.print("Enter Product ID to sell: ");
                    int saleId = sc.nextInt();

                    Product saleProduct = null;

                    for (Product p : products) {
                        if (p.getProductId() == saleId) {
                            saleProduct = p;
                            break;
                        }
                    }

                    if (saleProduct == null) {
                        System.out.println("Product not found.");
                        break;
                    }

                    System.out.print("Enter quantity to sell: ");
                    int saleQuantity = sc.nextInt();

                    if (saleQuantity <= 0) {
                        System.out.println(
                            "Sale quantity must be positive!"
                        );
                    } else if (
                        saleQuantity > saleProduct.getQuantity()
                    ) {
                        System.out.println("Insufficient stock!");
                    } else {
                        double total =
                            saleProduct.getPrice() * saleQuantity;

                        saleProduct.setQuantity(
                            saleProduct.getQuantity() - saleQuantity
                        );

                        System.out.println("\n===== SALES RECEIPT =====");
                        System.out.println(
                            "Product: " + saleProduct.getName()
                        );
                        System.out.println(
                            "Quantity Sold: " + saleQuantity
                        );
                        System.out.printf(
                            "Price per Item: Rs. %.2f%n",
                            saleProduct.getPrice()
                        );
                        System.out.printf(
                            "Total Bill: Rs. %.2f%n", total
                        );
                        System.out.println(
                            "Sale recorded successfully!"
                        );

                        checkLowStock(saleProduct);
                    }
                    break;

                case 6:
                    System.out.print("Enter Product ID to delete: ");
                    int deleteId = sc.nextInt();

                    Product deleteProduct = null;

                    for (Product p : products) {
                        if (p.getProductId() == deleteId) {
                            deleteProduct = p;
                            break;
                        }
                    }

                    if (deleteProduct == null) {
                        System.out.println("Product not found.");
                    } else {
                        products.remove(deleteProduct);
                        System.out.println(
                            "Product deleted successfully!"
                        );
                    }
                    break;

                case 7:
                    if (products.isEmpty()) {
                        System.out.println(
                            "No products available for the report."
                        );
                        break;
                    }

                    int totalProducts = products.size();
                    int totalUnits = 0;
                    int lowStockCount = 0;
                    double totalValue = 0;

                    System.out.println("\n===== STOCK REPORT =====");

                    for (Product p : products) {
                        totalUnits += p.getQuantity();
                        totalValue +=
                            p.getPrice() * p.getQuantity();

                        if (p.getQuantity() <= p.getMinimumStock()) {
                            lowStockCount++;
                        }
                    }

                    System.out.println(
                        "Total Products: " + totalProducts
                    );
                    System.out.println(
                        "Total Units in Stock: " + totalUnits
                    );
                    System.out.printf(
                        "Total Inventory Value: Rs. %.2f%n",
                        totalValue
                    );
                    System.out.println(
                        "Products Needing Restock: " + lowStockCount
                    );

                    if (lowStockCount > 0) {
                        System.out.println(
                            "\n----- PRODUCTS NEEDING RESTOCK -----"
                        );

                        for (Product p : products) {
                            if (
                                p.getQuantity() <= p.getMinimumStock()
                            ) {
                                System.out.println(
                                    p.getName()
                                    + " | Quantity: " + p.getQuantity()
                                    + " | Minimum: " + p.getMinimumStock()
                                );
                            }
                        }
                    } else {
                        System.out.println(
                            "All products have sufficient stock."
                        );
                    }

                    System.out.println("------------------------");
                    break;

                case 8:
                    System.out.println(
                        "Exiting StockFlow Inventory..."
                    );
                    break;

                default:
                    System.out.println(
                        "Invalid choice. Try again."
                    );
            }

        } while (choice != 8);

        sc.close();
    }

    public static void checkLowStock(Product p) {
        if (p.getQuantity() <= p.getMinimumStock()) {
            System.out.println("\n!!! LOW STOCK ALERT !!!");
            System.out.println("Product: " + p.getName());
            System.out.println(
                "Available Stock: " + p.getQuantity()
            );
            System.out.println(
                "Minimum Required: " + p.getMinimumStock()
            );
            System.out.println("Please restock this product!");
        }
    }
}
