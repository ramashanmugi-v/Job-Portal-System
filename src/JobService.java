import java.sql.*;
import java.math.BigDecimal;
import java.util.*;

public class JobService {
    public boolean addJob(int employerId, String title, String company, String location,
            BigDecimal salary, String type, String description, String skills) throws SQLException {
        String sql = "INSERT INTO jobs(employer_id,job_title,company_name,location,salary,job_type,description,required_skills) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            p.setInt(1,employerId); p.setString(2,title); p.setString(3,company); p.setString(4,location);
            p.setBigDecimal(5,salary); p.setString(6,type); p.setString(7,description); p.setString(8,skills);
            return p.executeUpdate()==1;
        }
    }

    public List<Job> listJobs(String keyword, String location, String type) throws SQLException {
        String sql = "SELECT * FROM jobs WHERE (?='' OR LOWER(CONCAT(job_title,' ',company_name,' ',required_skills)) LIKE ?) AND (?='' OR LOWER(location) LIKE ?) AND (?='' OR LOWER(job_type)=?) ORDER BY created_at DESC";
        try (Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            String k=keyword.toLowerCase(), l=location.toLowerCase(), t=type.toLowerCase();
            p.setString(1,k); p.setString(2,"%"+k+"%");
            p.setString(3,l); p.setString(4,"%"+l+"%");
            p.setString(5,t); p.setString(6,t);
            return readJobs(p);
        }
    }

    public List<Job> employerJobs(int employerId) throws SQLException {
        try (Connection c=DBConnection.getConnection();
             PreparedStatement p=c.prepareStatement("SELECT * FROM jobs WHERE employer_id=? ORDER BY created_at DESC")) {
            p.setInt(1,employerId); return readJobs(p);
        }
    }

    public boolean updateJob(int jobId, int employerId, String title, String company, String location,
            BigDecimal salary, String type, String description, String skills) throws SQLException {
        String sql="UPDATE jobs SET job_title=?,company_name=?,location=?,salary=?,job_type=?,description=?,required_skills=? WHERE job_id=? AND employer_id=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,title); p.setString(2,company); p.setString(3,location); p.setBigDecimal(4,salary);
            p.setString(5,type); p.setString(6,description); p.setString(7,skills); p.setInt(8,jobId); p.setInt(9,employerId);
            return p.executeUpdate()==1;
        }
    }

    public boolean deleteJob(int jobId, int employerId) throws SQLException {
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("DELETE FROM jobs WHERE job_id=? AND employer_id=?")){
            p.setInt(1,jobId); p.setInt(2,employerId); return p.executeUpdate()==1;
        }
    }

    public Job getJob(int id) throws SQLException {
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("SELECT * FROM jobs WHERE job_id=?")){
            p.setInt(1,id); try(ResultSet r=p.executeQuery()){ return r.next()?toJob(r):null; }
        }
    }

    private List<Job> readJobs(PreparedStatement p) throws SQLException {
        List<Job> jobs=new ArrayList<>();
        try(ResultSet r=p.executeQuery()){ while(r.next()) jobs.add(toJob(r)); }
        return jobs;
    }
    private Job toJob(ResultSet r) throws SQLException {
        return new Job(r.getInt("job_id"),r.getInt("employer_id"),r.getString("job_title"),
            r.getString("company_name"),r.getString("location"),r.getBigDecimal("salary"),
            r.getString("job_type"),r.getString("description"),r.getString("required_skills"));
    }
}
