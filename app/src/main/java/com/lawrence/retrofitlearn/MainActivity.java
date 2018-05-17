package com.lawrence.retrofitlearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRetrofit();
    }

    private void initRetrofit() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //连接超时时间
        builder.connectTimeout(5000, TimeUnit.SECONDS);
        //写操作 超时时间
        builder.writeTimeout(5000, TimeUnit.SECONDS);
        //读操作超时时间
        builder.readTimeout(5000, TimeUnit.SECONDS);
        // 创建Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        //获取接口实例
        MovieService movieService = retrofit.create(MovieService.class);
        Subscription subscription = movieService.getTop250(0, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieSubject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieSubject movieSubject) {
                        List<MovieSubject.SubjectsBean> subjects = movieSubject.getSubjects();
                        for (int i = 0; i < subjects.size(); i++) {
                            System.out.println("电影名称：  " + subjects.get(i).getTitle());
                        }
                    }
                });
    }
}
