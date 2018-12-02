package org.zechac.atlas.rbac.entity;

import org.zechac.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 姚猛 on 2018/3/29.
 *
 * @author 姚猛
 */
@Table(name = "t_taskinformation")
@Entity
@Data
public class TaskInformation extends BaseEntity {

    /**
     * 任务名称
     */
    @Column(name = "t_name")
    private String jobName;

    /**
     * 任务分组名称
     */
    @Column(name = "t_group")
    private String jobGroup;

    /**
     * 最近执行时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "t_recenttime")
    private Date recenttime;

    /**
     * 执行时长
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "t_executiontime")
    private Date executiontime;

    /**
     * 执行信息
     */
    @Column(name = "t_message")
    private String message;
}