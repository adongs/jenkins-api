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
public class SimulateBrowserInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        final Headers.Builder header = new Headers.Builder();
        header.add("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        header.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36");
        header.add("Upgrade-Insecure-Requests","1");
        header.add("DNT","1");
        final Request build = chain.request().newBuilder().headers(header.build()).build();
        return chain.proceed(build);
    }
}
