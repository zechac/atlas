package org.zechac.atlas.controller;

import org.zechac.atlas.common.web.JsonResponse;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.zechac.atlas.common.web.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("workflow")
public class TaskController {
    @Autowired
    private ProcessEngine processEngine;

    @RequestMapping("task")
    public JsonResponse getTask(String user) {
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(user).list();
        return JsonResponse.success().data(taskList);
    }
}
