package com.paymentsystem.atto.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "terminal_number")
    private String terminalNumber;

    @Column(name = "transaction_date")
    Date transactionTime;

    @Column(name = "faire")
    private Double faire;
}
