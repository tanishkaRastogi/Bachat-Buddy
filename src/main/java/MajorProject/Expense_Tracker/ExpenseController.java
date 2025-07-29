package MajorProject.Expense_Tracker;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "*") // Allow CORS for frontend fetch()
public class ExpenseController {

    private final ExpenseRepository expenseRepository = new ExpenseRepository();

    // POST - Add new expense
    @PostMapping
    public void addExpense(@RequestBody Expense expense) {
        expenseRepository.addExpense(expense);
    }

    // GET - Fetch all expenses
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepository.getAllExpenses();
    }

    // DELETE - Delete expense by ID
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable int id) {
        expenseRepository.deleteExpenseById(id);
    }
    
    // PUT - Update expense by ID
    @PutMapping("/{id}")
    public void updateExpense(@PathVariable int id, @RequestBody Expense updatedExpense) {
        updatedExpense.setId(id);
        expenseRepository.updateExpense(updatedExpense);
    }
}