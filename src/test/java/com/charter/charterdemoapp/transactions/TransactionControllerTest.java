package com.charter.charterdemoapp.transactions;

import com.charter.charterdemoapp.model.TransactionResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc

// Not possible to disable jpa in spring 3

//@WebMvcTest(value = TransactionController.class,
//        excludeAutoConfiguration = {
//                AutoConfigureDataJpa.class,
//        DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class,
//        JpaRepositoriesAutoConfiguration.class},
//        excludeFilters =
//@ComponentScan.Filter(
//        type = FilterType.ASSIGNABLE_TYPE,
//        classes = {TransactionRepository.class, CustomerRepository.class}))
//@EnableAutoConfiguration(exclude = {
//        DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class,
//        JpaRepositoriesAutoConfiguration.class})
//@Import(TransactionControllerTest.Config.class)
class TransactionControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TransactionService service;

    @Autowired
    TransactionMapper transactionMapper;

    private final TransationMock transationMock = new TransationMock();

    @Autowired
    private JacksonTester<List<TransactionResponse>> transactions;

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