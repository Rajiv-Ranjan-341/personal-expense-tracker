# 💰 Personal Expense Tracker

A Java-based desktop application designed to help users manage and track their daily expenses efficiently. Built using **JavaFX/Swing** for the graphical user interface and **MySQL/SQLite** for backend storage, this application allows users to:

- Add, edit, delete, and categorize expenses
- View monthly summaries
- Analyze spending habits through charts and tables

## 🚀 Features

- **User-Friendly Interface**: Intuitive GUI built with JavaFX/Swing for seamless user interaction.
- **Expense Management**: Easily add, edit, delete, and categorize your daily expenses.
- **Data Persistence**: Store expense data securely using MySQL or SQLite databases.
- **Visual Analytics**: Gain insights into your spending habits through interactive charts and tables.
- **Monthly Summaries**: Review your monthly expenses to monitor and adjust your budgeting.

## 🛠️ Installation

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/Rajiv-Ranjan-341/personal-expense-tracker.git
   ```

2. **Navigate to the Project Directory**:

   ```bash
   cd personal-expense-tracker
   ```

3. **Set Up the Database**:

   - For **MySQL**:
     - Create a new database.
     - Update the database configuration in the source code with your MySQL credentials.
   - For **SQLite**:
     - Ensure the SQLite database file is in the correct directory as expected by the application.

4. **Compile and Run the Application**:

   - Using an IDE like **IntelliJ IDEA** or **Eclipse**, import the project and run the `Main` class.
   - Alternatively, compile and run via the command line:

     ```bash
     javac -cp ".;path_to_javafx_libs/*" src\Main.java
     java -cp ".;path_to_javafx_libs/*" Main
     ```

     Replace `path_to_javafx_libs` with the path to your JavaFX library files.

## 📸 Screenshots

![Dashboard](image/dashboard.png)
*Dashboard displaying monthly expense summary.*

![Add Expense](image/add_expense.png)
*Form to add a new expense.*

![Expense Chart](image/expense_chart.png)
*Visual representation of expenses by category.*

## 📂 Project Structure

```
personal-expense-tracker/
├── src/
│   ├── Main.java
│   ├── controllers/
│   ├── models/
│   └── views/
├── image/
│   ├── dashboard.png
│   ├── add_expense.png
│   └── expense_chart.png
├── README.md
└── JAVA_PROJECT.jar
```

## 🤝 Contributing

Contributions are welcome! If you'd like to improve this project:

1. Fork the repository.
2. Create a new branch:

   ```bash
   git checkout -b feature/YourFeature
   ```

3. Commit your changes:

   ```bash
   git commit -m "Add YourFeature"
   ```

4. Push to the branch:

   ```bash
   git push origin feature/YourFeature
   ```

5. Open a pull request.

## 📄 License

This project is licensed under the [MIT License](LICENSE).
