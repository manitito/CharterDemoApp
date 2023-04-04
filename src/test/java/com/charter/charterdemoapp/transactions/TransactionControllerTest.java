package com.charter.charterdemoapp.transactions;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Month;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({TransactionController.class, TransactionMapperImpl.class})
class TransactionControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TransactionService service;

    @SpyBean
    TransactionMapper transactionMapper;

    private final TransactionDataMock transationMock = new TransactionDataMock();


    @SneakyThrows
    @Test
    void listTransactions() {

        given(service.getTransactionsList()).willReturn(transationMock.mockTransactionList());

        mvc.perform(MockMvcRequestBuilders
                        .get("/v1/transactions")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].dateCreated").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].customerId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].points").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerId").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].points").value(90));
    }

    @SneakyThrows
    @Test
    void listTransactionsStats() {
        given(service.getTransactionStats(any(), any())).willReturn(transationMock.mockStatus());

        mvc.perform(MockMvcRequestBuilders
                        .get("/v1/transactions/stats")
                        .queryParam("dateStart", "2023-04-04")
                        .queryParam("dateEnd", "2024-04-04")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].customerId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalPoints").value(450))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].monthly").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].monthly").value(hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].monthly[0].month").value(Month.APRIL.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].monthly[0].points").value(150));
    }

    @Test
    void transactionById() {
    }

    @Test
    void updateTransaction() {
    }

    @Test
    void deleteTransaction() {
    }

}