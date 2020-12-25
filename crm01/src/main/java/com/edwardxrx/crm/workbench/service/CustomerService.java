package com.edwardxrx.crm.workbench.service;

import java.util.List;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.workbench.service
 * @ClassName: CustomerService
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/23 21:24
 * @Version: 1.0
 */
public interface CustomerService {
    List<String> getCustomerName(String name);
}
