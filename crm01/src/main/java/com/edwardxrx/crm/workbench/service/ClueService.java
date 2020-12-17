package com.edwardxrx.crm.workbench.service;

import com.edwardxrx.crm.workbench.domain.Activity;
import com.edwardxrx.crm.workbench.domain.Clue;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.workbench.service
 * @ClassName: ClueService
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/17 14:54
 * @Version: 1.0
 */
public interface ClueService {
    boolean save(Clue clue);

    Clue detail(String id);

}
