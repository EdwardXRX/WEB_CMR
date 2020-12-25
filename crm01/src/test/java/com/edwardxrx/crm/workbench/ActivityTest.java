package com.edwardxrx.crm.workbench;

import com.edwardxrx.crm.utils.ServiceFactory;
import com.edwardxrx.crm.utils.UUIDUtil;
import com.edwardxrx.crm.workbench.domain.Activity;
import com.edwardxrx.crm.workbench.service.ActivityService;
import com.edwardxrx.crm.workbench.service.imple.ActivityServiceImpl;
import org.junit.Test;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.workbench
 * @ClassName: ActivityTest
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/22 21:18
 * @Version: 1.0
 */
public class ActivityTest {

    @Test
    public void testSave()
    {
        Activity a = new Activity();
        a.setId(UUIDUtil.getUUID());
        a.setName("宣传推广会");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.save(a);

        System.out.println(flag);


    }

    @Test
    public void testEdit()
    {
        System.out.println("456");
    }

}
