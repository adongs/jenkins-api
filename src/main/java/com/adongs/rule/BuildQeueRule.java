package com.adongs.rule;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/22 3:47 下午
 * @modified By
 */
public class BuildQeueRule {

    public final static BuildQeueRule DEFAULT_RULE = new BuildQeueRule(
            "//div[@id='buildQueue']/div[2]/table/tbody/tr",
            "//td[1]/a/allText()",
            "//td[1]/a/@tooltip",
            "//td[2]/a/@href"
    );

    /**
     * 集合
     */
    private String list;

    /**
     * 名称
     */
    private String name;

    /**
     * 启动用户
     */
    private String startUser;

    /**
     * 是否允许取消
     */
    private String allowCancel;

    public BuildQeueRule() {
    }

    public BuildQeueRule(String list, String name, String startUser, String allowCancel) {
        this.list = list;
        this.name = name;
        this.startUser = startUser;
        this.allowCancel = allowCancel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getAllowCancel() {
        return allowCancel;
    }

    public void setAllowCancel(String allowCancel) {
        this.allowCancel = allowCancel;
    }

    public String getStartUser() {
        return startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser;
    }
}
