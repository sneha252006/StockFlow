
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class InventoryDemo {

    static ArrayList<Product> products = new ArrayList<>();
    static final String FILE_NAME = "stock.csv";

    static final String ADMIN_USER = "admin";
    static final String ADMIN_PASS = "admin123";
    static final String STAFF_USER = "staff";
    static final String STAFF_PASS = "staff123";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadProducts();

        System.out.println("================================");
        System.out.println("       WELCOME TO STOCKFLOW");
        System.out.println("================================");
        System.out.println("Inventory loaded successfully.");

        boolean exitProgram = false;

        while (!exitProgram) {
            String role = login(sc);

            if (role.equals("EXIT")) {
                break;
            }

            boolean logout = false;

            while (!logout && !exitProgram) {
                showMenu(role);
                int choice = readInt(sc, "Enter your choice: ");

                if (role.equals("ADMIN")) {
                    switch (choice) {
                        case 1:
                            addProduct(sc);
                            break;
                        case 2:
                            viewProducts();
                            break;
                        case 3:
                            searchProduct(sc);
                            break;
                        case 4:
                            updateStock(sc);
                            break;
                        case 5:
                            recordSale(sc);
                            break;
                        case 6:
                            deleteProduct(sc);
                            break;
                        case 7:
                            stockReport();
                            break;
                        case 8:
                            logout = true;
                            System.out.println("Admin logged out.");
                            break;
                        case 9:
                            saveProducts();
                            exitProgram = true;
                            System.out.println("Inventory saved. Goodbye!");
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                } else {
                    switch (choice) {
                        case 1:
                            viewProducts();
                            break;
                        case 2:
                            searchProduct(sc);
                            break;
                        case 3:
                            recordSale(sc);
                            break;
                        case 4:
                            stockReport();
                            break;
                        case 5:
                            logout = true;
                            System.out.println("Staff logged out.");
                            break;
                        case 6:
                            saveProducts();
                            exitProgram = true;
                            System.out.println("Inventory saved. Goodbye!");
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                }
            }
        }

        saveProducts();
        sc.close();
    }

    static String login(Scanner sc) {
        while (true) {
            System.out.println("\n========== STOCKFLOW LOGIN ==========");
            System.out.println("Enter 0 as username to exit.");
            System.out.print("Username: ");
            String username = sc.nextLine().trim();

            if (username.equals("0")) {
                return "EXIT";
            }

            System.out.print("Password: ");
            String password = sc.nextLine();

            if (username.equals(ADMIN_USER)
                    && password.equals(ADMIN_PASS)) {
                System.out.println("Admin login successful!");
                return "ADMIN";
            }

            if (username.equals(STAFF_USER)
                    && password.equals(STAFF_PASS)) {
                System.out.println("Staff login successful!");
                return "STAFF";
            }

            System.out.println("Invalid username or password. Try again.");
        }
    }

    static void showMenu(String role) {
        System.out.println("\n========== STOCKFLOW ==========");

        if (role.equals("ADMIN")) {
            System.out.println("Logged in as: ADMIN");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Search Product by ID");
            System.out.println("4. Update Stock Quantity");
            System.out.println("5. Record Sale");
            System.out.println("6. Delete Product");
            System.out.println("7. Stock Report");
            System.out.println("8. Logout");
            System.out.println("9. Exit Program");
        } else {
            System.out.println("Logged in as: STAFF");
            System.out.println("1. View All Products");
            System.out.println("2. Search Product by ID");
            System.out.println("3. Record Sale");
            System.out.println("4. Stock Report");
            System.out.println("5. Logout");
            System.out.println("6. Exit Program");
        }
    }

    static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    static void addProduct(Scanner sc) {
        System.out.println("\n----- ADD PRODUCT -----");

        int id = readInt(sc, "Enter Product ID: ");

        if (findProduct(id) != null) {
            System.out.println("Product ID already exists!");
            return;
        }

        System.out.print("Enter Product Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter Category: ");
        String category = sc.nextLine().trim();

        if (name.isEmpty() || category.isEmpty()
                || name.contains(",") || category.contains(",")) {
            System.out.println(
                "Name and category must not be empty or contain commas."
            );
            return;
        }

        double price = readDouble(sc, "Enter Price: ");
        int quantity = readInt(sc, "Enter Quantity: ");
        int minimumStock = readInt(sc, "Enter Minimum Stock: ");

        if (id <= 0 || price < 0 || quantity < 0 || minimumStock < 0) {
            System.out.println("ID must be positive; other values cannot be negative.");
            return;
        }

        Product p = new Product(
            id, name, category, price, quantity, minimumStock
        );

        products.add(p);
        saveProducts();

        System.out.println("Product added successfully!");
        checkLowStock(p);
    }

    static void viewProducts() {
        System.out.println("\n----- PRODUCT LIST -----");

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product p : products) {
            p.displayProduct();
            checkLowStock(p);
            System.out.println("------------------------");
        }
    }

    static void searchProduct(Scanner sc) {
        int id = readInt(sc, "Enter Product ID to search: ");
        Product p = findProduct(id);

        if (p == null) {
            System.out.println("Product not found.");
        } else {
            p.displayProduct();
            checkLowStock(p);
        }
    }

    static void updateStock(Scanner sc) {
        int id = readInt(sc, "Enter Product ID: ");
        Product p = findProduct(id);

        if (p == null) {
            System.out.println("Product not found.");
            return;
        }

        int quantity = readInt(sc, "Enter new quantity: ");

        if (quantity < 0) {
            System.out.println("Quantity cannot be negative!");
            return;
        }

        p.setQuantity(quantity);
        saveProducts();

        System.out.println("Stock updated successfully!");
        checkLowStock(p);
    }

    static void recordSale(Scanner sc) {
        int id = readInt(sc, "Enter Product ID to sell: ");
        Product p = findProduct(id);

        if (p == null) {
            System.out.println("Product not found.");
            return;
        }

        int quantity = readInt(sc, "Enter quantity to sell: ");

        if (quantity <= 0) {
            System.out.println("Sale quantity must be positive!");
            return;
        }

        if (quantity > p.getQuantity()) {
            System.out.println("Insufficient stock!");
            return;
        }

        double total = p.getPrice() * quantity;
        p.setQuantity(p.getQuantity() - quantity);
        saveProducts();

        System.out.println("\n========== SALES RECEIPT ==========");
        System.out.println("Product: " + p.getName());
        System.out.println("Quantity Sold: " + quantity);
        System.out.printf("Price per Item: Rs. %.2f%n", p.getPrice());
        System.out.printf("Total Bill: Rs. %.2f%n", total);
        System.out.println("Sale recorded successfully!");
        System.out.println("===================================");

        checkLowStock(p);
    }

    static void deleteProduct(Scanner sc) {
        int id = readInt(sc, "Enter Product ID to delete: ");
        Product p = findProduct(id);

        if (p == null) {
            System.out.println("Product not found.");
            return;
        }

        products.remove(p);
        saveProducts();
        System.out.println("Product deleted successfully!");
    }

    static void stockReport() {
        System.out.println("\n========== STOCK REPORT ==========");

        int totalUnits = 0;
        int lowStockCount = 0;
        double totalValue = 0;

        for (Product p : products) {
            totalUnits += p.getQuantity();
            totalValue += p.getPrice() * p.getQuantity();

            if (p.getQuantity() <= p.getMinimumStock()) {
                lowStockCount++;
            }
        }

        System.out.println("Total Products: " + products.size());
        System.out.println("Total Units in Stock: " + totalUnits);
        System.out.printf("Total Inventory Value: Rs. %.2f%n", totalValue);
        System.out.println("Products Needing Restock: " + lowStockCount);

        for (Product p : products) {
            if (p.getQuantity() <= p.getMinimumStock()) {
                System.out.println(
                    "LOW STOCK: " + p.getName()
                    + " | Quantity: " + p.getQuantity()
                    + " | Minimum: " + p.getMinimumStock()
                );
            }
        }

        System.out.println("==================================");
    }

    public static Product findProduct(int id) {
        for (Product p : products) {
            if (p.getProductId() == id) {
                return p;
            }
        }
        return null;
    }

    public static void checkLowStock(Product p) {
        if (p.getQuantity() <= p.getMinimumStock()) {
            System.out.println("\n!!! LOW STOCK ALERT !!!");
            System.out.println("Product: " + p.getName());
            System.out.println("Available Stock: " + p.getQuantity());
            System.out.println("Minimum Required: " + p.getMinimumStock());
            System.out.println("Please restock this product!");
        }
    }

    public static void saveProducts() {
        try (PrintWriter writer =
                 new PrintWriter(new FileWriter(FILE_NAME))) {

            writer.println(
                "ProductID,Name,Category,Price,Quantity,MinimumStock"
            );

            for (Product p : products) {
                writer.println(
                    p.getProductId() + ","
                    + p.getName() + ","
                    + p.getCategory() + ","
                    + p.getPrice() + ","
                    + p.getQuantity() + ","
                    + p.getMinimumStock()
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    public static void loadProducts() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            saveProducts();
            return;
        }

        try (Scanner reader = new Scanner(file)) {
            if (reader.hasNextLine()) {
                reader.nextLine();
            }

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split(",", -1);

                if (data.length == 6) {
                    try {
                        Product p = new Product(
                            Integer.parseInt(data[0]),
                            data[1],
                            data[2],
                            Double.parseDouble(data[3]),
                            Integer.parseInt(data[4]),
                            Integer.parseInt(data[5])
                        );
                        products.add(p);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid CSV row.");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }
}
