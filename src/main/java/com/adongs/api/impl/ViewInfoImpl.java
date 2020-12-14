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
import com.sun.deploy.util.ArrayUtil;
import com.sun.tools.javac.util.ArrayUtils;
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
                Set<String> jobList = new HashSet<>(20);
                final ResponseData jobs = httpReques.get(path, null);
                if (jobs.isRead()){
                    final JSONObject jobsJSONObject = JSON.parseObject(jobs.getData(), JSONObject.class);
                    final JSONArray jobsArray = jobsJSONObject.getJSONArray("jobs");
                    for (int j = 0,s=jobsArray.size(); j < s; j++) {
                        final JSONObject jsonObject = jobsArray.getJSONObject(j);
                        jobList.add(jsonObject.getString("name"));
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
    @Override
    public boolean syncJobs(){
        final ResponseData responseData = httpReques.get("/view/all",null);
        if (responseData.isRead()){
            JXDocument taskRootJXDocument = new JXDocument(Jsoup.parse(new String(responseData.getData())).children());
            final String serverUrl = httpReques.getServer().getUrl();
            final List<JXNode> taskNode = taskRootJXDocument.selN(jobRule.getList());
            if (!taskNode.isEmpty()){
                ALL_JOB.clear();
                for (JXNode jxNode : taskNode) {
                    JXDocument jobJXDocument = new JXDocument(jxNode.asElement().children());
                    final String statusStr = Optional.ofNullable(jobJXDocument.selOne(jobRule.getStatus())).orElse("").toString();
                     String url = Optional.ofNullable(jobJXDocument.selOne(jobRule.getStatusUrl())).orElse("").toString();
                    if (!url.isEmpty()){
                        url = serverUrl+url;
                    }
                    final String title = Optional.ofNullable(jobJXDocument.selOne(jobRule.getStatusTitle())).orElse("").toString();
                    Job.Status status = new Job.Status(url,title,statusStr);
                    final String buildHealthStr = Optional.ofNullable(jobJXDocument.selOne(jobRule.getBuildHealth())).orElse("0").toString();
                    final Iterator<String> buildHealthUrls = new HashSet<String>(Arrays.asList(jobRule.getBuildHealthUrl().split(","))).iterator();
                    String buildHealthUrl = "";
                    while (StringUtils.isEmpty(buildHealthUrl) && buildHealthUrls.hasNext()){
                        buildHealthUrl = Optional.ofNullable(jobJXDocument.selOne(buildHealthUrls.next())).orElse("").toString();
                    }
                    if (!buildHealthUrl.isEmpty()){
                        buildHealthUrl = serverUrl+buildHealthUrl;
                    }
                    final String buildHealthTitle = Optional.ofNullable(jobJXDocument.selOne(jobRule.getBuildHealthTitle())).orElse("").toString();
                    Job.Status buildHealth = new Job.Status(buildHealthUrl,buildHealthTitle,buildHealthStr);
                    final String name = Optional.ofNullable(jobJXDocument.selOne(jobRule.getName())).orElse("").toString();
                    final String buildSuccessDatatimeStr = Optional.ofNullable(jobJXDocument.selOne(jobRule.getBuildSuccessDatatime())).orElse("-").toString();
                    final String buildSuccessDatatimeDifference = Optional.ofNullable(jobJXDocument.selOne(jobRule.getBuildSuccessDatatimeDifference())).orElse("-").toString();
                    Job.DateTime buildSuccessDatatime = new Job.DateTime(buildSuccessDatatimeStr,buildSuccessDatatimeDifference);
                    final String buildFailureDatatimeStr = Optional.ofNullable(jobJXDocument.selOne(jobRule.getBuildFailureDatatime())).orElse("-").toString();
                    final String buildFailureDatatimeDifference = Optional.ofNullable(jobJXDocument.selOne(jobRule.getBuildFailureDatatime())).orElse("-").toString();
                    Job.DateTime buildFailureDatatime = new Job.DateTime(buildFailureDatatimeStr,buildFailureDatatimeDifference);
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
                            Set<String> jobList = new HashSet<>(20);
                            for (int j = 0,s=jobsArray.size(); j < s; j++) {
                                final JSONObject jsonObject = jobsArray.getJSONObject(j);
                                jobList.add(jsonObject.getString("name"));
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

    @Override
    public Map<String, Job> jobAll() {
        return new HashMap<>(ALL_JOB);
    }
}
