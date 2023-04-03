package com.charter.charterdemoapp.customer;

import com.charter.charterdemoapp.model.CustomerDetails;
import com.charter.charterdemoapp.model.TransactionDetails;
import com.charter.charterdemoapp.transactions.Transaction;
import com.charter.charterdemoapp.transactions.TransactionRepository;
import com.charter.charterdemoapp.transactions.rewards.RewardPointsCalculator;
import com.charter.charterdemoapp.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final RewardPointsCalculator rewardPointsCalculator;
    private final CustomerMapper customerMapper;
    private final Validator validator;

    @Transactional
    public Customer createCustomer(CustomerDetails customerDetails) {
        validator.validateCustomerDetails(customerDetails);
        Customer user = customerMapper.sourceToDestination(customerDetails);
        Customer savedCustomer = customerRepository.save(user);
        log.info("User created:" + savedCustomer);
        return savedCustomer;
    }

    @Transactional
    public Transaction createTransactionForCustomer(TransactionDetails transactionDetails, Long customerId) {
        int amount = transactionDetails.getAmount();
        validator.validateAmount(amount);
        Customer customer = Optional.of(customerRepository.findById(customerId)).get().orElseThrow(() -> {
            log.warn("Customer not found for customerId:" + customerId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        });

        OffsetDateTime now = OffsetDateTime.now();
        Transaction transaction = new Transaction(amount, rewardPointsCalculator.calculateRewards(amount), now, customer);
        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transaction created:" + transaction);
        return savedTransaction;
    }
}
