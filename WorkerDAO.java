import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class WorkerDAO {
    private Connection connection;
    private static final Scanner scanner = new Scanner(System.in);
    private static final WorkerDAO workerDAO = new WorkerDAO(); // Add this line
    
    public WorkerDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Method to fetch all workers from the database
    public List<Worker> getAllWorkers() {
        List<Worker> workers = new ArrayList<>();
        String query = "SELECT * FROM workers";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Worker worker = new Worker(id, name, email, password);
                workers.add(worker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workers;
    }
    
    // Method to fetch a worker by email from the database
    public Worker getWorkerByEmail(String email) {
        String query = "SELECT * FROM workers WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                return new Worker(id, name, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Method to insert a new worker into the database
    public void addWorker(Worker worker) {
        String query = "INSERT INTO workers (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, worker.getName());
            statement.setString(2, worker.getEmail());
            statement.setString(3, worker.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Method to update an existing worker in the database
    public void updateWorker(Worker worker) {
        String query = "UPDATE workers SET name = ?, email = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, worker.getName());
            statement.setString(2, worker.getEmail());
            statement.setString(3, worker.getPassword());
            statement.setInt(4, worker.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Method to delete a worker from the database
    public void deleteWorker(int id) {
        String query = "DELETE FROM workers WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
