package com.adongs.api;

import com.adongs.model.TestLoginResult;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/26 4:33 下午
 * @modified By
 */
public interface LoginAction {

    /**
     * 测试登录
     * @param name
     * @param password
     */
    public TestLoginResult testLogin(String name, char[] password);

    /**
     * 登出
     */
    public void logout();
}
