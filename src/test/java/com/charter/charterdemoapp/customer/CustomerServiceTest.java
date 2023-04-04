package com.charter.charterdemoapp.customer;

import com.charter.charterdemoapp.model.CustomerDetails;
import com.charter.charterdemoapp.model.TransactionDetails;
import com.charter.charterdemoapp.transactions.Transaction;
import com.charter.charterdemoapp.transactions.TransactionRepository;
import com.charter.charterdemoapp.transactions.rewards.RewardPointsCalculator;
import com.charter.charterdemoapp.validator.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Spy
    private CustomerMapper customerMapper;

    @Spy
    private Validator validator;
    @Mock
    private RewardPointsCalculator rewardPointsCalculator;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void whenCustomerHaveBlankNameAndLastNameThrowsResponseStatusException() {
        // Given
        CustomerDetails customerDetails = new CustomerDetails("", "");

        // Then
        assertThrows(ResponseStatusException.class, () -> customerService.createCustomer(customerDetails));
    }

    @Test
    void whenCreateCustomerReturnSavedCustomer() {
        // Given
        CustomerDetails customerDetails = new CustomerDetails("John", "Doe");
        Customer customer = Customer.builder().name("John").lastname("Doe").build();
        Customer mockedCustomer = Customer.builder().id(1L).name("John").lastname("Doe").build();

        when(customerMapper.sourceToDestination(customerDetails)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(mockedCustomer);

        // when
        Customer savedCustomer = customerService.createCustomer(customerDetails);

        // Then
        assertEquals(mockedCustomer, savedCustomer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void whenCustomerAmountIsNegativeThrowsResponseStatusException() {
        // Given
        TransactionDetails transactionDetails = new TransactionDetails(-100);

        // Then
        assertThrows(ResponseStatusException.class, () -> customerService.createTransactionForCustomer(transactionDetails, 1L));
    }

    @Test
    void testCreateTransactionForCustomerWithValidDetails() {
        // given
        int amount = 51;
        int points = 1;
        TransactionDetails transactionDetails = new TransactionDetails(amount);

        Customer customer = Customer.builder().id(1L).name("John").lastname("Doe").build();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(rewardPointsCalculator.calculateRewards(amount)).thenReturn(points);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> Transaction.builder().id(1L).amount(amount).points(points).customer(customer).build());

        // when
        Transaction result = customerService.createTransactionForCustomer(transactionDetails, 1L);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(amount, result.getAmount());
        assertEquals(points, result.getPoints());
        assertEquals(customer, result.getCustomer());
    }
}