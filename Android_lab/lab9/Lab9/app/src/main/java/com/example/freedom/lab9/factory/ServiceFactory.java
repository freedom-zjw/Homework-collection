package com.example.freedom.lab9.factory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by freedom on 2017/12/19.
 * */

/**
 * Retrofit 是对 OkHttp 的封装，提供了使用注解更简单的构建各种请求，配置各种参数的方式。
 * 本质发起网络请求的还是 OkHttp，但 Retrofit 让这一操作更加的简单优雅。OKHttp通过http请求返回一个
 * json格式的数据
 */

/**
 * OkHttp 只负责发起网络请求，维护网络连接等操作，而 Retrofit 帮我们将网络传输的数据转换为可用的 model 对象，并且提供简单的数据处理方式。
 配置 CallAdapterFactory 为 RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()) 用来将返回的数据转换为 RxJava 的 Observable 对象
 配置 ConverterFactory 为 GsonConverterFactory.create(new Gson()) 用来使用 GSON 将网络数据序列化为可用对象。
 */
public class ServiceFactory {
        public static Retrofit createRetrofit(String baseUrl){
            return new Retrofit.Builder()   //创建一个新的Retrofit对象
                    .baseUrl(baseUrl) //设置baseURL，后续访问都是这个url目录下的
                    .addConverterFactory(GsonConverterFactory.create()) //添加GSON Converter
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //RxJavaCall Adapter
                    .client(createOkHttp()) //okHttp
                    .build();
        }

        public static OkHttpClient createOkHttp(){  //自己配置相应的okHttp
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS) //连接超时
                    .readTimeout(30, TimeUnit.SECONDS)   //读超时
                    .writeTimeout(10, TimeUnit.SECONDS) //写超时
                    .build();
            return okHttpClient;
        }
}
