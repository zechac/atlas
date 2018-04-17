package com.hongdee.atlas.rbac;

import lombok.Getter;

/**
*@author作者：王恒腾
*类名：
*功能：部门状态
*时间：2018-03-28/18:36
*
*/
@Getter
public enum OrganStatus
{
    USE("启用"),DISABLED("禁用");
    private String remark;
    OrganStatus(String s){
        this.remark=s;
    }
}
