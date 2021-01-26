package com.lilin.dubboserviceprovider.quartz;

import org.quartz.*;


/**
 * 不允许并发执行
 */
@DisallowConcurrentExecution
public class JobTwo implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data=context.getTrigger().getJobDataMap();
        String invokeParam =(String) data.get("invokeParam");
        //在这里实现业务逻辑
    }
}
