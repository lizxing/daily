package com.lizxing.daily.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(address);
        Request request = builder.build();
        client.newCall(request).enqueue(callback);
    }
}
