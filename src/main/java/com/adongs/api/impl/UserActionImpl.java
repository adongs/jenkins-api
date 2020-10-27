package com.adongs.api.impl;

import com.adongs.api.UserAction;
import com.adongs.http.HttpReques;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/26 4:34 下午
 * @modified By
 */
public class UserActionImpl implements UserAction {

    private HttpReques httpReques;

    public UserActionImpl(HttpReques httpReques) {
        this.httpReques = httpReques;
    }


    @Override
    public void logout() {
        httpReques.logout();
    }
}
