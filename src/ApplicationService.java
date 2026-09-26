import java.sql.*;
import java.util.*;

public class ApplicationService {
    public boolean apply(int jobId, int candidateId) throws SQLException {
        String sql="INSERT INTO applications(job_id,candidate_id,status) SELECT ?,?,'Applied' WHERE EXISTS(SELECT 1 FROM jobs WHERE job_id=?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,jobId); p.setInt(2,candidateId); p.setInt(3,jobId);
            return p.executeUpdate()==1;
        }
    }

    public void viewCandidateApplications(int candidateId) throws SQLException {
        String sql="SELECT a.application_id,j.job_title,j.company_name,a.status,a.applied_date FROM applications a JOIN jobs j ON a.job_id=j.job_id WHERE a.candidate_id=? ORDER BY a.applied_date DESC";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,candidateId); try(ResultSet r=p.executeQuery()){
                boolean any=false;
                while(r.next()){ any=true; System.out.printf("Application #%d | %s at %s | %s | %s%n",
                    r.getInt(1),r.getString(2),r.getString(3),r.getString(4),r.getTimestamp(5)); }
                if(!any) System.out.println("No applications yet.");
            }
        }
    }

    public void viewEmployerApplications(int employerId) throws SQLException {
        String sql="SELECT a.application_id,j.job_title,u.name,u.email,a.status,a.applied_date FROM applications a JOIN jobs j ON a.job_id=j.job_id JOIN users u ON a.candidate_id=u.user_id WHERE j.employer_id=? ORDER BY a.applied_date DESC";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,employerId); try(ResultSet r=p.executeQuery()){
                boolean any=false;
                while(r.next()){ any=true; System.out.printf("App #%d | Job: %s | Candidate: %s (%s) | Status: %s | %s%n",
                    r.getInt(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),r.getTimestamp(6)); }
                if(!any) System.out.println("No applications for your jobs.");
            }
        }
    }

    public boolean updateStatus(int applicationId, int employerId, String status) throws SQLException {
        if(!Arrays.asList("Applied","Shortlisted","Rejected").contains(status))
            throw new IllegalArgumentException("Invalid status.");
        String sql="UPDATE applications a JOIN jobs j ON a.job_id=j.job_id SET a.status=? WHERE a.application_id=? AND j.employer_id=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,status); p.setInt(2,applicationId); p.setInt(3,employerId); return p.executeUpdate()==1;
        }
    }
}
