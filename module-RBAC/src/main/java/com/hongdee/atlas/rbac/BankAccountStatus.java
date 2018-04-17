package com.hongdee.atlas.rbac;

import lombok.Getter;

/**
 * Created by My on 2018-03-28.
 */
@Getter
public enum BankAccountStatus {
    USE("启用"),DISABLED("禁用");
    private String remark;
    BankAccountStatus(String s){
        this.remark=s;
    }
}
