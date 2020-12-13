package com.edwardxrx.crm.workbench.domain;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.workbench.domain
 * @ClassName: Activity
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/13 20:45
 * @Version: 1.0
 */
public class Activity {
    private String id;   //主键
    private String OWNER;           //外键    tbl_user
    private String NAME;      //市场活动名称
    private String startDate;       //开始时间
    private String endDate;         //结束时间    年月日
    private String cost;            //成本
    private String description;        //描述
    private String createTime;      //创建时间
    private String createBy;        //创建人
    private String editTime;        //结束时间
    private String editBy;         //结束人       年月日时分秒

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOWNER() {
        return OWNER;
    }

    public void setOWNER(String OWNER) {
        this.OWNER = OWNER;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }
}
