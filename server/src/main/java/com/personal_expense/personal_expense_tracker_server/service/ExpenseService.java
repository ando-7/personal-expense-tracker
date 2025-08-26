package com.personal_expense.personal_expense_tracker_server.service;

import com.personal_expense.personal_expense_tracker_server.dto.ExpenseDto;
import com.personal_expense.personal_expense_tracker_server.enums.ExpenseCategory;
import com.personal_expense.personal_expense_tracker_server.model.Expense;
import com.personal_expense.personal_expense_tracker_server.model.User;
import com.personal_expense.personal_expense_tracker_server.repository.ExpenseRepository;
import com.personal_expense.personal_expense_tracker_server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService extends TransactionService<Expense> {

    private final ExpenseRepository expenseRepository;

    public ExpenseService (ExpenseRepository expenseRepository, UserRepository userRepository) {
        super(expenseRepository, userRepository);
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpense(ExpenseDto expenseDto) {

        User user = getCurrentUser();

        Expense expense = new Expense.Builder(expenseDto.getAmount(), expenseDto.getCategory())
                .name(expenseDto.getName())
                .description(expenseDto.getDescription())
                .date(expenseDto.getDate())
                .user(user)
                .build();


        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, ExpenseDto expenseDto) {

        User currentUser = getCurrentUser();

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        if(!expense.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access denied: You cannot update this expense");
        }

        expense.setAmount(expenseDto.getAmount());
        expense.setCategory(expenseDto.getCategory());
        expense.setName(expenseDto.getName());
        expense.setDescription(expenseDto.getDescription());
        expense.setDate(expenseDto.getDate());

        return expenseRepository.save(expense);
    }

    public List<Expense> filterExpensesByCategory(ExpenseCategory category) {
        return getAll().stream().filter(expense ->
            expense.getCategory() == category).toList();
    }

}
