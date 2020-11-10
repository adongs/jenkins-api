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
    private Status status;

    /**
     * 构建状态
     */
    private Status buildHealth;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 上次构建成功时间
     */
    private DateTime buildSuccessDatatime;

    /**
     * 上次构建失败时间
     */
    private DateTime buildFailureDatatime;

    /**
     * 持续时间(单位毫秒)
     */
    private Long duration;

    /**
     * 是否允许构建
     */
    private boolean allowBuild;


    public Job(Status status, Status buildHealth, String name, DateTime buildSuccessDatatime, DateTime buildFailureDatatime, Long duration, boolean allowBuild) {
        this.status = status;
        this.buildHealth = buildHealth;
        this.name = name;
        this.buildSuccessDatatime = buildSuccessDatatime;
        this.buildFailureDatatime = buildFailureDatatime;
        this.duration = duration;
        this.allowBuild = allowBuild;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getBuildHealth() {
        return buildHealth;
    }

    public void setBuildHealth(Status buildHealth) {
        this.buildHealth = buildHealth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getBuildSuccessDatatime() {
        return buildSuccessDatatime;
    }

    public void setBuildSuccessDatatime(DateTime buildSuccessDatatime) {
        this.buildSuccessDatatime = buildSuccessDatatime;
    }

    public DateTime getBuildFailureDatatime() {
        return buildFailureDatatime;
    }

    public void setBuildFailureDatatime(DateTime buildFailureDatatime) {
        this.buildFailureDatatime = buildFailureDatatime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public boolean isAllowBuild() {
        return allowBuild;
    }

    public void setAllowBuild(boolean allowBuild) {
        this.allowBuild = allowBuild;
    }

    public static class Status{

        private String iconUrl;
        private String title;
        private String data;

       public Status(String iconUrl, String title, String data) {
           this.iconUrl = iconUrl;
           this.title = title;
           this.data = data;
       }

       public String getIconUrl() {
           return iconUrl;
       }

       public void setIconUrl(String iconUrl) {
           this.iconUrl = iconUrl;
       }

       public String getTitle() {
           return title;
       }

       public void setTitle(String title) {
           this.title = title;
       }

       public String getData() {
           return data;
       }

       public void setData(String data) {
           this.data = data;
       }
   }

   public static class DateTime{
       private String data;
       private String description;

       public DateTime(String data, String description) {
           this.data = data;
           this.description = description;
       }

       public String getData() {
           return data;
       }

       public void setData(String data) {
           this.data = data;
       }

       public String getDescription() {
           return description;
       }

       public void setDescription(String description) {
           this.description = description;
       }
   }
}
