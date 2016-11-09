package prohit.androiddevelopertest.module;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;

import java.lang.reflect.Modifier;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import prohit.androiddevelopertest.base.ApiService;
import prohit.androiddevelopertest.base.MyApplication;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */
@Module
public class NetworkModule {


    private Application mApp;

    public NetworkModule(MyApplication application) {
        mApp = application;

    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(MyApplication application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        return gsonBuilder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(logging);

        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://dl.dropboxusercontent.com/")
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    ApiService provideApiServices(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
