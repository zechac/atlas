package com.hongdee.atlas.common.exception;

public class AtlasException extends ModuleException{
    public AtlasException(Class<?> clazz, String msg) {
        super(clazz, msg);
    }
}
