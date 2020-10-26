package com.adongs.api.impl;

import com.adongs.api.BuildQueue;
import com.adongs.http.HttpReques;
import com.adongs.model.QueueJob;
import com.adongs.model.ResponseData;
import com.adongs.rule.BuildQeueRule;
import com.adongs.rule.ExecutorsRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.util.*;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/22 3:45 下午
 * @modified By
 */
public class BuildQueueImpl implements BuildQueue {

    private HttpReques httpReques;
    private BuildQeueRule buildQeueRule = BuildQeueRule.DEFAULT_RULE;
    private ExecutorsRule executorsRule = ExecutorsRule.DEFAULT_RULE;

    public BuildQueueImpl(HttpReques httpReques, BuildQeueRule buildQeueRule, ExecutorsRule executorsRule) {
        this.httpReques = httpReques;
        this.buildQeueRule = buildQeueRule;
        this.executorsRule = executorsRule;
    }

    public BuildQueueImpl(HttpReques httpReques) {
        this.httpReques = httpReques;
    }


    @Override
    public List<QueueJob> buildQueue() {
        List<QueueJob> queueJobs = new ArrayList<>();
        final ResponseData responseData = httpReques.post("/ajaxBuildQueue");
        if (responseData.isRead()){
            JXDocument  buildJobsJXDocument= new JXDocument(Jsoup.parse(new String(responseData.getData())).children());
            final List<JXNode> jxNodes = buildJobsJXDocument.selN(buildQeueRule.getList());
            if (!jxNodes.isEmpty()){
                for (JXNode jxNode : jxNodes) {
                    JXDocument buildJobJXDocument = new JXDocument(jxNode.asElement().children());
                    final String startUser = Optional.ofNullable(buildJobJXDocument.selOne(buildQeueRule.getStartUser())).map(s->s.toString().substring(s.toString().indexOf("Started by user ")+16,s.toString().indexOf("<br>"))).orElse("").toString();
                    final String name = Optional.ofNullable(buildJobJXDocument.selOne(buildQeueRule.getName())).orElse("").toString();
                    final String cancelUrl =  Optional.ofNullable(buildJobJXDocument.selOne(buildQeueRule.getAllowCancel())).orElse("").toString();
                    QueueJob queueJob = new QueueJob(name,startUser,cancelUrl);
                    queueJobs.add(queueJob);
                }
            }
        }
        return queueJobs;
    }



    @Override
    public List<QueueJob> executors() {
        List<QueueJob> queueJobs = new ArrayList<>();
        final ResponseData responseData = httpReques.post("/ajaxExecutors");
        if (responseData.isRead()){
            JXDocument  queueJobsJXDocument= new JXDocument(Jsoup.parse(new String(responseData.getData())).children());
            final List<JXNode> jxNodes = queueJobsJXDocument.selN(executorsRule.getList());
            if (!jxNodes.isEmpty()){
                jxNodes.remove(0);
                for (JXNode jxNode : jxNodes) {
                    JXDocument queueJobJXDocument = new JXDocument(jxNode.asElement().children());
                    final String name = Optional.ofNullable(queueJobJXDocument.selOne(executorsRule.getName())).orElse("").toString();
                    if (StringUtils.isEmpty(name)){continue;}
                    final String number = Optional.ofNullable(queueJobJXDocument.selOne(executorsRule.getNumber())).orElse("").toString();
                    final Integer schedule = Integer.valueOf(Optional.ofNullable(queueJobJXDocument.selOne(executorsRule.getSchedule())).map(p ->p.toString().substring(p.toString().indexOf(":") + 1, p.toString().indexOf("%"))).orElse("0").toString());
                    final String cancelUrl =  Optional.ofNullable(queueJobJXDocument.selOne(executorsRule.getAllowCancel())).orElse("").toString();
                    String userName = "";
                    final ResponseData data = httpReques.get("/job/" + name + "/" + number.substring(1) + "/api/json?tree=actions%5Bcauses%5BuserName%5D%5D%7B,1%7D", null);
                    if (data.isRead()){
                        final JSONObject dataJSon = JSON.parseObject(data.getData(), JSONObject.class);
                        final JSONArray actions = dataJSon.getJSONArray("actions");
                        if (!actions.isEmpty()){
                            final JSONArray causes = actions.getJSONObject(0).getJSONArray("causes");
                            if (!causes.isEmpty()){
                                userName = causes.getJSONObject(0).getString("userName");
                            }
                        }
                    }
                    QueueJob queueJob = new QueueJob(name,userName,number,schedule,cancelUrl);
                    queueJobs.add(queueJob);
                }
            }
        }
        return queueJobs;
    }
}
