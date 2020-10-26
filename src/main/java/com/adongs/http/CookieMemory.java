package com.adongs.http;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/20 3:18 下午
 * @modified By
 */
public class CookieMemory  implements CookieJar {

    private final List<Cookie> cookieStore = new ArrayList<>();
    private final static String TOKEN_NAME = "ACEGI_SECURITY_HASHED_REMEMBER_ME_COOKIE";
    private final TokenSave tokenSave;

    public CookieMemory(@NotNull TokenSave tokenSave) {
        this.tokenSave = tokenSave;
        final String token = tokenSave.token();
        if(!StringUtils.isEmpty(token)){
            final Cookie build = new Cookie.Builder().name(TOKEN_NAME).value(token).expiresAt(tokenSave.time()).build();
            if (tokenSave!=null) {
                cookieStore.add(build);
            }
        }
    }

    @Override
    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
        for (Cookie cookie : list) {
            if (TOKEN_NAME.equals(cookie.name())){
                if (tokenSave!=null) {
                    tokenSave.save(cookie.value(), cookie.expiresAt(), TimeUnit.SECONDS);
                }
            }
        }
        cookieStore.addAll(list);
    }

    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
        return cookieStore;
    }

    public void delete(){
        tokenSave.delete();
        cookieStore.clear();
    }
}