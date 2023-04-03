package com.charter.charterdemoapp.validator;

import com.charter.charterdemoapp.model.CustomerDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class Validator {

    public void validateCustomerDetails(CustomerDetails customerDetails) {
        if (StringUtils.isBlank(customerDetails.getName()) && StringUtils.isBlank(customerDetails.getLastname())) {
            log.debug("Customer name and last name can't be blank");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer name and last name can't be blank");
        }
    }

    public void validateAmount(int amount) {
        if (amount < 0) {
            log.debug("Amount is less than 0");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount is less than 0");

        }
    }
}