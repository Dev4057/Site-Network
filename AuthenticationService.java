public class AuthenticationService {
    private WorkerDAO workerDAO;
    
    public AuthenticationService() {
        workerDAO = new WorkerDAO();
    }
    
    public boolean authenticateUser(String email, String password) {
        Worker worker = workerDAO.getWorkerByEmail(email);
        if (worker != null && worker.getPassword().equals(password)) {
            return true; // Authentication successful
        } else {
            return false; // Authentication failed
        }
    }
}
