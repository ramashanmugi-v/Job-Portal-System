import java.math.BigDecimal;

public class Job {
    public int jobId, employerId;
    public String title, company, location, jobType, description, skills;
    public BigDecimal salary;

    public Job(int jobId, int employerId, String title, String company,
               String location, BigDecimal salary, String jobType,
               String description, String skills) {
        this.jobId = jobId; this.employerId = employerId; this.title = title;
        this.company = company; this.location = location; this.salary = salary;
        this.jobType = jobType; this.description = description; this.skills = skills;
    }
    @Override public String toString() {
        return "\nJob ID: " + jobId + "\nTitle: " + title + "\nCompany: " + company
            + "\nLocation: " + location + "\nSalary: " + salary + "\nType: " + jobType
            + "\nSkills: " + skills + "\nDescription: " + description
            + "\n----------------------------------------";
    }
}
