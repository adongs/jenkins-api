package com.adongs.http;

import java.util.concurrent.TimeUnit;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/20 10:50 上午
 * @modified By
 */
public interface TokenSave {
    /**
     * 获取token
     * @return
     */
    public String token();

    /**
     * 保存token
     * @param token
     */
    public void save(String token,long time,TimeUnit timeUnit);

    /**
     * 获取过期时间
     * @param token
     */
    public long time();

    /**
     * 返回是否过期
     * @return
     */
    public boolean expired();

    /**
     * 删除
     */
    public void delete();



   public static class DefaultTokenSave implements TokenSave{
       @Override
       public String token() {
           return null;
       }

       @Override
       public void save(String token, long time, TimeUnit timeUnit) {

       }

       @Override
       public long time() {
           return 0;
       }

       @Override
       public boolean expired() {
           return false;
       }

       @Override
       public void delete() {

       }
   }

}
