package com.charter.charterdemoapp.transactions;

import com.charter.charterdemoapp.customer.Customer;
import com.charter.charterdemoapp.model.MonthRewardDetails;
import com.charter.charterdemoapp.model.StatsResponse;

import java.time.Month;
import java.time.OffsetDateTime;
import java.util.List;

public class TransactionDataMock {

    public List<Transaction> mockTransactionList() {
        return List.of(
                new Transaction(120, 90, OffsetDateTime.now(), Customer.builder().id(1L).name("John").lastname("Doe").build()),
                new Transaction(120, 80, OffsetDateTime.now(), Customer.builder().id(1L).name("John").lastname("Doe").build()),
                new Transaction(120, 90, OffsetDateTime.now(), Customer.builder().id(1L).name("John").lastname("Doe").build()));
    }


    public List<StatsResponse> mockStatus() {
        return List.of(new StatsResponse().customerId(1L).totalPoints(450).monthly(
                        List.of(new MonthRewardDetails().month(Month.APRIL.name()).points(200),
                                new MonthRewardDetails().month(Month.MAY.name()).points(250))
                ),
                new StatsResponse().customerId(2L).totalPoints(400).monthly(
                        List.of(new MonthRewardDetails().month(Month.APRIL.name()).points(150),
                                new MonthRewardDetails().month(Month.JULY.name()).points(250))
                )
        );
    }
}