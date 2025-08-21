package com.personal_expense.personal_expense_tracker_server.enums;

public enum ExpenseCategory {

    GENERAL("general"),
    GROCERIES("groceries"),
    SHOPPING("shopping"),
    EAT_OUT("eat out"),
    CINEMA("cinema"),
    TRAVELLING("travelling"),
    GIFT("gift"),
    HOBBY("hobby");

    private final String displayName;

    ExpenseCategory(String displayName) {
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
