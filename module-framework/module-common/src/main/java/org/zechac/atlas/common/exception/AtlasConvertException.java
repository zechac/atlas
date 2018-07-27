package org.zechac.atlas.common.exception;

/**
 * 类型转换错误
 */
public class AtlasConvertException extends ModuleException {
    public AtlasConvertException() {
        super("AtlasConvertException");
    }

    public AtlasConvertException(String s) {
        super(s);
    }

    public AtlasConvertException(Class<?> clazz, String s) {
        super(clazz, s);
    }
}
