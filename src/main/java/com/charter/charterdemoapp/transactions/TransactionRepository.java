package com.charter.charterdemoapp.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByDateCreatedBetween(OffsetDateTime startDate, OffsetDateTime endDate);
}
