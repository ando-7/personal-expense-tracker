package com.personal_expense.personal_expense_tracker_server.enums;

public enum TransactionType {
    INCOME("income"),
    EXPENSE("expense");

    private final String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
