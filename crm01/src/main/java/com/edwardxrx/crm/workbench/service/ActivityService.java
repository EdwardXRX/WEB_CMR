package com.edwardxrx.crm.workbench.service;

import com.edwardxrx.crm.vo.PaginationVO;
import com.edwardxrx.crm.workbench.domain.Activity;

import java.util.Map;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.workbench.service
 * @ClassName: ActivityService
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/13 20:55
 * @Version: 1.0
 */
public interface ActivityService {
    boolean save(Activity a);

    PaginationVO<Activity> pageList(Map<String, Object> map);
}
