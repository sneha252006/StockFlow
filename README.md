# StockFlow – Stock Management System

StockFlow is a Java console-based stock management system designed to simplify inventory and sales management for small businesses. It supports role-based access for administrators and staff, product management, sales processing, and inventory reporting.

## Features

- Admin and Staff login with role-based permissions
- Add, view, search, update, and delete products (Admin)
- View and search products (Staff)
- Update stock quantities
- Record sales and generate billing receipts
- Automatically deduct stock after sales
- Low-stock alerts based on minimum stock levels
- Generate inventory reports
- Calculate total inventory value and available stock
- Save and load product data automatically using CSV files

## Technology Stack

- Java
- Object-Oriented Programming (OOP)
- Java Collections (`ArrayList`)
- File Handling and CSV Data Storage
- Git and GitHub

## Data Storage

StockFlow uses a CSV file named `stock.csv` to store product details, including product ID, name, category, price, quantity, and minimum stock level.

Inventory changes are saved automatically and loaded when the application starts. The CSV file can also be opened using Microsoft Excel.

## Login Credentials

**Administrator**
- Username: `admin`
- Password: `admin123`

**Staff**
- Username: `staff`
- Password: `staff123`

*These are demonstration credentials for the college project.*

## How to Run

1. Install the Java Development Kit (JDK).
2. Open Command Prompt and navigate to the `src` directory.
3. Compile the Java files:

   `javac Product.java InventoryDemo.java`

4. Run the application:

   `java InventoryDemo`

5. Log in using one of the demonstration accounts.

## Project Status

Core development completed. The application supports role-based login, product management, sales processing, stock alerts, inventory reports, and CSV-based persistence.

## Future Improvements

- Enhanced reports and analytics
- Improved input validation and error handling
- Sales transaction history
- Secure user authentication
- Additional inventory insights

---

Developed as a college Java project.
