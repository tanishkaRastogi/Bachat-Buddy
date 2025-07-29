package MajorProject.Expense_Tracker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {

    private static final String DB_URL = "jdbc:sqlite:src/main/resources/expenses.db";

    // Constructor – ensures table exists
    public ExpenseRepository() {
        createTableIfNotExists();
    }

    // 🔧 Create table if it doesn't exist
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS expenses ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + "category TEXT NOT NULL,"
                   + "amount REAL NOT NULL,"
                   + "description TEXT,"
                   + "date TEXT NOT NULL"
                   + ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Table 'expenses' checked/created.");
        } catch (SQLException e) {
            System.out.println("❌ Error creating table: " + e.getMessage());
        }
    }

    // ✅ Add a new expense
    public void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (category, amount, description, date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, expense.getCategory());
            stmt.setDouble(2, expense.getAmount());
            stmt.setString(3, expense.getDescription());
            stmt.setString(4, expense.getDate());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Get all expenses
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Expense expense = new Expense();
                expense.setId(rs.getInt("id"));
                expense.setCategory(rs.getString("category"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setDescription(rs.getString("description"));
                expense.setDate(rs.getString("date"));
                expenses.add(expense);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }

    // ✅ Delete an expense by ID
    public void deleteExpenseById(int id) {
        String sql = "DELETE FROM expenses WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // ✅ Update an expense by ID
    public void updateExpense(Expense expense) {
        String sql = "UPDATE expenses SET category = ?, amount = ?, description = ?, date = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, expense.getCategory());
            stmt.setDouble(2, expense.getAmount());
            stmt.setString(3, expense.getDescription());
            stmt.setString(4, expense.getDate());
            stmt.setInt(5, expense.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}