package com.microservice.accounts.service;

import com.microservice.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {

    CustomerDetailsDto fetchCustomerDetails(String nicNumber);
}
