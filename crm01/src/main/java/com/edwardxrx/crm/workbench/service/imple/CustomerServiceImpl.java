package com.edwardxrx.crm.workbench.service.imple;

import com.edwardxrx.crm.utils.SqlSessionUtil;
import com.edwardxrx.crm.workbench.dao.CustomerDao;
import com.edwardxrx.crm.workbench.service.CustomerService;

import java.util.List;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.workbench.service.imple
 * @ClassName: CustomerServiceImpl
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/23 21:25
 * @Version: 1.0
 */
public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);


    @Override
    public List<String> getCustomerName(String name) {
        List<String> list =  customerDao.getCustomerName(name);
        return list;
    }
}
