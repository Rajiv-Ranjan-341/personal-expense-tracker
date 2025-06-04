import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UserProfilePage extends JPanel {
    private Image backgroundImage;

    public UserProfilePage(CardLayout cardLayout, JPanel mainPanel) {
           try {
            backgroundImage = ImageIO.read(new File("image/profile.jpg")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new BorderLayout());
        setOpaque(false);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setOpaque(false); // Make the button transparent
        backButton.setContentAreaFilled(false); // Remove background fill
        backButton.setBorderPainted(false); // Remove border
        backButton.setForeground(Color.WHITE); // Set text color
        backButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home Page"));
        headerPanel.add(backButton, BorderLayout.WEST);

        // Header Label
        // JLabel header = Helper.createHeader("User Profile");
        // headerPanel.add(header, BorderLayout.CENTER);

        JLabel header = new JLabel("User Profile", JLabel.CENTER);
        header.setOpaque(false); // Make the label transparent
        header.setForeground(Color.WHITE); // Set text color
        header.setFont(new Font("Arial", Font.BOLD, 26)); // Set font
        headerPanel.add(header, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        // JLabel title = new JLabel("User Profile", JLabel.CENTER);
        // title.setFont(new Font("Arial", Font.BOLD, 24));
        // title.setForeground(Color.DARK_GRAY);

        // Fetch user details from the database
        // String[] userDetails = JDBC.getUserDetails(LoginPage.currentUserId);
        // String username = userDetails != null ? userDetails[0] : "N/A";
        // String email = userDetails != null ? userDetails[1] : "N/A";

        // JLabel lblUsername = new JLabel("Username: " + username);
        // lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));

        // JLabel lblEmail = new JLabel("Email: " + email);
        // lblEmail.setFont(new Font("Arial", Font.PLAIN, 16));

        // Fetch username from DB
        // String username = JDBC.getUserDetails(LoginPage.currentUserId);

        // // If username is null, show a default message
        // if (username == null) {
        //     username = "ping_pong";
        // }

        // // Display the username
        // JLabel lblUsername = new JLabel("Username: " + username);
        // lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));

        // contentPanel.add(title);
        // contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        // contentPanel.add(lblUsername);
        // contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        // contentPanel.add(btnLogout);

        // Logout Button
        JButton btnLogout = Helper.createEnhancedButton("Logout");
        btnLogout.addActionListener(e -> {
            LoginPage.currentUserId = -1; // Reset the logged-in user ID
            cardLayout.show(mainPanel, "Login"); // Redirect to Login page
        });

        // contentPanel.add(title);
        // contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        // contentPanel.add(lblUsername);
        // contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        // // contentPanel.add(lblEmail);
        // contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(btnLogout);

        add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}