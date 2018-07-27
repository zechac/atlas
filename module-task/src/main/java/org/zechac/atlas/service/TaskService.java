package org.zechac.atlas.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.entity.Task;
import org.zechac.atlas.repo.TaskRepo;
import org.zechac.atlas.common.services.BaseServiceImpl;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 姚猛 on 2018/3/28.
 *
 * @author 姚猛
 */
@Service
@Transactional
public class TaskService extends BaseServiceImpl<Task, TaskRepo> {


    public TaskService() throws SchedulerException {
    }

    public void scheduleJobs() throws SchedulerException {
    }

    StdSchedulerFactory factory = new StdSchedulerFactory();
    Scheduler scheduler = factory.getScheduler();

    /**
     * 开始一个定时任务
     *
     * @param time
     * @param jobname
     * @param groupname
     * @param job
     */
    public void startJob(String time, String jobname, String groupname, String job) {
        try {
            Class jobClass = Class.forName(job);
            //设置Job的名字和组
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobname, groupname).build();
            //动态添加数据
            jobDetail.getJobDataMap().put("name", "MyName");
            //  corn表达式  每2秒执行一次 time 0/2 * * * * ?
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            //设置定时任务的时间触发规则
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobname, groupname).withSchedule(scheduleBuilder).build();
            StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = stdSchedulerFactory.getScheduler();
            scheduler.start();
            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 修改定时任务时间
     * @param jobName
     * @param triggerGroupName
     * @param time
     */
    public void modifyJobTime(String jobName, String triggerGroupName, String time) {
        try {
            StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = stdSchedulerFactory.getScheduler();
            TriggerKey triggerKey = new TriggerKey(jobName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            System.out.println(scheduler.getTriggerState(triggerKey));
            String oldTime = trigger.getCronExpression();
            // Trigger已存在，那么更新相应的定时设置
            if (!oldTime.equalsIgnoreCase(time)) {
                //设置一个新的定时时间
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
                // 按新的cronExpression表达式重新构建trigger
                CronTrigger cronTrigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                // 按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, cronTrigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /****
     * 删除一个任务
     * @param jobName
     * @param triggerGroupName
     */
    public void deleteJob(String jobName, String triggerGroupName) {
        try {
            JobKey jobKey = new JobKey(jobName, triggerGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                return;
            }
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /***
     * 开始定时任务
     */
    public void startAllJob() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /***
     * 立即执行定时任务
     */
    public void doJob(String jobName, String triggerGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, triggerGroupName);
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭定时任务
     */
    public void shutdown() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /****
     * 暂停一个任务
     * @param jobName
     * @param triggerGroupName
     */
    public void pauseJob(String jobName, String triggerGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, triggerGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                return;
            }
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /****
     * 恢复一个任务
     * @param jobName
     * @param triggerGroupName
     */
    public void resumeJob(String jobName, String triggerGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, triggerGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                return;
            }
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取quartz调度器的运行任务
     *
     * @return
     */

    public List<Task> getScheduleJobRunningList() {
        List<Task> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<Task>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Task job = new Task();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setClassadd(jobKey.getClass().toString());
                job.setJobDesc("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                } else if (trigger instanceof SimpleTrigger) {
                    SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
                    long milliseconds = simpleTrigger.getRepeatInterval();
                    //job.setTimeValue((int) (milliseconds/1000));
                }
                jobList.add(job);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobList;
    }
}