package com.adongs.model;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/21 9:33 上午
 * @modified By
 */
public class TestLoginResult {

    private boolean ok;

    private String msg;

    public TestLoginResult(boolean ok, String msg) {
        this.ok = ok;
        this.msg = msg;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
