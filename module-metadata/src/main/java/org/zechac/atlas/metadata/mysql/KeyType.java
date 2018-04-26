package org.zechac.atlas.metadata.mysql;

import lombok.Getter;

@Getter
public enum KeyType {
    PRI("主键"), MUL("外键"), UNI("唯一");

    private String remark;

    KeyType(String remark) {
        this.remark = remark;
    }

}
