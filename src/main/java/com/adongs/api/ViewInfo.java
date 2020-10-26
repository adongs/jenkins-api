package com.adongs.api;

import com.adongs.model.View;

import java.util.List;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/20 10:30 上午
 * @modified By
 */
public interface ViewInfo {

    /**
     * 全局视图
     * @return
     */
    List<View> global();

    /**
     * 我的视图
     * @return
     */
    List<View> myView();

}
