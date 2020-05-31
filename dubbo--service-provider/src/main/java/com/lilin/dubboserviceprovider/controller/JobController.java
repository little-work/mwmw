package com.lilin.dubboserviceprovider.controller;


import com.lilin.dubboserviceprovider.mybatis.entity.AppQuartz;
import com.lilin.dubboserviceprovider.quartz.JobUtil;
import com.lilin.dubboserviceprovider.service.Quartz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    @Autowired
    private JobUtil jobUtil;
    @Autowired
    private Quartz appQuartzService;


    //添加一个job
    @RequestMapping(value = "/addJob", method = RequestMethod.GET)
    public String addjob() throws Exception {
        AppQuartz appQuartz = new AppQuartz();
        appQuartz.setQuartzId("000002");
        appQuartz.setJobGroup("JobOne");
        appQuartz.setJobName("job2");
        appQuartz.setStartTime("2020-05-31 21:31:00");
        appQuartz.setCronExpression("0/3 * * * * ?");
        appQuartz.setInvokeParam("李林");

        appQuartzService.insertAppQuartzSer(appQuartz);
        jobUtil.addJob(appQuartz);
        return "添加定时任务成功";
    }

    /*//暂停job
    @RequestMapping(value="/pauseJob",method=RequestMethod.POST)
    public ReturnMsg pausejob(@RequestBody Integer[]quartzIds) throws Exception {
        AppQuartz appQuartz=null;
        if(quartzIds.length>0){
            for(Integer quartzId:quartzIds) {
                appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId).get(0);
                jobUtil.pauseJob(appQuartz.getJobName(), appQuartz.getJobGroup());
            }
            return new ReturnMsg("200","success pauseJob");
        }else {
            return new ReturnMsg("404","fail pauseJob");
        }
    }

    //恢复job
    @RequestMapping(value="/resumeJob",method=RequestMethod.POST)
    public ReturnMsg resumejob(@RequestBody Integer[]quartzIds) throws Exception {
        AppQuartz appQuartz=null;
        if(quartzIds.length>0) {
            for(Integer quartzId:quartzIds) {
                appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId).get(0);
                jobUtil.resumeJob(appQuartz.getJobName(), appQuartz.getJobGroup());
            }
            return new ReturnMsg("200","success resumeJob");
        }else {
            return new ReturnMsg("404","fail resumeJob");
        }
    }


    //删除job
    @RequestMapping(value="/deletJob",method=RequestMethod.POST)
    public ReturnMsg deletjob(@RequestBody Integer[]quartzIds) throws Exception {
        AppQuartz appQuartz=null;
        for(Integer quartzId:quartzIds) {
            appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId).get(0);
            String ret=jobUtil.deleteJob(appQuartz);
            if("success".equals(ret)) {
                appQuartzService.deleteAppQuartzByIdSer(quartzId);
            }
        }
        return new ReturnMsg("200","success deleteJob");
    }

    //修改
    @RequestMapping(value="/updateJob",method=RequestMethod.POST)
    public ReturnMsg  modifyJob(@RequestBody AppQuartz appQuartz) throws Exception {
        String ret= jobUtil.modifyJob(appQuartz);
        if("success".equals(ret)) {
            appQuartzService.updateAppQuartzSer(appQuartz);
            return new ReturnMsg("200","success updateJob",ret);
        }else {
            return new ReturnMsg("404","fail updateJob",ret);
        }
    }

    //暂停所有
    @RequestMapping(value="/pauseAll",method=RequestMethod.GET)
    public ReturnMsg pauseAllJob() throws Exception {
        jobUtil.pauseAllJob();
        return new ReturnMsg("200","success pauseAll");
    }

    //恢复所有
    @RequestMapping(value="/repauseAll",method=RequestMethod.GET)
    public ReturnMsg repauseAllJob() throws Exception {
        jobUtil.resumeAllJob();
        return new ReturnMsg("200","success repauseAll");
    }*/
}
