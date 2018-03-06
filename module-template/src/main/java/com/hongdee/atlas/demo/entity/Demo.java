package com.hongdee.atlas.demo.entity;


import com.alibaba.fastjson.annotation.JSONType;
import com.hongdee.atlas.common.constant.Constant;
import com.hongdee.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = Constant.PERFIXX_TABLE_SYSTEM+"demo")
@Data
@Cacheable
@Entity
@JSONType(ignores = {"demoMany2Many","demoOne2Many"})
public class Demo extends BaseEntity{

    @Basic
    private String name;

    @JoinColumn(name = "r_damo_one_2_many")
    @ManyToOne
    private DemoOne2Many demoOne2Many;


    @ManyToMany(fetch = FetchType.LAZY,cascade = {})
    @JoinTable(name="r_demo_many_to_many",
            joinColumns={@JoinColumn(name="dome_r_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="demo_id",referencedColumnName="id")})
    private List<DemoMany2Many> demoMany2Manies;
}
