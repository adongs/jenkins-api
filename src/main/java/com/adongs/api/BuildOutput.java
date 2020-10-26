package com.adongs.api;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/23 2:14 下午
 * @modified By
 */
public interface BuildOutput {

    /**
     * 任务名称
     * @return
     */
    public String jobName();

    /**
     * 任务编号
     * @return
     */
    public String number();
    /**
     * 构建输出
     * @param content 内容
     * @param isFinish 是否结束
     */
    public void output(String content,boolean isFinish);

    /**
     * 是否中断
     * @return
     */
    public boolean discontinue();

    /**
     * 设置中断
     */
    public void setDiscontinue();
}
