package org.zechac.atlas.common.exception;

import lombok.Getter;

/**
 * 模块异常
 */
@Getter
public class ModuleException extends RuntimeException {
    private String msg;
    private Class<?> clazz = null;

    public ModuleException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ModuleException(Class<?> clazz, String msg) {
        super(msg);
        this.msg = msg;
        this.clazz = clazz;
    }

    /**
     * 获取自定义的消息
     *
     * @return
     */
    public String moduleExceptionString() {
        String s = "";
        if (clazz != null) {
            s += "[" + clazz.toString() + "]";
        }
        s += msg;
        return s;
    }
}
