package org.zechac.atlas.common.exception;

import lombok.Data;
import lombok.Getter;

public class ModelException extends RuntimeException {

    public ModelException(){}

    @Getter
    private Class aClass;

    public ModelException(String msg,Class clazz){
        super(msg);
        this.aClass=clazz;
    }
}
