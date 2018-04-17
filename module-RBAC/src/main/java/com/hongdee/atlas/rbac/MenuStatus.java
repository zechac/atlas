package com.hongdee.atlas.rbac;

import lombok.Getter;

@Getter
public enum  MenuStatus {
    USE("启用"),DISABLED("禁用");

    private String remark;

    MenuStatus(String s){
        this.remark=s;
    }
}
