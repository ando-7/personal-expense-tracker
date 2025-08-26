package com.personal_expense.personal_expense_tracker_server.repository;

import com.personal_expense.personal_expense_tracker_server.model.Transaction;
import com.personal_expense.personal_expense_tracker_server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDate;
import java.util.List;

@NoRepositoryBean
public interface BaseTransactionRepository<T extends Transaction>
        extends JpaRepository<T, Long> {
    List<T> findByUser(User user);
    List<T> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);

}
