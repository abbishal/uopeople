import java.text.SimpleDateFormat;
import java.util.Date;

public class Clock {
    private volatile boolean running = true;
    String currentTime;

    public void start() {
        // Creating a thread for updating time in the background
        Thread updateTimeThread = new Thread(() -> {
            while (running) {
                updateTime();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        // Create a thread for printing time to console
        Thread printTimeThread = new Thread(() -> {
            while (running) {
                displayTime();
                try {
                    Thread.sleep(1000); // Update every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Setting higher priority for display
        printTimeThread.setPriority(Thread.MAX_PRIORITY);

        // Starting threads
        updateTimeThread.start();
        printTimeThread.start();
    }

    private void updateTime() {
        // Method to update the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        currentTime = sdf.format(new Date());
    }

    private void displayTime() {
        // Method to display the current time
        if (currentTime != null){
            System.out.println("Current time: " + currentTime);
        }
    }

    public void stop() {
        running = false;
    }

    public static void main(String[] args) {
        Clock clock = new Clock();
        clock.start();

        // Run for 10 seconds for example
        try {
            Thread.sleep(10000); 
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        clock.stop();
    }
}
