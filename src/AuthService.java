import java.sql.*;

public class AuthService {
    public boolean register(String name, String email, String password, String role) throws SQLException {
        if (!role.equals("Candidate") && !role.equals("Employer"))
            throw new IllegalArgumentException("Role must be Candidate or Employer.");
        String sql = "INSERT INTO users(name,email,password,role) VALUES(?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, name); p.setString(2, email); p.setString(3, password); p.setString(4, role);
            return p.executeUpdate() == 1;
        }
    }

    public User login(String email, String password) throws SQLException {
        String sql = "SELECT user_id,name,email,role FROM users WHERE email=? AND password=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, email); p.setString(2, password);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) return new User(r.getInt("user_id"), r.getString("name"),
                        r.getString("email"), r.getString("role"));
            }
        }
        return null;
    }
}
