package org.zechac.atlas.rbac.entity;

import com.alibaba.fastjson.annotation.JSONType;
import org.zechac.atlas.common.constant.EnableStatus;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import org.zechac.atlas.rbac.ResourceType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 菜单
 */
@Table(name = "rabc_resource")
@Entity
@Data
@JSONType(ignores = {"parent", "children"})
public class Resource extends BaseEntity {

    private String id;

    private String name;

    private int level;

    private String url;

    /**
     * 动态页面路由参数
     */
    @Column(name = "params", columnDefinition = "text")
    private String params;

    private String code;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

    @Enumerated(EnumType.STRING)
    private EnableStatus status;

    @OneToMany(mappedBy = "parent")
    private List<Resource> children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "r_parent_id")
    private Resource parent;

    /**
     * 关联动态页面的菜单
     */
    private String dynamicPageId;

    private String description;

}
