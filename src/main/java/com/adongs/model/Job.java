package com.adongs.model;

/**
 * 当前任务
 * @author yudong
 * @version 1.0
 * @date 2020/10/20 9:50 上午
 * @modified By
 */
public class Job {
    /**
     * 上次构建状态
     */
    private String status;

    /**
     * 构建状态
     */
    private Integer buildHealth;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 上次构建成功时间
     */
    private String buildSuccessDatatime;

    /**
     * 上次构建失败时间
     */
    private String buildFailureDatatime;

    /**
     * 持续时间(单位毫秒)
     */
    private Long duration;

    /**
     * 是否允许构建
     */
    private boolean allowBuild;

    public Job(String status, Integer buildHealth, String name, String buildSuccessDatatime, String buildFailureDatatime, Long duration, boolean allowBuild) {
        this.status = status;
        this.buildHealth = buildHealth;
        this.name = name;
        this.buildSuccessDatatime = buildSuccessDatatime;
        this.buildFailureDatatime = buildFailureDatatime;
        this.duration = duration;
        this.allowBuild = allowBuild;
    }

    public String getStatus() {
        return status;
    }

    public Integer getBuildHealth() {
        return buildHealth;
    }

    public String getName() {
        return name;
    }

    public String getBuildSuccessDatatime() {
        return buildSuccessDatatime;
    }

    public String getBuildFailureDatatime() {
        return buildFailureDatatime;
    }

    public Long getDuration() {
        return duration;
    }

    public boolean isAllowBuild() {
        return allowBuild;
    }
}
