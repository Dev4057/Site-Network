import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboard {
    private static final Scanner scanner = new Scanner(System.in);
    private static final WorkerDAO workerDAO = new WorkerDAO();
    private static final TaskDAO taskDAO = new TaskDAO();
    
    public static void showDashboard() {
        System.out.println("Admin Dashboard");
        while (true) {
            System.out.println("1. View Workers");
            System.out.println("2. Add Worker");
            System.out.println("3. View Tasks");
            System.out.println("4. Add Task");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    viewWorkers();
                    break;
                case 2:
                    addWorker();
                    break;
                case 3:
                    viewTasks();
                    break;
                case 4:
                    addTask();
                    break;
                case 5:
                    System.out.println("Logged out!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void viewWorkers() {
        System.out.println("View Workers");
        List<Worker> workers = workerDAO.getAllWorkers();
        for (Worker worker : workers) {
            System.out.println(worker);
        }
    }
    
    private static void addWorker() {
        System.out.println("Add Worker");
        System.out.print("Enter worker name: ");
        String name = scanner.nextLine();
        System.out.print("Enter worker email: ");
        String email = scanner.nextLine();
        System.out.print("Enter worker password: ");
        String password = scanner.nextLine();
        // You might need to generate an ID or retrieve it from somewhere
        int id = 1; // Example ID
        Worker worker = new Worker(id, name, email, password);
        workerDAO.addWorker(worker);
        System.out.println("Worker added successfully!");
    }
    
    private static void viewTasks() {
        System.out.println("View Tasks");
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "Dev@9007");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Print column headers
            System.out.printf("%-5s %-30s %-20s %-20s %-15s%n", "ID", "Description", "Token NO.", "Deadline", "Status");
            System.out.println("-----------------------------------------------------------------------------");

            // Print each task
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                int assignedWorkerId = resultSet.getInt("assigned_worker_id");
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime();
                String status = resultSet.getString("status");

                // Ensure description doesn't break the formatting
                if (description.length() > 30) {
                    description = description.substring(0, 27) + "...";
                }

                System.out.printf("%-5d %-30s %-20d %-20s %-15s%n", id, description, assignedWorkerId, deadline, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void addTask() {
        System.out.println("Add Task");
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        
        System.out.print("Enter Token NO. to be Assigned: ");
        int assignedWorkerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        // Prompting for year, month, day, hour, and minute separately
        int year, month, day, hour, minute;
        do {
            System.out.print("Enter year for deadline (YYYY): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid year (YYYY): ");
                scanner.next(); // discard non-integer input
            }
            year = scanner.nextInt();
            scanner.nextLine(); // Consume newline after integer input
        } while (year < LocalDateTime.now().getYear()); // Ensuring year is not in the past
    
        do {
            System.out.print("Enter month for deadline (MM): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid month (MM): ");
                scanner.next(); // discard non-integer input
            }
            month = scanner.nextInt();
        } while (month < 1 || month > 12);
    
        do {
            System.out.print("Enter day for deadline (DD): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid day (DD): ");
                scanner.next(); // discard non-integer input
            }
            day = scanner.nextInt();
        } while (day < 1 || day > 31);
    
        do {
            System.out.print("Enter hour for deadline (HH): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid hour (HH): ");
                scanner.next(); // discard non-integer input
            }
            hour = scanner.nextInt();
        } while (hour < 0 || hour > 23);
    
        do {
            System.out.print("Enter minute for deadline (mm): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid minute (mm): ");
                scanner.next(); // discard non-integer input
            }
            minute = scanner.nextInt();
        } while (minute < 0 || minute > 59);
        
        // Constructing LocalDateTime object
        LocalDateTime deadline = LocalDateTime.of(year, month, day, hour, minute);
        
        TaskStatus status = TaskStatus.OPEN;
        Task task = new Task(description, assignedWorkerId, deadline, status);
        
        try {
            taskDAO.addTask(task);
            System.out.println("Task added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding task: " + e.getMessage());
        }   
    }
    
}
