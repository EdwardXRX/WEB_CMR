package com.edwardxrx.crm.workbench.service.imple;

import com.edwardxrx.crm.utils.DateTimeUtil;
import com.edwardxrx.crm.utils.SqlSessionUtil;
import com.edwardxrx.crm.utils.UUIDUtil;
import com.edwardxrx.crm.workbench.dao.CustomerDao;
import com.edwardxrx.crm.workbench.dao.TranDao;
import com.edwardxrx.crm.workbench.dao.TranHistoryDao;
import com.edwardxrx.crm.workbench.domain.Customer;
import com.edwardxrx.crm.workbench.domain.Tran;
import com.edwardxrx.crm.workbench.domain.TranHistory;
import com.edwardxrx.crm.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.workbench.service.imple
 * @ClassName: TranServiceImpl
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/23 19:47
 * @Version: 1.0
 */
public class TranServiceImpl implements TranService {

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public boolean save(Tran t, String customerName) {
        /*
         *   添加交易之前，参数就少了customerId
         *   先处理客户相关需求
         *
         *       （1）判断customerName，根据客户名进行精准查询
         *               如果没有，则新建
         *               否则，取出customerId
         *   （2）经过上面操作，t对象就全了，执行交易添加操作
         *       （3）交易完成后，生成一个历史记录
         *
         *
         * */

        boolean flag = true;

        Customer customer = customerDao.getCustomerByName(customerName);
        if (customer == null) {
            //需要创建用户
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setCreateBy(t.getCreateBy());
            customer.setContactSummary(t.getContactSummary());
            customer.setNextContactTime(t.getNextContactTime());
            customer.setOwner(t.getOwner());

            int count1 = customerDao.save(customer);
            if (count1 != 1)
                flag = false;
        }

        //通过以上对用户的处理，不论是查询查来的用户，还是新建的用户，都已经有了

        t.setCustomerId(customer.getId());

        int count2 = tranDao.save(t);
        if (count2 != 1)
            flag = false;


        //添加交易
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(t.getId());
        th.setStage(t.getStage());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setCreateBy(t.getCreateBy());

        int count3 = tranHistoryDao.save(th);
        if (count3 != 1)
            flag = false;


        return flag;
    }

    @Override
    public Tran detail(String id) {
        Tran t = tranDao.detail(id);
        return t;
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {

        List<TranHistory> thList = tranHistoryDao.getHistoryListByTranId(tranId);
        return thList;
    }

    @Override
    public boolean changeStage(Tran t) {
        boolean flag = true;

        int count1 = tranDao.changeStage(t);

        if (count1!=1)
            flag = false;

        //阶段改变之后，我们需要生成一条交易历史
        TranHistory th = new TranHistory();
        th.setStage(t.getStage());
        th.setId(UUIDUtil.getUUID());
        th.setPossibility(t.getPossibility());
        th.setCreateBy(t.getEditBy());
        th.setCreateTime(t.getEditTime());
        th.setExpectedDate(t.getExpectedDate());
        th.setMoney(t.getMoney());
        th.setTranId(t.getId());

        //添加一条交易历史
        int count2 = tranHistoryDao.save(th);
        if (count2 != 1)
            flag = false;
        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {

        Map<String, Object> map = new HashMap<>();

        //取得total
        int total = tranDao.getTotal();

        System.out.println("total:" + total);

        //取得dataList
        List<Map<String,Object>> dataList =  tranDao.getCharts();

        if(dataList == null)
            System.out.println("空2");

        //将两个数据保存至map中
        map.put("total",total);
        map.put("dataList",dataList);

        return map;
    }
}
