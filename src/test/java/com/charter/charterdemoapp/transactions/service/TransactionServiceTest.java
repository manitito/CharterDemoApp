package com.charter.charterdemoapp.transactions.service;

import com.charter.charterdemoapp.customer.Customer;
import com.charter.charterdemoapp.model.MonthRewardDetails;
import com.charter.charterdemoapp.model.StatsResponse;
import com.charter.charterdemoapp.transactions.Transaction;
import com.charter.charterdemoapp.transactions.TransactionDataMock;
import com.charter.charterdemoapp.transactions.TransactionRepository;
import com.charter.charterdemoapp.transactions.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    private final TransactionDataMock transactionDataMock = new TransactionDataMock();
    @InjectMocks
    TransactionService service;
    @Mock
    TransactionRepository repository;

    @Test
    void shouldReturnAllTransactionsList() {
        //given
        when(repository.findAll()).thenReturn(transactionDataMock.mockTransactionList());

        //when
        List<Transaction> transactionList = service.getTransactionsList();

        //then
        assertEquals(3, transactionList.size());
    }

    @Test
    void shouldReturnCorrectTransactionStatsBetweenDate() {
        //given
        List<Transaction> transactionListWithCustomDate = getTransactionListWithCustomDate();

        //when
        when(repository.findByDateCreatedBetween(any(), any())).thenReturn(transactionListWithCustomDate);
        List<StatsResponse> transactionStats = service.getTransactionStats(LocalDate.of(2022, 8, 10), LocalDate.of(2022, 12, 10));
        List<String> months = transactionStats.get(0).getMonthly().stream().map(MonthRewardDetails::getMonth).toList();
        Integer totalPoints = transactionStats.get(0).getTotalPoints();

        //then
        assertEquals(1, transactionStats.size());
        assertEquals(3, months.size());
        assertEquals(450, totalPoints);
        assertTrue(months.contains("SEPTEMBER"));
        assertTrue(months.contains("OCTOBER"));
        assertTrue(months.contains("NOVEMBER"));
    }

    private List<Transaction> getTransactionListWithCustomDate() {
        Customer customer = Customer.builder().id(1L).name("John").lastname("Doe").build();
        return List.of(
                new Transaction(120, 90, OffsetDateTime.of(LocalDate.of(2022, 9, 10), LocalTime.of(8, 10), ZoneOffset.UTC), customer),
                new Transaction(120, 90, OffsetDateTime.of(LocalDate.of(2022, 9, 10), LocalTime.of(8, 10), ZoneOffset.UTC), customer),
                new Transaction(120, 90, OffsetDateTime.of(LocalDate.of(2022, 10, 10), LocalTime.of(8, 10), ZoneOffset.UTC), customer),
                new Transaction(120, 90, OffsetDateTime.of(LocalDate.of(2022, 10, 10), LocalTime.of(8, 10), ZoneOffset.UTC), customer),
                new Transaction(120, 90, OffsetDateTime.of(LocalDate.of(2022, 11, 10), LocalTime.of(8, 10), ZoneOffset.UTC), customer));
    }
}
