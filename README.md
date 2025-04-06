
 Site Network [# Employee Management System (EMS)]

## Project Overview

The Employee Management System (EMS) is a Java-based application that provides an efficient way to manage employee data and task assignments within an organization. The system is designed with a three-tier architecture using Java SE, promoting modularity and scalability. It is suitable for both academic projects and real-world small business applications.

This system enables Admins to perform employee and task management operations, while Employees (Workers) can securely log in to view their dashboards (future scope includes viewing tasks and notifications).

## Key Features

- Secure Admin and Worker login system  
- Add, view, and delete employee records  
- Add, view, and delete tasks  
- Assign tasks to employees  
- Separation of concerns with a layered architecture  
- JDBC-based database connectivity  
- UML Class Diagram representation  
- Clean and modular object-oriented codebase

## Technologies Used

Java SE - Application development  
JDBC - Database connectivity  
MySQL or PostgreSQL - Relational database backend  
PlantUML - UML diagram generation  
IntelliJ / VS Code - Java development environment  
Terminal / Command Prompt - CLI-based interaction

## Architecture

The EMS project is structured as follows:

1. Presentation Layer  
   Handles user interaction through a CLI (Command Line Interface).  
   Classes: Main.java, AdminDashboard.java, WorkerDashboard.java

2. Application Layer  
   Contains business logic and services.  
   Classes: AuthenticationService.java, WorkerDAO.java, TaskDAO.java

3. Data Layer  
   Responsible for data representation and database connections.  
   Classes: Worker.java, Task.java, DatabaseConnection.java

## Installation Instructions

1. Clone the Repository  
   Use this command in your terminal:  
   `git clone https://github.com/your-username/employee-management-system.git`  
   Navigate into the folder:  
   `cd employee-management-system`

2. Open the Project  
   Open the folder in IntelliJ IDEA, VS Code, or Eclipse.

3. Set Up Database  
   Create a MySQL or PostgreSQL database.  
   Create the required tables using the following SQL:

```
CREATE TABLE workers (
  id INT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100),
  password VARCHAR(100)
);

CREATE TABLE tasks (
  id INT PRIMARY KEY,
  title VARCHAR(100),
  description TEXT,
  assignedTo INT,
  FOREIGN KEY (assignedTo) REFERENCES workers(id)
);
```

4. Update Database Credentials  
   Open the file `DatabaseConnection.java` and update your database URL, username, and password accordingly.

5. Build and Run  
   Run `Main.java` to start the CLI-based application.

## How to Run

- Start the application by running `Main.java`
- Choose whether to log in as Admin or Worker
- Admin can perform full CRUD operations for employees and tasks
- Worker can log in and view their dashboard (this can be expanded later)
- Exit the application using the menu option or by closing the terminal

## UML Class Diagram

Diagram includes classes like Worker, Task, AuthenticationService, WorkerDAO, and TaskDAO, showing their relationships.  
(Attach or link the UML diagram image here if available)

## Future Improvements

- Add graphical user interface using JavaFX or Swing  
- Implement task status tracking (Completed, Pending)  
- Add email notification for task assignments  
- Support multiple admin roles  
- Add report generation (PDF or Excel)  
- Include change logs and action history  
- Improve input validation and error handling

## Contributors

Devang Gandhi - GitHub: https://github.com/Dev4057


## License

This project is licensed under the MIT License. Refer to the LICENSE file for details.

## Contact

Email:devanggandhi007@gmail.com
LinkedIn: www.linkedin.com/in/devang-gandhi-917304213



---

