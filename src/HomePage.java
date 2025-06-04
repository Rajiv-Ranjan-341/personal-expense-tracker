import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HomePage extends JPanel {

    private Image backgroundImage;

    public HomePage(CardLayout cardLayout, JPanel mainPanel) {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("image/home2.jpg")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        setOpaque(false); // Make the panel transparent

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        // Header Label (Transparent)
        JLabel header = new JLabel("Personal Expense Tracker", JLabel.CENTER);
        header.setOpaque(false); // Make the label transparent
        header.setForeground(Color.WHITE); // Set text color
        header.setFont(new Font("Arial", Font.BOLD, 26)); // Set font
        headerPanel.add(header, BorderLayout.CENTER);

        // User Profile Button (Transparent)
        JButton userProfileButton = new JButton("O_O");
        userProfileButton.setOpaque(false); // Make the button transparent
        userProfileButton.setContentAreaFilled(false); // Remove background fill
        userProfileButton.setBorderPainted(false); // Remove border
        userProfileButton.setForeground(Color.WHITE); // Set text color
        userProfileButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        JPopupMenu userMenu = Helper.createUserMenu(cardLayout, mainPanel);

        userProfileButton.addActionListener(e -> {
            if (LoginPage.currentUserId != -1) {
                cardLayout.show(mainPanel, "User Profile"); // Navigate to User Profile page
            } else {
                cardLayout.show(mainPanel, "Login"); // Redirect to Login page if not logged in
            }
        });

        headerPanel.add(userProfileButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Center Content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        JLabel welcomeLabel = new JLabel("Welcome to Expense Tracker", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(255, 255, 255));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("Select an option below to get started:", JLabel.CENTER);
        subLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subLabel.setForeground(new Color(255, 255, 255));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(subLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(800, 50));

        JButton btnAddExpense = Helper.createEnhancedButton("Add Expense");
        JButton btnViewExpenseList = Helper.createEnhancedButton("View Expense List");
        JButton btnViewReports = Helper.createEnhancedButton("View Reports");

        btnAddExpense.addActionListener(e -> cardLayout.show(mainPanel, "Add Expense"));
        btnViewExpenseList.addActionListener(e -> cardLayout.show(mainPanel, "View Expense List"));
        btnViewReports.addActionListener(e -> cardLayout.show(mainPanel, "View Reports"));

        buttonPanel.add(btnAddExpense);
        buttonPanel.add(btnViewExpenseList);
        buttonPanel.add(btnViewReports);

        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);
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