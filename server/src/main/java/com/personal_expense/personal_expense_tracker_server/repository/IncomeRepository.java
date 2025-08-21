package com.personal_expense.personal_expense_tracker_server.repository;

import com.personal_expense.personal_expense_tracker_server.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
