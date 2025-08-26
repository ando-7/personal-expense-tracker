package com.personal_expense.personal_expense_tracker_server.controller;

import com.personal_expense.personal_expense_tracker_server.dto.ExpenseDto;
import com.personal_expense.personal_expense_tracker_server.model.Expense;
import com.personal_expense.personal_expense_tracker_server.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> getAllExpenses() {
        return expenseService.getAll();
    }

    @GetMapping("/month/{year}/{month}")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> getExpensesByMonth(@PathVariable int year, @PathVariable int month) {
        return expenseService.filterByMonth(year, month);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.getElementById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Expense addExpense(@RequestBody ExpenseDto expenseDto) {
        return expenseService.createExpense(expenseDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Expense updateExpense(@PathVariable Long id, @RequestBody ExpenseDto expenseDto) {
        return expenseService.updateExpense(id, expenseDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteElementById(id);
    }

}
