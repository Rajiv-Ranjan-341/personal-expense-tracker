import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ViewExpenseListPage extends JPanel {
    private Image backgroundImage;

    private DefaultTableModel model;
    private JTable expenseTable;

    public ViewExpenseListPage(CardLayout cardLayout, JPanel mainPanel) {

        try {
            backgroundImage = ImageIO.read(new File("image/viewat.png")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new BorderLayout());
        setOpaque(false);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        // Back Button (Transparent)
        JButton backButton = new JButton("Back");
        backButton.setOpaque(false); // Make the button transparent
        backButton.setContentAreaFilled(false); // Remove background fill
        backButton.setBorderPainted(false); // Remove border
        backButton.setForeground(Color.BLACK); // Set text color
        backButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home Page"));
        headerPanel.add(backButton, BorderLayout.WEST);

        // Header Label (Transparent)
        JLabel header = new JLabel("Expense List", JLabel.CENTER);
        header.setOpaque(false); // Make the label transparent
        header.setForeground(Color.BLACK); // Set text color
        header.setFont(new Font("Arial", Font.BOLD, 26)); // Set font
        headerPanel.add(header, BorderLayout.CENTER);

        // User Profile Button (Transparent)
        JButton userProfileButton = new JButton("O_O");
        userProfileButton.setOpaque(false); // Make the button transparent
        userProfileButton.setContentAreaFilled(false); // Remove background fill
        userProfileButton.setBorderPainted(false); // Remove border
        userProfileButton.setForeground(Color.BLACK); // Set text color
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

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout()); // Use BorderLayout for the main content
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        // Title Label
        JLabel title = new JLabel("All Recorded Expenses:", JLabel.LEFT);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.DARK_GRAY);

        // Add title to the top of the content panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align title to the left
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        contentPanel.add(titlePanel, BorderLayout.NORTH);

        // Create table model
        model = new DefaultTableModel(new Object[]{"Date", "Category", "Amount", "Description"}, 0);
        expenseTable = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(expenseTable);

        // Load data from database for the logged-in user
        refreshTable();

        // Add table to the center of the content panel
        contentPanel.add(tableScroll, BorderLayout.CENTER);

        // Refresh Button (Smaller Size)
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller font size
        refreshButton.setBackground(new Color(255, 219, 88)); // Background color
        refreshButton.setForeground(Color.WHITE); // Text color
        refreshButton.setFocusPainted(false); // Remove focus border
        refreshButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Smaller padding
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        refreshButton.addActionListener(e -> refreshTable());

        // Reduce size of the refresh button
        refreshButton.setPreferredSize(new Dimension(80, 30));

        // Panel to hold the refresh button at the bottom-right
        JPanel refreshButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align button to the right
        refreshButtonPanel.setOpaque(false);
        refreshButtonPanel.add(refreshButton);

        // Add refresh button panel to the bottom of the content panel
        contentPanel.add(refreshButtonPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);
    }

    // Method to refresh the table data
    private void refreshTable() {
        model.setRowCount(0); // Clear existing data

        // Load data from database for the logged-in user
        if (LoginPage.currentUserId != -1) {
            List<String[]> expenses = JDBC.getExpenses(LoginPage.currentUserId);
            for (String[] expense : expenses) {
                model.addRow(new Object[]{expense[0], expense[1], expense[2], expense[3]});
            }
        }
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