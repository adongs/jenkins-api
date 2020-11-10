package com.adongs.http;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * 模拟浏览器请求
 * @author yudong
 * @version 1.0
 * @date 2020/10/20 3:50 下午
 * @modified By
 */
public class LanguageInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        final Headers.Builder header = new Headers.Builder();
         header.add("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
        final Request build = chain.request().newBuilder().headers(header.build()).build();
        return chain.proceed(build);
    }
}
