package com.edwardxrx.crm.settings.service.impl;

import com.edwardxrx.crm.settings.dao.DicTypeDao;
import com.edwardxrx.crm.settings.dao.DicValueDao;
import com.edwardxrx.crm.settings.domain.DicType;
import com.edwardxrx.crm.settings.domain.DicValue;
import com.edwardxrx.crm.settings.service.DicService;
import com.edwardxrx.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.settings.service.impl
 * @ClassName: DicServiceImpl
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/17 15:11
 * @Version: 1.0
 */
public class DicServiceImpl implements DicService {

    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);



    @Override
    public Map<String, List<DicValue>> getAll() {

        Map<String,List<DicValue>> map = new HashMap<>();
        //将字典类型列表取出
        List<DicType> dtList = dicTypeDao.getTypeList();

        //遍历字典类型列表

        for(DicType dc: dtList)
        {
            //获得每一种类型的信息
            String code = dc.getCode();

            //根据每一个字典类型取得字典值表
            List<DicValue> dvList = dicValueDao.getValueList(code);

            map.put(code+"List",dvList);

        }
        return map;
    }
}
