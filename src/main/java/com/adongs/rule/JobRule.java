package com.adongs.rule;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/21 4:34 下午
 * @modified By
 */
public class JobRule {

    public static final JobRule DEFAULT_RULE = new JobRule(
            "//div[@id='page-body']/div[@id='main-panel']/div[@class='dashboard']/div[@class='pane-frame']/table[@id='projectstatus']/tbody/tr[@class!='header']",
            "//td[1]/@data",
            "//td[1]/img/@src",
            "//td[1]/img/@tooltip",
            "//td[2]/@data",
            "//td[2]/a/img/@src",
            "//td[2]/div/table/tbody/tr[1]/td[2]/allText()",
            "//td[3]/a/allText()",
            "//td[4]/@data",
            "//td[4]/allText()",
            "//td[5]/@data",
            "td[5]/allText()",
            "//td[6]/@data",
            "//td[7]/a/@href"
            );

    public JobRule() {
    }

    public JobRule(String list, String status,String statusUrl,String statusTitle,
                   String buildHealth,String buildHealthUrl,String buildHealthTitle,
                   String name, String buildSuccessDatatime,String buildSuccessDatatimeDifference,
                   String buildFailureDatatime, String buildFailureDatatimeDifference,
                   String duration, String allowBuild) {
        this.list = list;
        this.status = status;
        this.statusUrl = statusUrl;
        this.statusTitle = statusTitle;
        this.buildHealth = buildHealth;
        this.buildHealthUrl = buildHealthUrl;
        this.buildHealthTitle = buildHealthTitle;
        this.name = name;
        this.buildSuccessDatatime = buildSuccessDatatime;
        this.buildSuccessDatatimeDifference = buildSuccessDatatimeDifference;
        this.buildFailureDatatime = buildFailureDatatime;
        this.buildFailureDatatimeDifference = buildFailureDatatimeDifference;
        this.duration = duration;
        this.allowBuild = allowBuild;
    }

    /**
     * 集合
     */
    private String list;
    /**
     * 上次构建状态
     */
    private String status;

    /**
     * 状态图标
     */
    private String statusUrl;

    /**
     * 状态标题
     */
    private String statusTitle;

    /**
     * 构建状态
     */
    private String buildHealth;

    /**
     * 构建状态url
     */
    private String buildHealthUrl;

    /**
     * 构建状态描述
     */
    private String buildHealthTitle;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 上次构建成功时间
     */
    private String buildSuccessDatatime;

    /**
     * 上次构建时间差
     */
    private String buildSuccessDatatimeDifference;

    /**
     * 上次构建失败时间
     */
    private String buildFailureDatatime;

    /**
     * 上次构建失败时间差
     */
    private String buildFailureDatatimeDifference;
    /**
     * 持续时间(单位毫秒)
     */
    private String duration;

    /**
     * 允许构建
     */
    private String allowBuild;

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuildHealth() {
        return buildHealth;
    }

    public void setBuildHealth(String buildHealth) {
        this.buildHealth = buildHealth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildSuccessDatatime() {
        return buildSuccessDatatime;
    }

    public void setBuildSuccessDatatime(String buildSuccessDatatime) {
        this.buildSuccessDatatime = buildSuccessDatatime;
    }

    public String getBuildFailureDatatime() {
        return buildFailureDatatime;
    }

    public void setBuildFailureDatatime(String buildFailureDatatime) {
        this.buildFailureDatatime = buildFailureDatatime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAllowBuild() {
        return allowBuild;
    }

    public void setAllowBuild(String allowBuild) {
        this.allowBuild = allowBuild;
    }

    public String getStatusUrl() {
        return statusUrl;
    }

    public void setStatusUrl(String statusUrl) {
        this.statusUrl = statusUrl;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public String getBuildHealthUrl() {
        return buildHealthUrl;
    }

    public void setBuildHealthUrl(String buildHealthUrl) {
        this.buildHealthUrl = buildHealthUrl;
    }

    public String getBuildHealthTitle() {
        return buildHealthTitle;
    }

    public void setBuildHealthTitle(String buildHealthTitle) {
        this.buildHealthTitle = buildHealthTitle;
    }

    public String getBuildSuccessDatatimeDifference() {
        return buildSuccessDatatimeDifference;
    }

    public void setBuildSuccessDatatimeDifference(String buildSuccessDatatimeDifference) {
        this.buildSuccessDatatimeDifference = buildSuccessDatatimeDifference;
    }

    public String getBuildFailureDatatimeDifference() {
        return buildFailureDatatimeDifference;
    }

    public void setBuildFailureDatatimeDifference(String buildFailureDatatimeDifference) {
        this.buildFailureDatatimeDifference = buildFailureDatatimeDifference;
    }
}
