import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AssignmentUnit8 {

    public static void main(String[] args) {
        // Sample dataset
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 28, "HR", 50000),
            new Employee("Bob", 35, "Engineering", 75000),
            new Employee("Charlie", 30, "Sales", 60000),
            new Employee("David", 45, "Engineering", 85000),
            new Employee("Eve", 50, "HR", 70000)
        );

        // Step 3: Function to concatenate name and department
        Function<Employee, String> nameAndDept = emp -> emp.getName() + " - " + emp.getDepartment();

        // Step 4: Using streams to generate a new collection of concatenated strings
        List<String> nameAndDeptList = employees.stream()
                                                .map(nameAndDept)
                                                .collect(Collectors.toList());

        // Print the concatenated name and department list
        System.out.println("Name and Department List:");
        nameAndDeptList.forEach(System.out::println);

        // Step 5: Finding the average salary using streams
        double averageSalary = employees.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);

        System.out.println("\nAverage Salary: " + averageSalary);

        // Step 6: Filtering employees by age and processing the collection
        int ageThreshold = 30;
        List<Employee> filteredEmployees = employees.stream().filter(emp -> emp.getAge() > ageThreshold).collect(Collectors.toList());

        // Print the filtered employee details
        System.out.println("\nFiltered Employees (Age > " + ageThreshold + "):");
        filteredEmployees.forEach(emp -> System.out.println(emp.getName() + ", Age: " + emp.getAge() + ", Department: " + emp.getDepartment() + ", Salary: " + emp.getSalary()));
    }
}

class Employee {
    private String name;
    private int age;
    private String department;
    private double salary;

    public Employee(String name, int age, String department, double salary) {
        this.name = name;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }
}