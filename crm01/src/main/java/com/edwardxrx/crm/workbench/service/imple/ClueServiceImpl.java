package com.edwardxrx.crm.workbench.service.imple;

import com.edwardxrx.crm.utils.SqlSessionUtil;
import com.edwardxrx.crm.workbench.dao.ClueDao;
import com.edwardxrx.crm.workbench.domain.Activity;
import com.edwardxrx.crm.workbench.domain.Clue;
import com.edwardxrx.crm.workbench.service.ClueService;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.workbench.service.imple
 * @ClassName: ClueServiceImlp
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/17 14:54
 * @Version: 1.0
 */
public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);


    @Override
    public boolean save(Clue clue) {
        int count = clueDao.save(clue);
        boolean flag = true;

        if(count!= 1)
            flag = false;

        return flag;

    }

    @Override
    public Clue detail(String id) {
        Clue clue = clueDao.detail(id);

        return clue;
    }
}
