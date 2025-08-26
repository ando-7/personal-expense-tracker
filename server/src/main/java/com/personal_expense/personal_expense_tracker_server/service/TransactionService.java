package com.personal_expense.personal_expense_tracker_server.service;

import com.personal_expense.personal_expense_tracker_server.model.Expense;
import com.personal_expense.personal_expense_tracker_server.model.Transaction;
import com.personal_expense.personal_expense_tracker_server.model.User;
import com.personal_expense.personal_expense_tracker_server.repository.BaseTransactionRepository;
import com.personal_expense.personal_expense_tracker_server.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.YearMonth;
import java.util.List;

public abstract class TransactionService <T extends Transaction> {

    protected final BaseTransactionRepository<T> repository;
    protected final UserRepository userRepository;

    public TransactionService(BaseTransactionRepository<T> repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    protected User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }

    public List<T> getAll() {
        User user = getCurrentUser();
        return repository.findByUser(user);
    }


    public T getElementById(Long id) {
        User user = getCurrentUser();

        T element =  repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No transaction with id " + id + " found"));

        if(!element.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied: You cannot view this transaction");
        }

        return element;
    }

    public void deleteElementById(Long id) {
        User user = getCurrentUser();

        T element =  repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No transaction with id " + id + " found"));

        if(!element.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied: You cannot delete this transaction");
        }

        repository.deleteById(id);
    }

    public List<T> filterByMonth(int year, int month) {
        User user = getCurrentUser();

        YearMonth yearMonth = YearMonth.of(year, month);

        return repository.findByUserAndDateBetween(user,
                yearMonth.atDay(1),
                yearMonth.atEndOfMonth());
    }

}
