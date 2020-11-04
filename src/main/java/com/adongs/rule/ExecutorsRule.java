package com.adongs.rule;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/22 3:47 下午
 * @modified By
 */
public class ExecutorsRule {

    public final static ExecutorsRule DEFAULT_RULE = new ExecutorsRule(
            "//div[@id='executors']/div[@class='row pane-content']/table[@class='pane ']/tbody/tr",
            "//td[2]/div/a/allText()",
            "//td[2]/div/span/allText()",
            "//td[3]/a/allText()",
            "//td[2]/div/table/tbody/tr/td[1]/@style",
            "//td[4]/a/@href");

    private String list;
    /**
     * 名称
     */
    private String name;

    /**
     * 任务通道
     */
    private String taskChannel;
    /**
     * 编号
     */
    private String number;

    /**
     * 执行进读
     */
    private String schedule;

    /**
     * 是否允许取消
     */
    private String allowCancel;

    public ExecutorsRule() {
    }

    public ExecutorsRule(String list, String name,String taskChannel, String number, String schedule, String allowCancel) {
        this.list = list;
        this.name = name;
        this.taskChannel = taskChannel;
        this.number = number;
        this.schedule = schedule;
        this.allowCancel = allowCancel;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskChannel() {
        return taskChannel;
    }

    public void setTaskChannel(String taskChannel) {
        this.taskChannel = taskChannel;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getAllowCancel() {
        return allowCancel;
    }

    public void setAllowCancel(String allowCancel) {
        this.allowCancel = allowCancel;
    }
}
