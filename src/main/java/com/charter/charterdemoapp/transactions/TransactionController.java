package com.charter.charterdemoapp.transactions;

import com.charter.charterdemoapp.configuration.TransactionsApi;
import com.charter.charterdemoapp.model.StatsResponse;
import com.charter.charterdemoapp.model.TransactionInput;
import com.charter.charterdemoapp.model.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TransactionController implements TransactionsApi {

    private final TransactionService service;
    private final TransactionMapper transactionMapper;

    @Override
    public ResponseEntity<List<TransactionResponse>> listTransactions() {
        return ResponseEntity.ok(transactionMapper.mapList(service.getTransactionsList()));
    }

    @Override
    public ResponseEntity<List<StatsResponse>> listTransactionsStats(LocalDate dateStart, LocalDate dateEnd) {
        return ResponseEntity.ok(service.getTransactionStats(dateStart, dateEnd));
    }

    @Override
    public ResponseEntity<TransactionResponse> transactionById(Long transactionId) {
        return ResponseEntity.ok(transactionMapper.map(service.findById(transactionId)));
    }

    @Override
    public ResponseEntity<Void> updateTransaction(Long transactionId, TransactionInput transactionInput) {
        service.update(transactionId, transactionInput);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> deleteTransaction(Long transactionId) {
        service.delete(transactionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
