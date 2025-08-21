package com.personal_expense.personal_expense_tracker_server.repository;

import com.personal_expense.personal_expense_tracker_server.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
