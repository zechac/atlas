package com.hongdee.atlas.metadata.exception;

import com.hongdee.atlas.common.exception.ModuleException;

public class MetadataException extends ModuleException{

    public MetadataException(Class<?> clazz, String msg) {
        super(clazz, msg);
    }
}
