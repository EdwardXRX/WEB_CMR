package com.edwardxrx.crm.workbench.service;

import com.edwardxrx.crm.workbench.domain.Tran;
import com.edwardxrx.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.workbench.service
 * @ClassName: TranService
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/23 19:47
 * @Version: 1.0
 */
public interface TranService {
    boolean save(Tran t, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);

    boolean changeStage(Tran t);

    Map<String, Object> getCharts();
}
