package com.wzj.nio01;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

/**
 * （必做）写一段代码，使用 HttpClient 或 OkHttp
 *  访问  http://localhost:8801
 *  @author wangzhijie
 */
public class VisitWebsite {

    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://localhost:8801";
        Request request = new Request.Builder().url(url).get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            System.out.println(response.code()==200?"访问成功":"访问出错 {}"+response.body().string());
            System.out.println(response);
            String responseData = response.body().string();
            System.out.println(responseData);
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }


}
