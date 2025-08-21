package com.personal_expense.personal_expense_tracker_server.service;

import com.personal_expense.personal_expense_tracker_server.dto.IncomeDto;
import com.personal_expense.personal_expense_tracker_server.model.Income;
import com.personal_expense.personal_expense_tracker_server.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    private IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public Income createIncome(IncomeDto incomeDto) {
        Income income = new Income.Builder( incomeDto.getAmount())
                .name(incomeDto.getName())
                .description(incomeDto.getDescription())
                .date(incomeDto.getDate())
                .build();

        return incomeRepository.save(income);
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public Income getIncomeById(Long id) {
        return incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No income with id " + id + " found"));
    }

    public void deleteIncomeById(Long id) {
        incomeRepository.deleteById(id);
    }

    public Income updateIncomeById(Long id, IncomeDto incomeDto) {
        Income income = getIncomeById(id);

        income.setName(incomeDto.getName());
        income.setDescription(incomeDto.getDescription());
        income.setDate(incomeDto.getDate());
        income.setAmount(incomeDto.getAmount());

        return incomeRepository.save(income);


    }
}
