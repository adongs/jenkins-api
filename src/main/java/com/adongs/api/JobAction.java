package com.adongs.api;

import com.adongs.model.CrumbIssuer;
import com.adongs.model.QueueJob;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/23 2:09 下午
 * @modified By
 */
public interface JobAction {

    /**
     * 构建
     * @param jobName
     * @return
     */
    public boolean build(String jobName);

    /**
     * 取消构建
     * @param queueJob
     * @return
     */
    public boolean cancel(QueueJob queueJob);

    /**
     * 构建输出
     * @param buildOutput
     */
    public void buildOutput(BuildOutput buildOutput);

    /**
     * 取消输出
     * @param buildOutput
     */
    public void cancelBuildOutput(BuildOutput buildOutput);

    /**
     * 获取令牌
     * @return
     */
    public CrumbIssuer crumbIssuer();
}
