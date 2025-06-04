import javax.swing.*;
import java.awt.*;

public class Helper {

    // Create a gradient background panel
    public static JPanel createGradientPanel() {
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(135, 206, 250), 0, getHeight(), Color.WHITE);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setLayout(new BorderLayout());
        return gradientPanel;
    }
    // Create a styled header label
    public static JLabel createHeader(String title) {
        JLabel header = new JLabel(title, JLabel.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(0, 123, 255));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 26));
        header.setPreferredSize(new Dimension(1000, 60));
        return header;
    }

    // Create an enhanced button with hover effects
    public static JButton createEnhancedButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 219, 88));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 144, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 219, 88));
            }
        });

        return button;
    }

       // Create a user menu with Profile and Logout options
       public static JPopupMenu createUserMenu(CardLayout cardLayout, JPanel mainPanel) {
        JPopupMenu userMenu = new JPopupMenu();

        if (LoginPage.currentUserId == -1) {
            // If no user is logged in, show Login option
            JMenuItem loginItem = new JMenuItem("Login");
            loginItem.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
            userMenu.add(loginItem);
        } else {
            // If a user is logged in, show Profile and Logout options
            JMenuItem profileItem = new JMenuItem("Profile");
            profileItem.addActionListener(e -> cardLayout.show(mainPanel, "User Profile"));

            JMenuItem logoutItem = new JMenuItem("Logout");
            logoutItem.addActionListener(e -> {
                LoginPage.currentUserId = -1; // Reset the logged-in user ID
                cardLayout.show(mainPanel, "Login"); // Redirect to Login page
            });

            userMenu.add(profileItem);
            userMenu.add(logoutItem);
        }

        return userMenu;
    }
}