package com.charter.charterdemoapp.customer;

import com.charter.charterdemoapp.model.CustomerDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    Customer sourceToDestination(CustomerDetails customerDetails);
}
