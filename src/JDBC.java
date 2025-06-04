import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBC {

    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_db?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "rajiv";

    // Get a database connection
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Database connection successful!");
        return conn;
    }

  // Fetch user details by user ID
  public static String getUserDetails(int userId) {
    String query = "SELECT username FROM users WHERE id = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            String username = rs.getString("username");
            System.out.println("[DEBUG] Fetched username: " + username); // Log fetched username
            return username;
        } else {
            System.out.println("[DEBUG] No user found with ID: " + userId); // Log if no user found
        }
    } catch (SQLException e) {
        System.err.println("[ERROR] SQL Error in getUserDetails: " + e.getMessage()); // Better error logging
        e.printStackTrace();
    }
    return null;
}

    // Add an expense to the database (with user_id)
    public static void addExpense(int userId, String date, String category, double amount, String description) {
        String query = "INSERT INTO transactions (user_id, date, category, amount, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, date);
            pstmt.setString(3, category);
            pstmt.setDouble(4, amount);
            pstmt.setString(5, description);
            pstmt.executeUpdate();
            System.out.println("Expense added to database for user ID: " + userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all expenses for a specific user
    public static List<String[]> getExpenses(int userId) {
        List<String[]> expenses = new ArrayList<>();
        String query = "SELECT date, category, amount, description FROM transactions WHERE user_id = ? ORDER BY date DESC";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String date = rs.getString("date");
                String category = rs.getString("category");
                String amount = rs.getString("amount");
                String description = rs.getString("description");
                expenses.add(new String[]{date, category, amount, description});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    // Add a new user to the database
    public static int addUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password); // Store plain text password
            pstmt.executeUpdate();

            // Get the generated user ID
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                System.out.println("User registered successfully with ID: " + rs.getInt(1));
                return rs.getInt(1); // Return the new user ID
            }
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
        }
        return -1; // Return -1 if user creation fails
    }
    
    public static String[] getUserCredentials(String username) {
        String query = "SELECT id, username, password FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String userId = rs.getString("id");
                String storedUsername = rs.getString("username");
                String storedPassword = rs.getString("password");
                return new String[]{userId, storedUsername, storedPassword};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}