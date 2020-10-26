package com.adongs.rule;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/21 5:10 下午
 * @modified By
 */
public class MyViewRule {

    public static final MyViewRule DEFAULT_RULE = new MyViewRule("//div[@id='page-body']/div[@id='main-panel']/div[@class='dashboard']/div[@id='projectstatus-tabBar']/div[@class='tabBarFrame ']/div[@class='tabBar']/div","//a/allText()");

    private String list;

    private String name;

    public MyViewRule() {
    }

    public MyViewRule(String list, String name) {
        this.list = list;
        this.name = name;
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
}
