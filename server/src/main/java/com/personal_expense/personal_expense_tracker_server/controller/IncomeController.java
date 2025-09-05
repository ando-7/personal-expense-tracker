package com.personal_expense.personal_expense_tracker_server.controller;

import com.personal_expense.personal_expense_tracker_server.dto.IncomeDto;
import com.personal_expense.personal_expense_tracker_server.model.Expense;
import com.personal_expense.personal_expense_tracker_server.model.Income;
import com.personal_expense.personal_expense_tracker_server.service.IncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions/income")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Income> getAllIncomes() {
        return incomeService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Income getIncomeById(@RequestParam @PathVariable Long id) {
        return incomeService.getElementById(id);
    }

    @GetMapping("/month/{year}/{month}")
    @ResponseStatus(HttpStatus.OK)
    public List<Income> getExpensesByMonth(@PathVariable int year, @PathVariable int month) {
        return incomeService.filterByMonth(year, month);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Income addIncome(@RequestBody IncomeDto income) {
        return incomeService.createIncome(income);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Income updateIncome(@PathVariable Long id, @RequestBody IncomeDto income) {
        return incomeService.updateIncomeById(id, income);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable Long id) {
        incomeService.deleteElementById(id);
    }

    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    public Double totalIncome() {
        return incomeService.getTotalSum();
    }

}
