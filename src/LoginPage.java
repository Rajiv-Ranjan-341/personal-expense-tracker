import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoginPage extends JPanel {
    public static int currentUserId = -1; // Global variable to store logged-in user ID
    private Image backgroundImage;

    public LoginPage(CardLayout cardLayout, JPanel mainPanel) {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("image/login.jpg")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        setOpaque(false); // Make the panel transparent

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        // Back Button (top-left corner)
        // JButton backButton = Helper.createEnhancedButton("Back");
        // backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home Page"));
        // headerPanel.add(backButton, BorderLayout.WEST);

        // Header Label (centered)
        // JLabel header = Helper.createHeader("Login");
        // headerPanel.add(header, BorderLayout.CENTER);

        // Add the header panel to the top of the main panel
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel (to center the form on the right)
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout()); // Use BorderLayout for right alignment
        mainContentPanel.setOpaque(false);
        mainContentPanel.setBorder(new EmptyBorder(100, 100, 100, 100)); // Add padding

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(30, 30, 30, 30)); // Smaller padding
        formPanel.setBackground(new Color(255, 255, 255, 150)); // Semi-transparent background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Add padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make components fill horizontally

        // Username Field
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 16)); // Smaller font
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblUsername, gbc);

        JTextField txtUsername = new JTextField(20); // Smaller width
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(txtUsername, gbc);

        // Password Field
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 16)); // Smaller font
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblPassword, gbc);

        JPasswordField txtPassword = new JPasswordField(20); // Smaller width
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(txtPassword, gbc);

        // Login Button
        JButton btnLogin = Helper.createEnhancedButton("Login");
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String[] credentials = JDBC.getUserCredentials(username);
                if (credentials != null && credentials[2].equals(password)) {
                    currentUserId = Integer.parseInt(credentials[0]); // Set the logged-in user ID
                    JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(mainPanel, "Home Page"); // Redirect to Home Page
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(btnLogin, gbc);

        // Add a link to Register page
        JButton btnRegisterLink = new JButton("Don't have an account? Register");
        btnRegisterLink.setBorder(BorderFactory.createEmptyBorder());
        btnRegisterLink.setContentAreaFilled(false);
        btnRegisterLink.setForeground(Color.BLUE);
        btnRegisterLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegisterLink.addActionListener(e -> cardLayout.show(mainPanel, "Register"));

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(btnRegisterLink, gbc);

        // Add the form panel to the right side of the main content panel
        mainContentPanel.add(formPanel, BorderLayout.EAST);

        // Add the main content panel to the center of the main panel
        add(mainContentPanel, BorderLayout.CENTER);
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