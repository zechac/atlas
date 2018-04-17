package com.hongdee.atlas.dynamic;

import lombok.Getter;

@Getter
public enum FormType {
    INPUT("input"),SELECT("select");

    private String remark;

    FormType(String s){
        this.remark=s;
    }
}
