package org.zechac.atlas.rbac;

import lombok.Getter;

@Getter
public enum ResourceType {
    SUBSYS("子系统"), MODULE("模块"), STANDARD("标准菜单"), DYNAMICPAGE("动态页面");

    private String remark;

    ResourceType(String s) {
        this.remark = s;
    }

}
