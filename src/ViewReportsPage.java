import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jdesktop.swingx.JXDatePicker;

public class ViewReportsPage extends JPanel {
    private Image backgroundImage;

    public ViewReportsPage(CardLayout cardLayout, JPanel mainPanel) {

          try {
            backgroundImage = ImageIO.read(new File("image/repo.jpg")); // Replace with your image path
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
        backButton.setForeground(Color.WHITE); // Set text color
        backButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home Page"));
        headerPanel.add(backButton, BorderLayout.WEST);

          // User Profile Button
          JButton userProfileButton = new JButton("O_O");
        userProfileButton.setOpaque(false); // Make the button transparent
        userProfileButton.setContentAreaFilled(false); // Remove background fill
        userProfileButton.setBorderPainted(false); // Remove border
        userProfileButton.setForeground(Color.WHITE); // Set text color
        userProfileButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
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
  
          headerPanel.add(userProfileButton, BorderLayout.EAST);
  
          add(headerPanel, BorderLayout.NORTH);

        // Header Label
        // JLabel header = Helper.createHeader("Reports");
        // headerPanel.add(header, BorderLayout.CENTER);

        JLabel header = new JLabel("Reports", JLabel.CENTER);
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

        JLabel title = new JLabel("Expense Reports", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.DARK_GRAY);

        JPanel filterPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        filterPanel.setOpaque(false);

        JLabel lblStartDate = new JLabel("Start Date:");
        lblStartDate.setFont(new Font("Arial", Font.PLAIN, 16));
        JXDatePicker startDatePicker = new JXDatePicker();
        startDatePicker.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
        startDatePicker.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel lblEndDate = new JLabel("End Date:");
        lblEndDate.setFont(new Font("Arial", Font.PLAIN, 16));
        JXDatePicker endDatePicker = new JXDatePicker();
        endDatePicker.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
        endDatePicker.setFont(new Font("Arial", Font.PLAIN, 14));

        filterPanel.add(lblStartDate);
        filterPanel.add(startDatePicker);
        filterPanel.add(lblEndDate);
        filterPanel.add(endDatePicker);

        JButton btnGenerateReport = Helper.createEnhancedButton("Generate Report");
        btnGenerateReport.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Chart Panel
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.setOpaque(false);

        // Add action listener for Generate Report button
        btnGenerateReport.addActionListener(e -> {
            java.util.Date startDate = startDatePicker.getDate();
            java.util.Date endDate = endDatePicker.getDate();

            if (startDate == null || endDate == null) {
                JOptionPane.showMessageDialog(this, "Please select both start and end dates.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(this, "Start date must be before end date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Generate chart based on the selected date range
            DefaultPieDataset dataset = createDataset(startDate, endDate);
            JFreeChart chart = createChart(dataset);
            ChartPanel chartPanelComponent = new ChartPanel(chart);
            chartPanel.removeAll(); // Clear previous chart
            chartPanel.add(chartPanelComponent, BorderLayout.CENTER);
            chartPanel.revalidate();
            chartPanel.repaint();
        });

        contentPanel.add(title);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(filterPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(btnGenerateReport);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(chartPanel);

        add(contentPanel, BorderLayout.CENTER);
    }

    // Method to create dataset for the pie chart
    private DefaultPieDataset createDataset(java.util.Date startDate, java.util.Date endDate) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Fetch data from the database based on the date range
        if (LoginPage.currentUserId != -1) {
            List<String[]> expenses = JDBC.getExpenses(LoginPage.currentUserId);
            for (String[] expense : expenses) {
                String expenseDateStr = expense[0]; // Date in format dd-MM-yyyy
                String expenseCategory = expense[1]; // Category
                double expenseAmount = Double.parseDouble(expense[2]); // Amount

                // Parse expense date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    java.util.Date expenseDate = dateFormat.parse(expenseDateStr);

                    // Check if the expense date is within the selected range
                    if (!expenseDate.before(startDate) && !expenseDate.after(endDate)) {
                        // Add the expense to the dataset
                        dataset.setValue(expenseCategory, expenseAmount);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return dataset;
    }

    // Method to create a colorful pie chart
    private JFreeChart createChart(DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Expense Report - Date Range", // Chart title
                dataset, // Dataset
                true, // Include legend
                true, // Tooltips
                false // URLs
        );

        // Customize chart appearance
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Food", Color.RED);
        plot.setSectionPaint("Travel", Color.BLUE);
        plot.setSectionPaint("Entertainment", Color.GREEN);
        plot.setSectionPaint("Shopping", Color.ORANGE);
        plot.setSectionPaint("Bills", Color.MAGENTA);

        // Set background color
        chart.setBackgroundPaint(Color.WHITE);
        plot.setBackgroundPaint(Color.WHITE);

        return chart;
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