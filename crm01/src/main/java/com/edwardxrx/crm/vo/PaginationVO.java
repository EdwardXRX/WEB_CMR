package com.edwardxrx.crm.vo;

import java.util.List;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.vo
 * @ClassName: PaginationVO
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/14 21:23
 * @Version: 1.0
 */
public class PaginationVO<T> {
    private int total;

    @Override
    public String toString() {
        return "PaginationVO{" +
                "total=" + total +
                ", dataList=" + dataList +
                '}';
    }

    private List<T> dataList ;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
