package com.personal_expense.personal_expense_tracker_server.model;

import com.personal_expense.personal_expense_tracker_server.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name="income")
@Table(name="income")
public class Income extends Transaction {

    protected Income() {}

    private Income(Builder builder) {
        super(builder);
    }

    public static class Builder extends Transaction.Builder<Builder> {

        public Builder(double amount) {
            super(amount, TransactionType.INCOME);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Income build() {
            return new Income(this);
        }
    }
}
