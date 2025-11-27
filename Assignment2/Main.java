import com.ecommerce.Customer;
import com.ecommerce.Product;
import com.ecommerce.orders.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Product> products = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Initialize some demo products (store catalog)
        initializeProducts();

        System.out.println("=== E-Commerce System ===");
        System.out.println();

        // 2. Create customer using user input
        System.out.print("Enter your name to create a customer account: ");
        String customerName = scanner.nextLine().trim();
        while (customerName.isEmpty()) {
            System.out.print("Name cannot be empty. Please enter your name: ");
            customerName = scanner.nextLine().trim();
        }

        Customer customer = new Customer(customerName);
        customers.add(customer);
        System.out.println("Customer created successfully. Welcome, " + customer.getName() + "!");
        System.out.println();

        // 3. Let the customer browse products and add to cart
        boolean shopping = true;
        System.out.println("=== Product Catalog ===");
        displayProducts();
        while (shopping) {
            System.out.println("Your Shopping Cart: " + customer.getShoppingCart().size() + " items.");
            for (Product p : customer.getShoppingCart()) {
                System.out.println(" - " + p);
            }
            int choice = readInt(scanner,
                    "Enter product number to add to cart (0 to finish shopping or minus product number to remove): ");

            if (choice == 0) {
                shopping = false; // user finished adding items
            } else if (choice > 0 && choice <= products.size()) {
                Product selectedProduct = products.get(choice - 1);
                customer.addProductToCart(selectedProduct);
                System.out.println("Added to cart: " + selectedProduct);
            } else if (choice < 0 && -choice <= products.size()) {
                Product selectedProduct = products.get(-choice - 1);
                boolean removed = customer.removeProductFromCart(selectedProduct);
                if (removed) {
                    System.out.println("Removed from cart: " + selectedProduct);
                } else {
                    System.out.println("Product not found in cart: " + selectedProduct);
                }
            }
            else {
                System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }

        // 4. Display shopping cart and total cost
        if (customer.getShoppingCart().isEmpty()) {
            System.out.println("Your shopping cart is empty. No order will be placed.");
        } else {
            System.out.println("=== Shopping Cart Summary ===");
            System.out.println("Customer: " + customer.getName());
            System.out.println("Items in Cart:");
            for (Product p : customer.getShoppingCart()) {
                System.out.println(" - " + p);
            }
            System.out.println("Total Cost: $" + customer.calculateTotalCost());
            System.out.println();

            // 5. Ask if the user wants to place the order
            System.out.print("Do you want to place the order? (Y/N): ");
            String placeOrderInput = scanner.nextLine().trim().toLowerCase();

            if (placeOrderInput.equals("y") || placeOrderInput.equals("yes")) {
                try {
                    List<Product> cartCopy = new ArrayList<>(customer.getShoppingCart());
                    Order order = new Order(customer, cartCopy);
                    orders.add(order);

                    // 6. Display order details
                    System.out.println();
                    System.out.println("=== Order Placed Successfully ===");
                    System.out.println("Order ID: " + order.getOrderID());
                    System.out.println("Customer: " + order.getCustomer().getName());
                    System.out.println("Order Status: " + order.getStatus());
                    System.out.println("Products:");
                    for (Product p : order.getProducts()) {
                        System.out.println(" - " + p);
                    }
                    System.out.println("Order Total: $" + order.getOrderTotal());
                } catch (Exception e) {
                    // Basic error handling for unexpected issues during order placement
                    System.out.println("An error occurred while placing the order: " + e.getMessage());
                }
            } else {
                System.out.println("Order was not placed. Your cart remains saved for this session.");
            }
            System.out.print("Do you want to keep shopping? (Y/N): ");
            String keepShoppingInput = scanner.nextLine().trim().toLowerCase();
            if (keepShoppingInput.equals("y") || keepShoppingInput.equals("yes")) {
                main(null); // Restart the shopping process
            } else {
                System.out.println("Thank you for visiting our e-commerce system. Goodbye!");
            }
        }

        // 7. Display total counts
        System.out.println();
        System.out.println("=== System Summary ===");
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Products: " + products.size());
        System.out.println("Total Orders: " + orders.size());

        scanner.close();
    }

    // Initialize some sample products
    private static void initializeProducts() {
        products.add(new Product("Macbook Pro", 3000.00));
        products.add(new Product("Macbook Air", 1000.00));
        products.add(new Product("Asus TUF F15", 2500.00));
    }

    // Display products with numbers so user can choose
    private static void displayProducts() {
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i));
        }
    }

    // Safely read an integer from the user with basic validation
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
    }
}
