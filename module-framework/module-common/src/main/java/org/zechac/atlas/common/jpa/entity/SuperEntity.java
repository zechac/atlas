package org.zechac.atlas.common.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by zhaozh on 2016/10/13/013.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class SuperEntity implements Serializable {
}
