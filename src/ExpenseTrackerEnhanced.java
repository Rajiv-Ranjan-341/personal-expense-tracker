import javax.swing.*;
import java.awt.*;

public class ExpenseTrackerEnhanced extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public ExpenseTrackerEnhanced() {
        // Frame setup
        setTitle("Personal Expense Tracker");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon image = new ImageIcon("image/logo.jpg");
        setIconImage(image.getImage());

        // Apply gradient background
        setContentPane(Helper.createGradientPanel());

        // CardLayout for navigation
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false); // Transparent background

        // Add pages
        mainPanel.add(new HomePage(cardLayout, mainPanel), "Home Page");
        mainPanel.add(new AddExpensePage(cardLayout, mainPanel), "Add Expense");
        mainPanel.add(new ViewExpenseListPage(cardLayout, mainPanel), "View Expense List");
        mainPanel.add(new ViewReportsPage(cardLayout, mainPanel), "View Reports");
        mainPanel.add(new RegisterPage(cardLayout, mainPanel), "Register");
        mainPanel.add(new LoginPage(cardLayout, mainPanel), "Login");
        mainPanel.add(new UserProfilePage(cardLayout, mainPanel), "User Profile");

        add(mainPanel);

        // Restore login state and show the appropriate page
        if (LoginPage.currentUserId != -1) {
            cardLayout.show(mainPanel, "Home Page"); // User is already logged in
        }
        else {
            cardLayout.show(mainPanel, "Login"); // Show Login page
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExpenseTrackerEnhanced app = new ExpenseTrackerEnhanced();
            app.setVisible(true);
        });
    }
}