package com.adongs.http;

import com.adongs.JenkinsClient;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * 确保令牌有效
 * @author yudong
 * @version 1.0
 * @date 2020/10/20 4:06 下午
 * @modified By
 */
public class TokenInterceptor implements Interceptor {

    private final JenkinsClient.Server server;
    private final OkHttpClient client;
    private final String url;

    public TokenInterceptor(JenkinsClient.Server server,CookieMemory cookieMemory,String url) {
        this.server = server;
        this.url = url;
        client = new OkHttpClient.Builder().cookieJar(cookieMemory)
                .addInterceptor(new SimulateBrowserInterceptor()).build();
    }


    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (response.code()==403 && server.complete()){
            RequestBody body = new FormBody.Builder()
                    .add("j_username",server.getName())
                    .add("j_password",new String(server.getPassword()))
                    .add("from","/")
                    .add("Submit","登录")
                    .add("remember_me","on").build();
            final Request login = new Request.Builder().url(server.getUrl() + url).post(body).build();
            final Response execute = client.newCall(login).execute();
            if (execute.code()==403){
                return response;
            }else{
                execute.close();
                response.close();
                return chain.proceed(request);
            }
        }
        return response;
    }
}
