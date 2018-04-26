package org.zechac.atlas.metadata.exception;

import org.zechac.atlas.common.exception.ModuleException;

public class MetadataException extends ModuleException {

    public MetadataException(Class<?> clazz, String msg) {
        super(clazz, msg);
    }
}
