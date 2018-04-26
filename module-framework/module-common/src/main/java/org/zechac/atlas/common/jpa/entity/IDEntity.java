package org.zechac.atlas.common.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by zhaozh on 2016/6/29.
 */
@MappedSuperclass
@Data
@Access(AccessType.FIELD)
public class IDEntity extends SuperEntity {
    @Id
    @GeneratedValue(generator = "generatedkey")
    @GenericGenerator(name = "generatedkey", strategy = "uuid2")
    @Column(length = 64)
    protected String id;
}
