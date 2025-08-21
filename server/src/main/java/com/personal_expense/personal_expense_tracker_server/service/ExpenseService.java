package com.personal_expense.personal_expense_tracker_server.service;

import com.personal_expense.personal_expense_tracker_server.dto.ExpenseDto;
import com.personal_expense.personal_expense_tracker_server.model.Expense;
import com.personal_expense.personal_expense_tracker_server.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;

    public ExpenseService (ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpense(ExpenseDto ExpenseDto) {
        Expense expense = new Expense.Builder(ExpenseDto.getAmount(), ExpenseDto.getCategory())
                .name(ExpenseDto.getName())
                .description(ExpenseDto.getDescription())
                .date(ExpenseDto.getDate())
                .build();


        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public Expense updateExpense(Long id, ExpenseDto expenseDto) {
        Expense expense = getExpenseById(id);
        expense.setAmount(expenseDto.getAmount());
        expense.setCategory(expenseDto.getCategory());
        expense.setName(expenseDto.getName());
        expense.setDescription(expenseDto.getDescription());
        expense.setDate(expenseDto.getDate());

        return expenseRepository.save(expense);
    }
}
