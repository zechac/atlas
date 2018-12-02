package org.zechac.atlas.metadata.entity;

import org.zechac.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author作者：王恒腾 类名：
 * 功能：字典实体类
 * 时间：2018-04-01/23:20
 */
@Table(name = "t_bc_dict")
@Entity
@Data
public class Dict extends BaseEntity {
    @Column(name = "d_code")
    private String dCode;
    @Column(name = "d_label")
    private String dLabel;
    @Column(name = "d_p_code")
    private String dPCode;
    @Column(name = "d_remark")
    private String dRemark;
    @Column(name = "d_type")
    private String dType;
    @Transient
    private List<Dict> dictList;
    @JoinColumn
    @ManyToOne
    private Dict dict;
}
