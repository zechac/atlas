package com.hongdee.atlas.rbac;

/**
 * Created by My on 2018-04-01.
 */

import lombok.Getter;

/**
 * 方法名称: 状态枚举
 * 方法描述:？？是否可以将所有的状态使用一个枚举
 * 参数名称:
 * 参数名称:
 * 参数名称:
 * 返回内容:
 * 异常说明:
 * 创建用户: 王恒腾
 * 创建时间:
 * 修改用户: 王恒腾
 * 修改时间:
 * 修改内容:
 */

@Getter
public enum DictStatus {
    USE("启用"),DISABLED("禁用");
    private String remark;
    DictStatus(String s){
        this.remark=s;
    }
}
