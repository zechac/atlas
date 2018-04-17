package com.hongdee.atlas.entity;


import com.hongdee.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by 姚猛 on 2018/3/22.
 * @author 姚猛
 */
@Table(name="t_task")
@Entity
@Data
public class Task extends BaseEntity{

    /** 任务名称 */
    @Column(name = "t_name")
    private String jobName;

    /** 任务分组名称*/
    @Column(name = "t_group")
    private String jobGroup;

    /** 定时任务对应的类（包括包路径），如:com.hongdee.atlas.methods.HelloJob*/
    @Column(name = "t_classadd")
    private String classadd;

    /** 任务状态：1停用 2启用*/
    @Column(name = "t_status")
    private String jobStatus;

    /** 任务运行时间表达式 */
    @Column(name = "t_cron")
    private String cronExpression;

    /** 任务描述 */
    @Column(name = "t_desc")
    private String jobDesc;

}
