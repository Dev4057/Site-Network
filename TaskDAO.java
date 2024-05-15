import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class TaskDAO {
    private Connection connection;

    public TaskDAO() {
        // Initialize database connection
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Task> getTasksForWorker(int workerId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks WHERE assigned_worker_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, workerId);
            ResultSet resultSet = statement.executeQuery();
            
            // Print column headers
            System.out.printf("%-5s %-20s %-20s %-20s %-15s%n", "ID", "Description", "TOken NO.", "Deadline", "Status");
            System.out.println("-----------------------------------------------------------------------------");

            while (resultSet.next()) {
                int taskId = resultSet.getInt("id");
                String description = resultSet.getString("description");
                int assignedWorkerId = resultSet.getInt("assigned_worker_id");
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime();
                TaskStatus status = TaskStatus.valueOf(resultSet.getString("status"));

                // Print each task
                System.out.printf("%-5d %-20s %-20d %-20s %-15s%n", taskId, description, assignedWorkerId, deadline, status);
    
                Task task = new Task(description, assignedWorkerId, deadline, status);
                task.setId(taskId);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
    
    
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        
        String query = "SELECT * FROM tasks";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int taskId = resultSet.getInt("id");
                String description = resultSet.getString("description");
                int assignedWorkerId = resultSet.getInt("assigned_worker_id");
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime(); // Ensure this is not null
                TaskStatus status = TaskStatus.valueOf(resultSet.getString("status"));
                    
                Task task = new Task(description, assignedWorkerId, deadline, status);
                task.setId(taskId);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return tasks;
    }
        
    public void addTask(Task task) throws SQLException {
        String query = "INSERT INTO tasks (description, assigned_worker_id, deadline, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, task.getDescription());
            statement.setInt(2, task.getAssignedWorkerId());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(task.getDeadline()));
            statement.setString(4, task.getStatus().toString());
            statement.executeUpdate();
        }
    }
}
