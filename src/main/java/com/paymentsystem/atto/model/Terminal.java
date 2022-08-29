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
@Table(name = "terminal")
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 12, name = "terminal_number")
    private String number;

    @Column(name = "direction_number")
    private String name;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_date")
    private Date createdDate;
}
