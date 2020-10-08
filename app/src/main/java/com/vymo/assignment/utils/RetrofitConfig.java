package com.vymo.assignment.utils;

import android.content.Context;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitConfig {
    Retrofit retro;

    public RetrofitConfig(final Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request newRequest = chain.request().newBuilder()
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(newRequest);
            }
        };


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();


        this.retro = new Retrofit.Builder()
                .baseUrl("https://api.github.com/repos/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }


    public Retrofit getRetro() {
        return retro;
    }

    public void setRetro(Retrofit retro) {
        this.retro = retro;
    }
}
