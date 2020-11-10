package com.adongs.api.impl;

import com.adongs.api.HistoryInformation;
import com.adongs.http.HttpReques;
import com.adongs.model.BuildHistory;
import com.adongs.model.BuildInformation;
import com.adongs.model.Job;
import com.adongs.model.ResponseData;
import com.alibaba.fastjson.JSON;
import org.jetbrains.annotations.NotNull;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/11/10 2:25 下午
 * @modified By
 */
public class HistoryInformationImpl implements HistoryInformation {
    private HttpReques httpReques;

    public HistoryInformationImpl(HttpReques httpReques) {
        this.httpReques = httpReques;
    }

    @Override
    public BuildHistory buildistory(@NotNull String name) {
        final ResponseData responseData = httpReques.get("/job/" + name + "/api/json", null);
        if (responseData.isRead()){
            return JSON.parseObject(responseData.getData(), BuildHistory.class);
        }
        return null;
    }

    @Override
    public BuildInformation buildInformation() {
       throw new RuntimeException("未实现");
    }
}
