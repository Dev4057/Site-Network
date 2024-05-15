import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class WorkerDashboard {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskDAO taskDAO = new TaskDAO();

    public static void showDashboard() {
        System.out.println("Welcome to Worker Dashboard!");
        while (true) {
            System.out.println("1. View Tasks");
            System.out.println("2. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    viewTasks();
                    break;
                case 2:
                    System.out.println("Logged out!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewTasks() {
        System.out.println("Viewing Tasks:");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        
        // Query the database to get the worker ID based on the email
        int workerId = getWorkerIdByEmail(email);
        System.out.println("Retrieved Worker ID: " + workerId); // Debugging statement
        
        // Check if the worker ID is valid
        if (workerId != -1) {
            System.out.println("Retrieving tasks for worker ID: " + workerId);
            // Retrieve tasks for the worker ID
            taskDAO.getTasksForWorker(workerId);
        } else {
            System.out.println("No worker found with the provided email.");
        }
    }
    

    private static int getWorkerIdByEmail(String email) {
        int workerId = -1;
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT id FROM workers WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    workerId = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return workerId;
    }
    
}
