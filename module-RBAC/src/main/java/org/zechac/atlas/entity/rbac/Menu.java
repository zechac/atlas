package org.zechac.atlas.entity.rbac;

import com.alibaba.fastjson.annotation.JSONType;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import org.zechac.atlas.rbac.MenuStatus;
import org.zechac.atlas.rbac.MenuType;
import lombok.Data;
import org.zechac.atlas.rbac.MenuStatus;
import org.zechac.atlas.rbac.MenuType;

import javax.persistence.*;
import java.util.List;

/**
 * 菜单
 */
@Table(name = "t_bc_menu")
@Entity
@Data
@JSONType(ignores = {"parent", "children"})
public class Menu extends BaseEntity {

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
    private MenuType type;

    @Enumerated(EnumType.STRING)
    private MenuStatus status;

    @OneToMany(mappedBy = "parent")
    private List<Menu> children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "r_parent_id")
    private Menu parent;

    /**
     * 关联动态页面的菜单
     */
    private String dynamicPageId;

    private String description;

}
