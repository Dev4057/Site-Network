import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AuthenticationService authService = new AuthenticationService();
    private static String loggedInUserEmail; // To store the email of the logged-in user
    
    public static void main(String[] args) {
        showMainMenu();
    }
    private static void showMainMenu() {
        System.out.println("*Welcome to SITE NETWORK*");
        while (true) {
            System.out.println("1. Login as Worker");
            System.out.println("2. Login as Admin");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    login(false);
                    break;
                case 2:
                    login(true);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void login(boolean isAdmin) {
        System.out.println("Login:");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (authService.authenticateUser(email, password)) {
            System.out.println("Authentication successful!");
            loggedInUserEmail = email; // Store the email of the logged-in user
            if (isAdmin) {
                AdminDashboard.showDashboard();
            } else {
                WorkerDashboard.showDashboard();
            }
        } else {
            System.out.println("Authentication failed! Invalid email or password.");
        }
    } 
}
