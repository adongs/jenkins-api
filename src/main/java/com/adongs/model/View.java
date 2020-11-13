package com.adongs.model;

import java.util.List;
import java.util.Set;

/**
 * 视图
 * @author yudong
 * @version 1.0
 * @date 2020/10/20 9:58 上午
 * @modified By
 */
public class View {

    /**
     * 视图名称
     */
    private String name;

    /**
     * 视图地址
     */
    private String urlPath;

    /**
     * 说明
     */
    private String description;

    private Set<String> jobs;


    public View(String name, String urlPath, String description, Set<String> jobs) {
        this.name = name;
        this.urlPath = urlPath;
        this.description = description;
        this.jobs = jobs;
    }

    public String getName() {
        return name;
    }

    public Set<String> getJobs() {
        return jobs;
    }

    public String getUrlPath() {
        return urlPath;
    }
}
