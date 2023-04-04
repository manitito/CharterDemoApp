package com.charter.charterdemoapp.transactions;

import com.charter.charterdemoapp.model.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "customerId", source = "id")
    TransactionResponse map(Transaction transaction);

    List<TransactionResponse> mapList(List<Transaction> transactionsList);
}
