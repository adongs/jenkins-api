package com.adongs.http;

import com.adongs.JenkinsClient;
import com.adongs.model.CrumbIssuer;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 确保令牌有效
 * @author yudong
 * @version 1.0
 * @date 2020/10/20 4:06 下午
 * @modified By
 */
public class CrumbInterceptor implements Interceptor {

    private final Pattern pattern = Pattern.compile(".+/job/.+/build.+");

    private final JenkinsClient.Server server;

    private static CrumbIssuer crumbIssuer = null;

    public CrumbInterceptor(JenkinsClient.Server server) {
        this.server = server;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        final String url = request.url().toString();
        final boolean matches = pattern.matcher(url).matches();
        if (response.code()==403 &&matches){
            response.close();
            final CrumbIssuer crumbIssuer = getCrumbIssuer(chain);
            if (crumbIssuer==null){return response;}
            final Request build = request.newBuilder().header(crumbIssuer.getCrumbRequestField(), crumbIssuer.getCrumb()).build();
             return chain.proceed(build);
        }
        return response;
    }

   public CrumbIssuer getCrumbIssuer(Chain chain)throws IOException{
        if (crumbIssuer!=null){
            return crumbIssuer;
        }
       final Request build = new Request.Builder().url(server.getUrl() + "/crumbIssuer/api/json").get().build();
       Response proceed = null;
       try {
            proceed = chain.proceed(build);
           if (proceed.isSuccessful()) {
               crumbIssuer = JSON.parseObject(proceed.body().bytes(), CrumbIssuer.class);
               return crumbIssuer;
           }
       }finally {
           if (proceed!=null){
               proceed.close();
           }
       }
       return null;
   }


}
