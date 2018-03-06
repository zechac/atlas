package com.hongdee.atlas.demo.entity;

import com.hongdee.atlas.common.constant.Constant;
import com.hongdee.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Table(name = Constant.PERFIXX_TABLE_SYSTEM+"demo_many_2_many")
@Data
@Cacheable
@Entity
public class DemoMany2Many extends BaseEntity{

    @Basic
    private String name;


    @ManyToMany(fetch = FetchType.LAZY,cascade = {},mappedBy = "demoMany2Manies")
    private List<Demo> demoList;
}
