package com.lawrence.retrofitlearn;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Anthor：Lawrence Liu on 2018/5/17 13:57
 * Email：larry0712@foxmail.com
 */

public interface MovieService {
    //获取豆瓣Top250榜单
    @GET("top250")
    Observable<MovieSubject> getTop250(@Query("start") int start, @Query("count") int count);
}
