package com.microservice.accounts.mapper;

import com.microservice.accounts.dto.CustomerDto;
import com.microservice.accounts.entity.Customer;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setNicNumber(customer.getNicNumber());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setNicNumber(customerDto.getNicNumber());
        return customer;
    }
}
