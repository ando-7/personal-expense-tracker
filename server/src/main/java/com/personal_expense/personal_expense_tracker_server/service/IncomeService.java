package com.personal_expense.personal_expense_tracker_server.service;

import com.personal_expense.personal_expense_tracker_server.dto.IncomeDto;
import com.personal_expense.personal_expense_tracker_server.model.Income;
import com.personal_expense.personal_expense_tracker_server.model.User;
import com.personal_expense.personal_expense_tracker_server.repository.IncomeRepository;
import com.personal_expense.personal_expense_tracker_server.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class IncomeService extends TransactionService<Income> {

    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository, UserRepository userRepository) {
        super(incomeRepository, userRepository);
        this.incomeRepository = incomeRepository;
    }

    public Income createIncome(IncomeDto incomeDto) {
        User user = getCurrentUser();

        Income income = new Income.Builder( incomeDto.getAmount())
                .name(incomeDto.getName())
                .description(incomeDto.getDescription())
                .date(incomeDto.getDate())
                .user(user)
                .build();

        return incomeRepository.save(income);
    }


    public Income updateIncomeById(Long id, IncomeDto incomeDto) {
        User currentUser = getCurrentUser();

        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found with id: " + id));

        if(!income.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access denied: You cannot update this expense");
        }

        income.setName(incomeDto.getName());
        income.setDescription(incomeDto.getDescription());
        income.setDate(incomeDto.getDate());
        income.setAmount(incomeDto.getAmount());

        return incomeRepository.save(income);

    }
}
