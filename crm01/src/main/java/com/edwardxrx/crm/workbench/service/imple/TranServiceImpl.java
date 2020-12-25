package com.edwardxrx.crm.workbench.service.imple;

import com.edwardxrx.crm.utils.SqlSessionUtil;
import com.edwardxrx.crm.workbench.dao.TranDao;
import com.edwardxrx.crm.workbench.service.TranService;

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

}
