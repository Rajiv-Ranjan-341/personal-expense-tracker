import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jdesktop.swingx.JXDatePicker;

public class AddExpensePage extends JPanel {
    private Image backgroundImage;
    public AddExpensePage(CardLayout cardLayout, JPanel mainPanel) {


        try {
            backgroundImage = ImageIO.read(new File("image/add.png")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        setOpaque(false);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        // Back Button
        // JButton backButton = Helper.createEnhancedButton("Back");
        // backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home Page"));
        // headerPanel.add(backButton, BorderLayout.WEST);

        JButton backButton = new JButton("Back");
        backButton.setOpaque(false); // Make the button transparent
        backButton.setContentAreaFilled(false); // Remove background fill
        backButton.setBorderPainted(false); // Remove border
        backButton.setForeground(Color.BLACK); // Set text color
        backButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home Page"));
        headerPanel.add(backButton, BorderLayout.WEST);

        // Back Button with Icon
        // ImageIcon backIcon = new ImageIcon("image/left-arrow.png"); // Replace with the actual path to your icon
        // JButton backButton = new JButton(backIcon); // Create button with icon
        // // backButton.setBorder(BorderFactory.createEmptyBorder()); // Remove button border
        // // backButton.setContentAreaFilled(false); // Make the button background transparent
        // // backButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Set hand cursor on hover
        // // backButton.setToolTipText("Go back to Home Page"); // Add a tooltip for better usability
        // backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home Page")); // Add action listener

        // // headerPanel.add(backButton, BorderLayout.WEST); // Add the button to the header panel

        // Header Label
        // JLabel header = Helper.createHeader("Add Expense");
        // headerPanel.add(header, BorderLayout.CENTER);
        JLabel header = new JLabel("Add Expense", JLabel.CENTER);
        header.setOpaque(false); // Make the label transparent
        header.setForeground(Color.BLACK); // Set text color
        header.setFont(new Font("Arial", Font.BOLD, 26)); // Set font
        headerPanel.add(header, BorderLayout.CENTER);


        // User Profile Button
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
            } 
            else {
                cardLayout.show(mainPanel, "Login"); // Redirect to Login page if not logged in
            }
        });

        headerPanel.add(userProfileButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        // Date Field
        JLabel lblDate = new JLabel("Date:");
        lblDate.setFont(new Font("Arial", Font.PLAIN, 16));
        JXDatePicker datePicker = new JXDatePicker();
        datePicker.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
        datePicker.setFont(new Font("Arial", Font.PLAIN, 14));

        // Category Field
        JLabel lblCategory = new JLabel("Category:");
        lblCategory.setFont(new Font("Arial", Font.PLAIN, 16));
        JComboBox<String> cmbCategory = new JComboBox<>(
                new String[] { "Food", "Travel", "Entertainment", "Shopping", "Bills" });
        cmbCategory.setEditable(true);
        cmbCategory.setFont(new Font("Arial", Font.PLAIN, 14));

        // Amount Field
        JLabel lblAmount = new JLabel("Amount:");
        lblAmount.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField txtAmount = new JTextField();
        txtAmount.setFont(new Font("Arial", Font.PLAIN, 14));
        txtAmount.setToolTipText("Enter the amount spent (e.g., 500.00)");

        // Allow only numeric input in the Amount field
        txtAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                }
            }
        });

        // Description Field
        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField txtDescription = new JTextField();
        txtDescription.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDescription.setToolTipText("Enter a brief description of the expense");

        // Add Expense Button
        JButton btnAddExpense = Helper.createEnhancedButton("Add Expense");
        btnAddExpense.addActionListener(e -> {
            // Validate Fields
            Date selectedDate = datePicker.getDate();
            String category = cmbCategory.getSelectedItem().toString().trim();
            String amount = txtAmount.getText().trim();
            String description = txtDescription.getText().trim();

            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a valid date.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            } else if (amount.isEmpty() || !amount.matches("\\d+(\\.\\d{1,2})?")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            } else if (description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a description.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Format the selected date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = dateFormat.format(selectedDate);

                // Save to database (only if a user is logged in)
                if (LoginPage.currentUserId != -1) {
                    JDBC.addExpense(LoginPage.currentUserId, formattedDate, category, Double.parseDouble(amount),
                            description);

                    // Display success message
                    JOptionPane.showMessageDialog(this, "Expense added successfully!\nDate: " + formattedDate,
                            "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Clear fields
                    datePicker.setDate(null);
                    cmbCategory.setSelectedIndex(0);
                    txtAmount.setText("");
                    txtDescription.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "You must be logged in to add an expense.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add components to the form panel
        formPanel.add(lblDate);
        formPanel.add(datePicker);
        formPanel.add(lblCategory);
        formPanel.add(cmbCategory);
        formPanel.add(lblAmount);
        formPanel.add(txtAmount);
        formPanel.add(lblDescription);
        formPanel.add(txtDescription);
        formPanel.add(new JLabel()); // Empty cell for alignment
        formPanel.add(btnAddExpense);

        add(formPanel, BorderLayout.CENTER);
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