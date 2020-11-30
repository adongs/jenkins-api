package com.adongs.http;

import java.util.Optional;
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
    public Optional<String> token();

    /**
     * 保存token
     * @param token
     */
    public void save(String token,long time,TimeUnit timeUnit);


   public static class DefaultTokenSave implements TokenSave{

       @Override
       public Optional<String> token() {
           return Optional.empty();
       }

       @Override
       public void save(String token, long time, TimeUnit timeUnit) {

       }
   }

}
