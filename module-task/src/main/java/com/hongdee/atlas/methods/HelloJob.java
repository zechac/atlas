package com.hongdee.atlas.methods;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 姚猛 on 2018/3/27.
 * @author 姚猛
 */
public class HelloJob implements Job{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        System.err.println("现在的时间："+simpleDateFormat.format(date));
        int a=5*10;
        //System.err.println("定时任务计算：a="+a);
    }
}
