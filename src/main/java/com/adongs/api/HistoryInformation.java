package com.adongs.api;

import com.adongs.model.BuildHistory;
import com.adongs.model.BuildInformation;
import com.adongs.model.Job;
import org.jetbrains.annotations.NotNull;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/11/10 2:25 下午
 * @modified By
 */
public interface HistoryInformation {

    public BuildHistory buildistory(@NotNull String name);

    public BuildInformation buildInformation();

}
