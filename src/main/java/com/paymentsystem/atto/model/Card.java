package com.paymentsystem.atto.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString

@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 16, name = "card_number")
    private String number;

    @Column(name = "person_name")
    private String name;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_date")
    private Date createdDate;
}
