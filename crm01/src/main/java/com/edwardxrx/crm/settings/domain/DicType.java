package com.edwardxrx.crm.settings.domain;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.settings.domain
 * @ClassName: DicType
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/17 15:03
 * @Version: 1.0
 */
public class DicType {
    private String code;

    private String name;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
