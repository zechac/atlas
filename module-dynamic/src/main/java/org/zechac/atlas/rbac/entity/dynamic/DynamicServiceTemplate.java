package org.zechac.atlas.rbac.entity.dynamic;

import com.alibaba.fastjson.JSONArray;
import org.zechac.atlas.common.jpa.convert.StringAndJSONArrayConverter;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 */
@Table(name = "sys_dynamic_service_template")
@Entity
@Data
public class DynamicServiceTemplate extends BaseEntity {

    private String name;

    private String description;

    /**
     * 服务调用模板
     * [{
     * kind:"sql-select",
     * sql:"select * from table where a=#a"
     * },{
     * kind:""
     * }]
     */
    @Convert(converter = StringAndJSONArrayConverter.class)
    @Column(name = "service_template", columnDefinition = "text")
    private JSONArray serviceTemplate;

}
