import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RegisterPage extends JPanel {
    private Image backgroundImage;

    public RegisterPage(CardLayout cardLayout, JPanel mainPanel) {
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

        // // Back Button (top-left corner)
        // JButton backButton = Helper.createEnhancedButton("Back");
        // backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home Page"));
        // headerPanel.add(backButton, BorderLayout.WEST);

        // Header Label (centered)
        // JLabel header = Helper.createHeader("Register");
        // headerPanel.add(header, BorderLayout.CENTER);

        // Add the header panel to the top of the main panel
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel (to center the form on the right)
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout()); // Use BorderLayout for right alignment
        mainContentPanel.setOpaque(false);
        mainContentPanel.setBorder(new EmptyBorder(100, 100, 100, 100)); // Increased padding

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(30, 30, 30, 30)); // Increased padding
        formPanel.setBackground(new Color(255, 255, 255, 150)); // Semi-transparent background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Increased padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make components fill horizontally

        // Username Field
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 16)); // Increased font size
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblUsername, gbc);

        JTextField txtUsername = new JTextField(20); // Increased width
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(txtUsername, gbc);

        // Password Field
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 16)); // Increased font size
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblPassword, gbc);

        JPasswordField txtPassword = new JPasswordField(20); // Increased width
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(txtPassword, gbc);

        // Confirm Password Field
        JLabel lblConfirmPassword = new JLabel("Confirm Password:");
        lblConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 16)); // Increased font size
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblConfirmPassword, gbc);

        JPasswordField txtConfirmPassword = new JPasswordField(20); // Increased width
        txtConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(txtConfirmPassword, gbc);

        // Register Button
        JButton btnRegister = Helper.createEnhancedButton("Register");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 14)); // Increased font size
        btnRegister.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!validateInput(username, password)) {
                JOptionPane.showMessageDialog(this, "Invalid username or password format.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int userId = JDBC.addUser(username, password); // Store plain text password
                if (userId != -1) {
                    LoginPage.currentUserId = userId; // Set the logged-in user ID
                    JOptionPane.showMessageDialog(this, "Registration Successful!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(mainPanel, "Home Page"); // Redirect to Home Page
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(btnRegister, gbc);

        // Add a link to Login page
        JButton btnLoginLink = new JButton("Already have an account? Login");
        btnLoginLink.setBorder(BorderFactory.createEmptyBorder());
        btnLoginLink.setContentAreaFilled(false);
        btnLoginLink.setForeground(Color.BLUE);
        btnLoginLink.setFont(new Font("Arial", Font.PLAIN, 14)); // Increased font size
        btnLoginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLoginLink.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(btnLoginLink, gbc);

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

    private boolean validateInput(String username, String password) {
        // Validate username and password
        return username.length() >= 5 && password.length() >= 8;
    }
}