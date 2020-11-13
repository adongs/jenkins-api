package com.adongs.api;

import com.adongs.model.Job;
import com.adongs.model.View;

import java.util.List;
import java.util.Map;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/20 10:30 上午
 * @modified By
 */
public interface ViewInfo {

    /**
     * 全局视图
     * @return
     */
    List<View> global();

    /**
     * 我的视图
     * @return
     */
    List<View> myView();

    /**
     * 获取视图中的所有job
     * @return
     */
    Map<String, Job> jobAll();

    /**
     * 同步任务
     * @return
     */
    boolean syncJobs();
}
