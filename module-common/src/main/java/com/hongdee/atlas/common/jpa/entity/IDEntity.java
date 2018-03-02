package com.hongdee.atlas.common.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zhaozh on 2016/6/29.
 */
@MappedSuperclass
@Data
@Access(AccessType.FIELD)
public class IDEntity extends SuperEntity{
    @Id
    @GeneratedValue(generator = "generatedkey")
    @GenericGenerator(name = "generatedkey", strategy = "uuid")
    @Column(length=32)
    private String id;
}
