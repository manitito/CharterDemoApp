package com.charter.charterdemoapp.transactions;

import com.charter.charterdemoapp.customer.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "transactions")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;
    @Column(name = "date_created")
    OffsetDateTime dateCreated;
    @Column(name = "amount")
    int amount;
    @Column(name = "points")
    int points;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Transaction(int amount, int points, OffsetDateTime dateCreated, Customer customer) {
        this.amount = amount;
        this.points = points;
        this.dateCreated = dateCreated;
        this.customer = customer;
    }


}

