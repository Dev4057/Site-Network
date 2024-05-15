import java.time.LocalDateTime;

public class Task {
    private int id;
    private String description;
    private int assignedWorkerId;
    private LocalDateTime deadline;
    private TaskStatus status;
    
    public Task(String description, int assignedWorkerId, LocalDateTime deadline, TaskStatus status) {
        this.description = description;
        this.assignedWorkerId = assignedWorkerId;
        this.deadline = deadline;
        this.status = status;
    }
    
    // Getters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getAssignedWorkerId() {
        return assignedWorkerId;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public TaskStatus getStatus() {
        return status;
    }
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAssignedWorkerId(int assignedWorkerId) {
        this.assignedWorkerId = assignedWorkerId;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
