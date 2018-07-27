package org.zechac.atlas.rbac;

import lombok.Getter;

@Getter
public enum SexType {
    MAN("男"), WOMAN("女");

    private String remark;

    SexType(String s) {
        this.remark = s;
    }
}
