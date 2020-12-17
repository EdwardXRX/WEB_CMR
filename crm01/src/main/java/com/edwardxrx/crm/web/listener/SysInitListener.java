package com.edwardxrx.crm.web.listener;

import com.edwardxrx.crm.settings.domain.DicValue;
import com.edwardxrx.crm.settings.service.DicService;
import com.edwardxrx.crm.settings.service.impl.DicServiceImpl;
import com.edwardxrx.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.web.listener
 * @ClassName: SysInitListener
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/17 16:23
 * @Version: 1.0
 */
public class SysInitListener implements ServletContextListener {

    /*
        该方法是用来监听上下文域对象的方法，当服务器启动，上下文域对象创建
        对象创建完毕之后
        就会立即执行本方法

        event:该参数能够取得监听的对象
                监听的是什么对象
                就可以通过该参数取得什么对象

                例如我们监听的是上下文域对象

                通过该参数就可以取得此对象
    */

    @Override
    public void contextInitialized(ServletContextEvent event) {

        System.out.println("服务器缓存数据字典开始");

        ServletContext application = event.getServletContext();

        //取数据字典

        DicService ds = (DicService) ServiceFactory.getService(new DicServiceImpl());
        //application.setAttribute("key",shujuzidian );

        //打包成一个map
        //业务层应该是这样保存数据的
        /*
            map.put("applicationList",dicList1);
            map.put("clueList",dicList2);
            map.put("stageList",dicList3);
        */
        Map<String, List<DicValue>> map = ds.getAll();

        Set<String> set = map.keySet();

        for(String key: set)
        {
          application.setAttribute(key,map.get(key));
        }

        System.out.println("服务器缓存数据字典结束");



    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {



    }
}
