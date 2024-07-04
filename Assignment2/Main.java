import com.ecommerce.Customer;
import com.ecommerce.Product;
import com.ecommerce.orders.Order;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Product> products = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        // Create products
        Product product1 = new Product("Macbook Pro", 3000.00);
        Product product2 = new Product("Macbook Air", 1000.00);
        Product product3 = new Product("Asus TUF F15", 2500.00);
        products.add(product1);
        products.add(product2);
        products.add(product3);
        // Display products
        System.out.println("Products:");
        for (Product product : products) {
            System.out.println(product);
        }

        // Create customer
        Customer customer1 = new Customer("John Doe");
        customers.add(customer1);

        // Customer browses and adds products to the shopping cart
        customer1.addProductToCart(product3);

        // Display customer shopping cart and total cost
        System.out.println("Customer: " + customer1.getName());
        System.out.println("Shopping Cart: " + customer1.getShoppingCart());
        System.out.println("Total Cost: $" + customer1.calculateTotalCost());

        // Create an order
        List<Product> cart = new ArrayList<>(customer1.getShoppingCart());
        Order order1 = new Order(customer1, cart);
        orders.add(order1);

        // Display order details
        System.out.println("Order Details:");
        System.out.println("Order ID: " + order1.getOrderID());
        System.out.println("Customer: " + order1.getCustomer().getName());
        System.out.println("Products: " + order1.getProducts());
        System.out.println("Order Total: $" + order1.getOrderTotal());

        // Display total counts
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Products: " + products.size());
        System.out.println("Total Orders: " + orders.size());
    }
}
