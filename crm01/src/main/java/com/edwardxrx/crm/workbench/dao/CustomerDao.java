package com.edwardxrx.crm.workbench.dao;

import com.edwardxrx.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByName(String name);

    int save(Customer cus);

    List<String> getCustomerName(String name);
}
