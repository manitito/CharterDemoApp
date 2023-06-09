package com.charter.charterdemoapp.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.Mockito.doNothing;
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
    @SpyBean
    ObjectMapper mapper;

    private final TransactionDataMock transactionMock = new TransactionDataMock();

    @SneakyThrows
    @Test
    void listTransactions() {

        given(service.getTransactionsList()).willReturn(transactionMock.mockTransactionList());

        mvc.perform(MockMvcRequestBuilders
                        .get("/v1/transactions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].dateCreated").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].customerId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].points").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerId").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].points").value(90));
    }

    @SneakyThrows
    @Test
    void listTransactionsStatsInvalidParams() {
        given(service.getTransactionStats(any(), any())).willReturn(transactionMock.mockStatus());

        mvc.perform(MockMvcRequestBuilders
                        .get("/v1/transactions/stats")
                        .queryParam("date-Start", "2023-04-04")
                        .queryParam("date-End", "2024-04-04"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.detail").value("Required parameter 'dateStart' is not present."));
    }

    @SneakyThrows
    @Test
    void listTransactionsStats() {
        given(service.getTransactionStats(any(), any())).willReturn(transactionMock.mockStatus());

        mvc.perform(MockMvcRequestBuilders
                        .get("/v1/transactions/stats")
                        .queryParam("dateStart", "2023-04-04")
                        .queryParam("dateEnd", "2024-04-04"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].customerId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalPoints").value(450))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].monthly").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].monthly").value(hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].monthly[0].month").value(Month.APRIL.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].monthly[0].points").value(150));
    }

    @SneakyThrows
    @Test
    void transactionById() {
        given(service.findById(11)).willReturn(transactionMock.mockTransactionList().get(0));

        mvc.perform(MockMvcRequestBuilders
                        .get("/v1/transactions/11")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.points").value(90))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateCreated").exists());
    }

    @SneakyThrows
    @Test
    void updateTransaction() {
        doNothing().when(service).update(any(), any());

        mvc.perform(MockMvcRequestBuilders
                        .put("/v1/transactions/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(transactionMock.mockTransactionList().get(0))))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @SneakyThrows
    @Test
    void deleteTransaction() {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/v1/transactions/11"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}