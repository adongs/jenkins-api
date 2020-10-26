package com.adongs.api.impl;

import com.adongs.api.ViewInfo;
import com.adongs.http.HttpReques;
import com.adongs.model.Job;
import com.adongs.model.ResponseData;
import com.adongs.model.View;
import com.adongs.rule.JobRule;
import com.adongs.rule.MyViewRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/20 10:32 上午
 * @modified By
 */
public class ViewInfoImpl implements ViewInfo {

    private static final Map<String,Job> ALL_JOB = new HashMap<>(100);
    private boolean init = false;
    private HttpReques httpReques;
    private JobRule jobRule = JobRule.DEFAULT_RULE;
    private MyViewRule myViewRule = MyViewRule.DEFAULT_RULE;

    public ViewInfoImpl(HttpReques httpReques, JobRule jobRule, MyViewRule myViewRule) {
        this.httpReques = httpReques;
        this.jobRule = jobRule;
        this.myViewRule = myViewRule;
    }

    public ViewInfoImpl(HttpReques httpReques) {
        this.httpReques = httpReques;
    }

    @Override
    public List<View> global() {
        init();
        List<View> views = new ArrayList<>();
        final ResponseData responseData = httpReques.get("/api/json",null);
        if (responseData.isRead()){
            final JSONObject data = JSON.parseObject(responseData.getData(), JSONObject.class);
            final JSONArray viewsJson = data.getJSONArray("views");
            for (int i = 0,l=viewsJson.size(); i < l; i++) {
                final JSONObject viewJson= viewsJson.getJSONObject(i);
                final String path = urlPath(viewJson.getString("url"))+"api/json";
                final String description = viewJson.getString("description");
                List<Job> jobList = new ArrayList<>(20);
                final ResponseData jobs = httpReques.get(path, null);
                if (jobs.isRead()){
                    final JSONObject jobsJSONObject = JSON.parseObject(jobs.getData(), JSONObject.class);
                    final JSONArray jobsArray = jobsJSONObject.getJSONArray("jobs");
                    for (int j = 0,s=jobsArray.size(); j < s; j++) {
                        final JSONObject jsonObject = jobsArray.getJSONObject(j);
                        final String jobName = jsonObject.getString("name");
                        final Job job = ALL_JOB.get(jobName);
                        if (job!=null) {
                            jobList.add(job);
                        }
                    }
                }
                View view = new View(viewJson.getString("name"),path,description,jobList);
                views.add(view);
            }
        }
        return views;
    }
    /**
     * 同步任务信息
     */
    public boolean syncJobs(){
        final ResponseData responseData = httpReques.get("/view/all",null);
        if (responseData.isRead()){
            JXDocument taskRootJXDocument = new JXDocument(Jsoup.parse(new String(responseData.getData())).children());
            final List<JXNode> taskNode = taskRootJXDocument.selN(jobRule.getList());
            if (!taskNode.isEmpty()){
                ALL_JOB.clear();
                for (JXNode jxNode : taskNode) {
                    JXDocument jobJXDocument = new JXDocument(jxNode.asElement().children());
                    final String status = Optional.ofNullable(jobJXDocument.selOne(jobRule.getStatus())).orElse("").toString();
                    final Integer buildHealth = Integer.valueOf(Optional.ofNullable(jobJXDocument.selOne(jobRule.getBuildHealth())).orElse("-1").toString());
                    final String name = Optional.ofNullable(jobJXDocument.selOne(jobRule.getName())).orElse("").toString();
                    final String buildSuccessDatatime = Optional.ofNullable(jobJXDocument.selOne(jobRule.getBuildSuccessDatatime())).orElse("-").toString();
                    final String buildFailureDatatime = Optional.ofNullable(jobJXDocument.selOne(jobRule.getBuildFailureDatatime())).orElse("-").toString();
                    final Long duration = Long.valueOf(Optional.ofNullable(jobJXDocument.selOne(jobRule.getDuration())).orElse("-1").toString());
                    final boolean allowBuild = jobJXDocument.selOne(jobRule.getAllowBuild())!=null;
                    Job job = new Job(status,buildHealth,name,buildSuccessDatatime,buildFailureDatatime,duration,allowBuild);
                    ALL_JOB.put(name,job);
                }
                return true;
            }
        }
        return false;
    }


    private String urlPath(String urlStr){
        if (StringUtils.isEmpty(urlStr)){
            return "";
        }
        try {
            URL url = new URL(urlStr);
           return url.getPath();
        } catch (MalformedURLException e) {
            return "";
        }
    }


    private synchronized void init(){
        if (!init){
            final boolean isOK = syncJobs();
            if (isOK){
                init = true;
            }
        }
    }


    @Override
    public List<View> myView() {
        init();
        List<View> views = new ArrayList<>();
        final ResponseData responseData = httpReques.get("/me/my-views/view/all", null);
        if (responseData.isRead()){
            JXDocument viewsJXDocument = new JXDocument(Jsoup.parse(new String(responseData.getData())).children());
            final List<JXNode> taskNode = viewsJXDocument.selN(myViewRule.getList());
            if (!taskNode.isEmpty()){
                for (JXNode viewNode : taskNode) {
                    JXDocument viewJXDocument = new JXDocument(viewNode.asElement().children());
                    final String name = Optional.ofNullable(viewJXDocument.selOne(myViewRule.getName())).orElse("").toString();
                    if (!StringUtils.isEmpty(name)){
                        String path = "/me/my-views/view/"+name+"/api/json";
                        final ResponseData viewJob = httpReques.get(path, null);
                        if (viewJob.isRead()){
                            final JSONObject data = JSON.parseObject(viewJob.getData(), JSONObject.class);
                            final String description = data.getString("description");
                            final JSONArray jobsArray = data.getJSONArray("jobs");
                            List<Job> jobList = new ArrayList<>(20);
                            for (int j = 0,s=jobsArray.size(); j < s; j++) {
                                final JSONObject jsonObject = jobsArray.getJSONObject(j);
                                final String jobName = jsonObject.getString("name");
                                final Job job = ALL_JOB.get(jobName);
                                if (job!=null) {
                                    jobList.add(job);
                                }
                            }
                            View view = new View(name,path,description,jobList);
                            views.add(view);
                        }
                    }
                }
            }
        }
        return views;
    }
}
