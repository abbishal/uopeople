import java.util.Scanner;

public class AssignmentUnit6 {
    public static void main(String[] args) {
        // Setting up vehicle details
        // car
        Car car = new Car("Toyota", "Corolla", 2020);
        car.setNumberOfDoors(4);
        car.setFuelType("Petrol");
        // motorbike
        Motorcycle motorcycle = new Motorcycle("Suzuki", "Gixxer-SF", 2024);
        motorcycle.setNumberOfWheels(2);
        motorcycle.setTypeOfMotorcycle("Sport");
        // Truck
        Truck truck = new Truck("Ford", "F-150", 2019);
        truck.setCargoCapacity(5.0);
        truck.setTransmissionType("Automatic");

        while (true) {

            // taking user input
            System.out.println("Select vehicle type: \n1. Car \n2. Motorcycle \n3. Truck");
            int choice = Inputs.getIntInput("Input Number: ");

            switch (choice) {
                case 1:
                    System.out.println("Car Details:");
                    System.out.println("Make: " + car.getMake());
                    System.out.println("Model: " + car.getModel());
                    System.out.println("Year: " + car.getYearOfManufacture());
                    System.out.println("Number of Doors: " + car.getNumberOfDoors());
                    System.out.println("Fuel Type: " + car.getFuelType());
                    System.out.println("");
                    break;
                case 2:
                    System.out.println("Motorcycle Details:");
                    System.out.println("Make: " + motorcycle.getMake());
                    System.out.println("Model: " + motorcycle.getModel());
                    System.out.println("Year: " + motorcycle.getYearOfManufacture());
                    System.out.println("Number of Wheels: " + motorcycle.getNumberOfWheels());
                    System.out.println("Type of Motorcycle: " + motorcycle.getTypeOfMotorcycle());
                    System.out.println("");
                    break;
                case 3:
                    System.out.println("Truck Details:");
                    System.out.println("Make: " + truck.getMake());
                    System.out.println("Model: " + truck.getModel());
                    System.out.println("Year: " + truck.getYearOfManufacture());
                    System.out.println("Cargo Capacity: " + truck.getCargoCapacity() + " tons");
                    System.out.println("Transmission Type: " + truck.getTransmissionType());
                    System.out.println("");
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        }
    }
}


interface Vehicle {
    String getMake();
    String getModel();
    int getYearOfManufacture();
}
interface CarVehicle {
    void setNumberOfDoors(int numberOfDoors);
    int getNumberOfDoors();

    void setFuelType(String fuelType);
    String getFuelType();
}

class Car implements Vehicle, CarVehicle {
    private String make;
    private String model;
    private int yearOfManufacture;
    private int numberOfDoors;
    private String fuelType;

    public Car(String make, String model, int yearOfManufacture) {
        this.make = make;
        this.model = model;
        this.yearOfManufacture = yearOfManufacture;
    }

    @Override
    public String getMake() {
        return make;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    @Override
    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    @Override
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public String getFuelType() {
        return fuelType;
    }
}


interface MotorVehicle {
    void setNumberOfWheels(int numberOfWheels);
    int getNumberOfWheels();

    void setTypeOfMotorcycle(String type);
    String getTypeOfMotorcycle();
}

class Motorcycle implements Vehicle, MotorVehicle {
    private String make;
    private String model;
    private int yearOfManufacture;
    private int numberOfWheels;
    private String typeOfMotorcycle;

    public Motorcycle(String make, String model, int yearOfManufacture) {
        this.make = make;
        this.model = model;
        this.yearOfManufacture = yearOfManufacture;
    }

    @Override
    public String getMake() {
        return make;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    @Override
    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    @Override
    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    @Override
    public void setTypeOfMotorcycle(String type) {
        this.typeOfMotorcycle = type;
    }

    @Override
    public String getTypeOfMotorcycle() {
        return typeOfMotorcycle;
    }
}


interface TruckVehicle {
    void setCargoCapacity(double cargoCapacity);
    double getCargoCapacity();

    void setTransmissionType(String transmissionType);
    String getTransmissionType();
}

class Truck implements Vehicle, TruckVehicle {
    private String make;
    private String model;
    private int yearOfManufacture;
    private double cargoCapacity;
    private String transmissionType;

    public Truck(String make, String model, int yearOfManufacture) {
        this.make = make;
        this.model = model;
        this.yearOfManufacture = yearOfManufacture;
    }

    @Override
    public String getMake() {
        return make;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    @Override
    public void setCargoCapacity(double cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public double getCargoCapacity() {
        return cargoCapacity;
    }

    @Override
    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    @Override
    public String getTransmissionType() {
        return transmissionType;
    }
}

class Inputs {
    public static int getIntInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        int input = 0;
        try {
            input = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter an integer.");
            Inputs.getIntInput(prompt);
        }
        return input;
    }
}