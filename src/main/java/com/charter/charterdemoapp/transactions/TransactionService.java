package com.charter.charterdemoapp.transactions;

import com.charter.charterdemoapp.model.MonthRewardDetails;
import com.charter.charterdemoapp.model.StatsResponse;
import com.charter.charterdemoapp.model.TransactionInput;
import com.charter.charterdemoapp.model.TransactionResponse;
import com.charter.charterdemoapp.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.charter.charterdemoapp.transactions.utils.DateTransformer.localDateToOffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final Validator validator;

    public TransactionResponse findById(long id) {
        return transactionMapper.sourceToDestination(transactionRepository.findById(id).orElseThrow(() -> {
            log.warn("Transaction not found for transaction:" + id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found");
        }));
    }

    public List<Transaction> getTransactionsList() {
        return transactionRepository.findAll();

    }

    public List<StatsResponse> getTransactionStats(LocalDate dateStart, LocalDate dateEnd) {
        List<StatsResponse> collectedStats = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findByDateCreatedBetween(localDateToOffsetDateTime(dateStart), localDateToOffsetDateTime(dateEnd));

        Map<Long, List<Transaction>> transactionsPerCustomer = aggregateTransactionPerCustomer(transactions);

        transactionsPerCustomer.forEach((customerId, transactionList) -> {
            Map<Month, List<Transaction>> transactionsPerMonthPerCustomer = aggregateTransactionPerMonthPerCustomer(transactionList);

            collectedStats.add(new StatsResponse()
                    .customerId(customerId)
                    .totalPoints(calculateTotalPoints(transactionList))
                    .monthly(calculateMonthlyRewardDetails(transactionsPerMonthPerCustomer)));
        });
        return collectedStats;
    }

    private Map<Long, List<Transaction>> aggregateTransactionPerCustomer(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getCustomer().getId()));
    }

    private Map<Month, List<Transaction>> aggregateTransactionPerMonthPerCustomer(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getDateCreated().getMonth()));
    }

    private Integer calculateTotalPoints(List<Transaction> transactionList) {
        return transactionList.stream()
                .map(Transaction::getPoints)
                .reduce(0, Integer::sum);
    }

    private List<MonthRewardDetails> calculateMonthlyRewardDetails(Map<Month, List<Transaction>> transactionsPerMonth) {
        List<MonthRewardDetails> monthRewardDetails = new ArrayList<>();
        transactionsPerMonth.forEach((month, transactionList) -> monthRewardDetails.add(new MonthRewardDetails()
                .month(String.valueOf(month))
                .points(calculateTotalPoints(transactionList))));
        return monthRewardDetails;
    }

    public void delete(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    @Transactional
    public void update(Long transactionId, TransactionInput transactionInput) {
        validator.validateAmount(transactionInput.getAmount());
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> {
            log.warn("Transaction not found for transaction:" + transactionId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found");
        });
        transaction.setPoints(transactionInput.getPoints());
        transaction.setAmount(transactionInput.getAmount());
        transaction.setDateCreated(transactionInput.getDateCreated());
        transactionRepository.save(transaction);
    }
}