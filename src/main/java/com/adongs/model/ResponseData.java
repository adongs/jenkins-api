package com.adongs.model;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/21 10:06 上午
 * @modified By
 */
public class ResponseData {

    private final  byte[] data;
    private final int code;
    private String msg;

    public ResponseData(byte[] data, int code) {
        this.data = data;
        this.code = code;
    }

    public ResponseData(int code, String msg) {
        data = null;
        this.code = code;
        this.msg = msg;
    }

    public byte[] getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public boolean isRead(){
        return code>=200 && code<=299 && data!=null && data.length>0;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
