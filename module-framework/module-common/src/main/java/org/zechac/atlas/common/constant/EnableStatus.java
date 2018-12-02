package org.zechac.atlas.common.constant;

import lombok.Getter;

@Getter
public enum EnableStatus {
    USE("启用"), DISABLED("禁用");

    private String remark;

    EnableStatus(String s) {
        this.remark = s;
    }
}
