package com.personal_expense.personal_expense_tracker_server.dto;

import com.personal_expense.personal_expense_tracker_server.enums.ExpenseCategory;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;

public class ExpenseDto {

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotNull(message = "Category is required")
    private ExpenseCategory category;

    private String name;
    private String description;
    private LocalDate date;

    public ExpenseDto() {}

    public ExpenseDto(Double amount, ExpenseCategory category, String name, String description, LocalDate date) {
        this.amount = amount;
        this.category = category;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
