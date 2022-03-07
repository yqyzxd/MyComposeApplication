package com.github.yqyzxd.data;


import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        Request originalRequest=chain.request();
        Request.Builder builder=originalRequest.newBuilder();
        builder.addHeader("Origin","1");
        Request request = builder
                .method(originalRequest.method(), originalRequest.body())
                .build();
        Response response=chain.proceed(request);
        return response;
    }



}
