package com.adongs.model;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/22 2:33 下午
 * @modified By
 */
public class QueueJob {

    /**
     * 任务名称
     */
    private String name;

    /**
     * 启动用户
     */
    private String startUser;
    /**
     * 编号
     */
    private String number;

    /**
     * 执行进读
     */
    private int schedule;

    /**
     * 取消url
     */
    private String cancelUrl;


    public QueueJob(String name,String startUser, String number, int schedule, String cancelUrl) {
        this.name = name;
        this.startUser = startUser;
        this.number = number;
        this.schedule = schedule;
        this.cancelUrl = cancelUrl;
    }

    public QueueJob(String name, String startUser, String cancelUrl) {
        this.name = name;
        this.startUser = startUser;
        this.cancelUrl = cancelUrl;
    }

    public String getStartUser() {
        return startUser;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public int getSchedule() {
        return schedule;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }
}
