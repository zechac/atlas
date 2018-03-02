package com.hongdee.atlas.common;

/**
 * 服务类型：
 * 1.内置的Spring服务
 * 2.插件式的java服务
 * 3.URL的服务
 * 4.组合式服务
 */
public enum ServiceType {

    INTERNAL("内置服务"),
    EXTERNAL("插件服务"),
    URL("URL服务"),
    COMPLEX("组合服务");

    private String remark;

    ServiceType(String s){
        this.remark=s;
    }

    public String getRemark(){
        return remark;
    }
}
