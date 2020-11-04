package com.adongs.api.impl;

import com.adongs.api.BuildOutput;
import com.adongs.api.JobAction;
import com.adongs.http.HttpReques;
import com.adongs.model.CrumbIssuer;
import com.adongs.model.QueueJob;
import com.adongs.model.ResponseData;
import com.adongs.model.TimedTask;
import com.alibaba.fastjson.JSON;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/23 2:32 下午
 * @modified By
 */
public class JobActionImpl implements JobAction {

    private static final Timer timer = new Timer("jenkins-api-timer");

    private HttpReques httpReques;

    public JobActionImpl(HttpReques httpReques) {
        this.httpReques = httpReques;
    }

    @Override
    public boolean build(String jobName) {
        Map<String,String> param = new HashMap<>();
        param.put("delay","0sec");
        final CrumbIssuer crumbIssuer = crumbIssuer();
        if (crumbIssuer!=null){

        }
        try(final Response response = httpReques.postResponseParam("/job/" + jobName + "/build", param)){
           return response.isSuccessful();
        }catch (IOException e){
            return false;
        }
    }

    @Override
    public boolean cancel(QueueJob queueJob) {
        final boolean empty = StringUtils.isEmpty(queueJob.getCancelUrl());
        if (empty){
            return false;
        }
        final ResponseData post = httpReques.post(queueJob.getCancelUrl());
        return post.getCode()==200;
    }

    @Override
    public void buildOutput(BuildOutput buildOutput) {
        TimedTask timedTask = new TimedTask(buildOutput,httpReques);
        timer.schedule(timedTask,1000,1500);
    }

    @Override
    public void cancelBuildOutput(BuildOutput buildOutput) {
        buildOutput.setDiscontinue();
    }


    @Override
    public CrumbIssuer crumbIssuer() {
        final ResponseData responseData = httpReques.get("/crumbIssuer/api/json", null);
        if (responseData.isRead()){
          return JSON.parseObject(responseData.getData(),CrumbIssuer.class);
        }
        return null;
    }
}
