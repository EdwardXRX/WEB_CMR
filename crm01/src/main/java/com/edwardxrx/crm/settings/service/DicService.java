package com.edwardxrx.crm.settings.service;

import com.edwardxrx.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.settings.service
 * @ClassName: DicService
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/17 15:11
 * @Version: 1.0
 */
public interface DicService {
    Map<String, List<DicValue>> getAll();
}
