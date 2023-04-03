package com.charter.charterdemoapp.transactions;

import com.charter.charterdemoapp.customer.Customer;

import java.time.OffsetDateTime;
import java.util.List;

public class TransationMock {

    public List<Transaction> mockTransactionList() {
        return List.of(
                new Transaction(120, 90, OffsetDateTime.now(), Customer.builder().id(1L).name("John").lastname("Doe").build()),
                new Transaction(120, 80, OffsetDateTime.now(), Customer.builder().id(1L).name("John").lastname("Doe").build()),
                new Transaction(120, 90, OffsetDateTime.now(), Customer.builder().id(1L).name("John").lastname("Doe").build()));
    }
}