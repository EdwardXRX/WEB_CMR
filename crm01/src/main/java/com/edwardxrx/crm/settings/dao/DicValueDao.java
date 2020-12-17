package com.edwardxrx.crm.settings.dao;

import com.edwardxrx.crm.settings.domain.DicValue;

import java.util.List;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.settings.dao
 * @ClassName: DicValueDao
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/17 15:09
 * @Version: 1.0
 */
public interface DicValueDao {
    List<DicValue> getValueList(String code);
}
