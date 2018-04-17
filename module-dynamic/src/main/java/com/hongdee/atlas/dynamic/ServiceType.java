package com.hongdee.atlas.dynamic;

import lombok.Getter;

@Getter
public enum ServiceType {
    NORMAL("普通服务"),EDITABLE("可配置服务");

    private String remark;

    ServiceType(String s){
        this.remark=s;
    }
}
