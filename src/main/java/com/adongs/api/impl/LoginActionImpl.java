package com.adongs.api.impl;

import com.adongs.api.LoginAction;
import com.adongs.http.HttpReques;
import com.adongs.model.TestLoginResult;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/26 4:34 下午
 * @modified By
 */
public class LoginActionImpl implements LoginAction {

    private HttpReques httpReques;

    public LoginActionImpl(HttpReques httpReques) {
        this.httpReques = httpReques;
    }

    @Override
    public TestLoginResult testLogin(String name, char[] password) {
        return httpReques.testLogin(name, password);
    }


    @Override
    public void logout() {
        httpReques.logout();
    }
}
