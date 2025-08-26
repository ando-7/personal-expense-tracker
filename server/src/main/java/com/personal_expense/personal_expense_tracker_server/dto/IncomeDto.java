package com.personal_expense.personal_expense_tracker_server.dto;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class IncomeDto {

    @NotNull(message = "Amount is required")
    private Double amount;

    private String name;          // optional
    private String description;   // optional
    private LocalDate date;       // optional

    public IncomeDto() {}

    public IncomeDto(Double amount, String name, String description, LocalDate date, Long userId) {
        this.amount = amount;
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
