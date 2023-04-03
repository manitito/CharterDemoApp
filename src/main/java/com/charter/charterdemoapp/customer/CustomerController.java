package com.charter.charterdemoapp.customer;


import com.charter.charterdemoapp.configuration.CustomersApi;
import com.charter.charterdemoapp.model.CreateCustomerResponse;
import com.charter.charterdemoapp.model.CustomerDetails;
import com.charter.charterdemoapp.model.TransactionDetails;
import com.charter.charterdemoapp.model.TransactionResponse;
import com.charter.charterdemoapp.transactions.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CustomerController implements CustomersApi {

    private final CustomerService service;

    @Override
    public ResponseEntity<TransactionResponse> createTransaction(Long customerId, TransactionDetails transactionDetails) {
        log.info("Creating transaction for user {}", transactionDetails.getAmount());
        Transaction transaction = service.createTransactionForCustomer(transactionDetails, customerId);
        TransactionResponse transactionResponse = new TransactionResponse()
                .dateCreated(transaction.getDateCreated())
                .customerId(transaction.getCustomer().getId())
                .points(transaction.getPoints());

        return new ResponseEntity<>(transactionResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CreateCustomerResponse> createCustomer(CustomerDetails userDetails) {
        Customer customer = service.createCustomer(userDetails);
        return new ResponseEntity<>(new CreateCustomerResponse().customerId(customer.getId()), HttpStatus.CREATED);
    }
}
