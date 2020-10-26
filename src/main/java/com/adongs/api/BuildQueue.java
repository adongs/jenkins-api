package com.adongs.api;

import com.adongs.model.QueueJob;

import java.util.List;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/22 2:31 下午
 * @modified By
 */
public interface BuildQueue {


    /**
     * 获取构建队列数据
     * @return
     */
    public List<QueueJob> buildQueue();

    /**
     * 获取执行中的数据
     * @return
     */
    public List<QueueJob> executors();


}
