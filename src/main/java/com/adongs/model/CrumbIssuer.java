package com.adongs.model;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/11/3 10:12 上午
 * @modified By
 */
public class CrumbIssuer {

    private String crumb;

    private String crumbRequestField;

    public String getCrumb() {
        return crumb;
    }

    public void setCrumb(String crumb) {
        this.crumb = crumb;
    }

    public String getCrumbRequestField() {
        return crumbRequestField;
    }

    public void setCrumbRequestField(String crumbRequestField) {
        this.crumbRequestField = crumbRequestField;
    }
}
