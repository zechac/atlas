package org.zechac.atlas.rbac.controller;

import org.zechac.atlas.common.web.JsonResponse;
import org.zechac.atlas.common.web.RequestParamUtils;
import org.zechac.atlas.rbac.entity.Task;
import org.zechac.atlas.rbac.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by 姚猛 on 2018/3/23.
 *
 * @author 姚猛
 */
@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 开始一个定时任务
     *
     * @param id
     */
    @RequestMapping("add/{id}")
    public void start(@PathVariable String id) {
        try {
            Task task = taskService.findById(id);
            taskService.startJob(task.getCronExpression(), task.getJobName(), task.getJobGroup(), task.getClassadd());
            System.out.println("开始一个任务成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改定时任务
     *
     * @param id
     */
    @RequestMapping("modify/{id}")
    public void modify(@PathVariable String id) {
        try {
            Task task = taskService.findById(id);
            taskService.modifyJobTime(task.getJobName(), task.getJobGroup(), "*/10 * * * * ?");
            System.out.println("修改成功");
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 立即执行定时任务
     */
    @RequestMapping("do/{id}")
    public void doJob(@PathVariable String id) {
        try {
            Task task = taskService.findById(id);
            taskService.doJob(task.getJobName(), task.getJobGroup());
            System.out.println("立即执行成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭定时任务
     */
    @RequestMapping("shutdown")
    public void shutdown() {
        try {
            taskService.shutdown();
            System.out.println("关闭成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一个任务
     */
    @RequestMapping("delete/{id}")
    public void deleteJob(@PathVariable String id) {
        try {
            Task task = taskService.findById(id);
            taskService.deleteJob(task.getJobName(), task.getJobGroup());
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库中定时任务列表
     *
     * @param pageable
     * @return
     */
    @RequestMapping("list")
    public JsonResponse list(Pageable pageable, @RequestParam Map param) {
        Map query = RequestParamUtils.mapRequestParam(param, "s_");
        Page<Task> metadataGroups = taskService.queryByPage(query, pageable);
        return JsonResponse.success().data(metadataGroups);
    }


    @RequestMapping("running")
    public List getScheduleJobRunningList(Pageable pageable, @RequestParam Map param) {

        List task = taskService.getScheduleJobRunningList();
        //System.out.println("删除成功");
        return task;
    }

    /**
     * 保存或修改数据库中定时任务的详细信息
     *
     * @param task
     * @return
     */
    @RequestMapping("save")
    public JsonResponse save(@RequestBody Task task) {
        taskService.save(task);
        return JsonResponse.success().data("成功");
    }

    /**
     * 删除数据库中的定时任务信息
     *
     * @param id
     * @return
     */
    @RequestMapping("delete/task/{id}")
    public JsonResponse delete(@PathVariable String id) {
        taskService.deleteById(id);
        return JsonResponse.success().data("成功");
    }
}
