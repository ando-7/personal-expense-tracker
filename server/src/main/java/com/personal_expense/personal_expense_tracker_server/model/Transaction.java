package com.personal_expense.personal_expense_tracker_server.model;

import com.personal_expense.personal_expense_tracker_server.enums.TransactionType;
import jakarta.persistence.*;

import java.time.LocalDate;

@MappedSuperclass
public abstract class Transaction {

    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    @Column(updatable=false)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private Double amount;

    @Column
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    protected Transaction() {} // JPA

    Transaction(Builder<?> builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.amount = builder.amount;
        this.date = builder.date != null ? builder.date : LocalDate.now();
        this.user = builder.user;
        this.type = builder.type;
    }

    public static abstract class Builder <T extends Builder<T>>{
        private String name;
        private String description;
        private Double amount;
        private LocalDate date;
        private User user;
        private TransactionType type;

        public Builder(Double amount, TransactionType type) {
            this.amount = amount;
            this.type = type;
        }

        public T description(String description) {
            this.description = description;
            return self();
        }

        public T date(LocalDate date) {
            this.date = date;
            return self();
        }

        public T name(String  name) {
            this.name = name;
            return self();
        }

        public T user(User user) {
            this.user = user;
            return self();
        }

        protected abstract T self(); // for method chaining in subclasses

        public abstract Transaction build();
    }
}
