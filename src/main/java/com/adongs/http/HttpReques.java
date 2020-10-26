package com.adongs.http;

import com.adongs.JenkinsClient;
import com.adongs.model.ResponseData;
import com.adongs.model.TestLoginResult;
import okhttp3.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/20 4:57 下午
 * @modified By
 */
public class HttpReques {

    private OkHttpClient client ;
    private final OkHttpClient testClient;
    private final JenkinsClient.Server server;
    private final static String LOGIN_URL = "/j_acegi_security_check";
    private final CookieMemory cookieMemory ;

    public HttpReques(TokenSave tokenSave, JenkinsClient.Server server) {
        this.cookieMemory = new CookieMemory(tokenSave);
        final TokenInterceptor tokenInterceptor = new TokenInterceptor(server,cookieMemory, LOGIN_URL);
        client =  new OkHttpClient.Builder().cookieJar(cookieMemory)
        .addInterceptor(new SimulateBrowserInterceptor())
        .addInterceptor(tokenInterceptor)
        .build();
        testClient = new OkHttpClient.Builder().build();
        this.server = server;
    }

    /**
     * 测试登录是否成功
     * @param name
     * @param password
     * @return
     */
    public TestLoginResult testLogin(String name, char [] password){
        RequestBody body = new FormBody.Builder()
                .add("j_username",server.getName())
                .add("j_password",new String(server.getPassword()))
                .add("from","/")
                .add("Submit","登录")
                .add("remember_me","on").build();
        final Request login = new Request.Builder().url(server.getUrl() + LOGIN_URL).post(body).build();
        try (final  Response response = testClient.newCall(login).execute()){
            final int code = response.code();
            if (code>=200 && code<400){
                return new TestLoginResult(true,"登录成功");
            }
            return new TestLoginResult(true,"登录失败");
        }catch (IOException e){
            return new TestLoginResult(false,e.getMessage());
        }catch (Exception e){
            return new TestLoginResult(false,e.getMessage());
        }
    }

    /**
     * 登出
     */
    public void logout(){
        cookieMemory.delete();
    }

    /**
     * get请求
     * @param url
     * @return
     */
    public ResponseData get(String url, Map<String,String> param){
        final Request.Builder builder = new Request.Builder();
        if (param!=null && !param.isEmpty()){
            HttpUrl.Builder urlBuilder =HttpUrl.parse(server.getUrl()+url)
                    .newBuilder();
            param.forEach(urlBuilder::addQueryParameter);
         builder.url(urlBuilder.build());
        }else{
            builder.url(server.getUrl()+url);
        }
        final Request build =builder.build();
        int code = -1;
        try(final Response execute = client.newCall(build).execute()){
            code = execute.code();
            return new ResponseData(execute.body().bytes(),code);
        }catch (Exception e){
            return new ResponseData(code,e.getMessage());
        }
    }
    public Response getResponse(String url, Map<String,String> param)throws IOException{
        final Request.Builder builder = new Request.Builder();
        if (param!=null && !param.isEmpty()){
            HttpUrl.Builder urlBuilder =HttpUrl.parse(server.getUrl()+url)
                    .newBuilder();
            param.forEach(urlBuilder::addQueryParameter);
            builder.url(urlBuilder.build());
        }else{
            builder.url(server.getUrl()+url);
        }
        final Request build =builder.build();
        return client.newCall(build).execute();
    }


    /**
     * post请求
     * @param url
     * @return
     */
    public ResponseData post(String url){
        Request request = new Request.Builder()
                .post(new FormBody.Builder().build())
                .url(server.getUrl()+url).build();
        int code = -1;
        try(Response response = client.newCall(request).execute()){
            code = response.code();
            return new ResponseData(response.body().bytes(),code);
        }catch (Exception e){
            return new ResponseData(code,e.getMessage());
        }
    }


    public Response postResponse(String url, Map<String,String> param)throws IOException{
        final FormBody.Builder builder = new FormBody.Builder();
        if (param!=null && !param.isEmpty()){
            param.forEach(builder::add);
        }
        Request request = new Request.Builder()
                .post(builder.build())
                .url(server.getUrl()+url).build();
       return client.newCall(request).execute();
    }
}
