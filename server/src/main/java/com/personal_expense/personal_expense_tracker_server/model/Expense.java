package com.personal_expense.personal_expense_tracker_server.model;

import com.personal_expense.personal_expense_tracker_server.enums.ExpenseCategory;
import com.personal_expense.personal_expense_tracker_server.enums.TransactionType;
import jakarta.persistence.*;

@Entity(name="expense")
@Table(name="expenses")
public class Expense extends Transaction {

    @Column(nullable = false)
    private ExpenseCategory category;

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    protected Expense() {}

    public Expense(Builder builder) {
        super(builder);
        this.category = builder.category;
    }

    public static class Builder extends Transaction.Builder<Builder> {
        private ExpenseCategory category;

        public Builder(Double amount, ExpenseCategory category) {
            super(amount, TransactionType.EXPENSE);
            this.category = category;
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Expense build() {
            return new Expense(this);
        }

    }


}
