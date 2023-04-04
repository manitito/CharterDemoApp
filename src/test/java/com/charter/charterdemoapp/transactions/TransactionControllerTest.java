package com.charter.charterdemoapp.transactions;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

    private final TransationMock transationMock = new TransationMock();


    @SneakyThrows
    @Test
    void listTransactions() {

        given(service.getTransactionsList()).willReturn(transationMock.mockTransactionList());

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                        .get("/v1/transactions")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse();

        // How to solve it ?
//        Assert.assertEquals(response.getContentAsString(),
//                transactions.write(transactionMapper.mapList(transationMock.mockTransactionList())).getJson());


    }
}