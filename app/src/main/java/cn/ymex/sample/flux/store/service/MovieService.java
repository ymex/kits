package cn.ymex.sample.flux.store.service;

import cn.ymex.sample.flux.store.entity.MovieEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ymex on 2016/10/27.
 */

public interface MovieService {

    //only retrofit
    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

    //retrofit + rxjava
    @GET("top250")
    Observable<MovieEntity> getTopMoview(@Query("start") int start, @Query("count") int count);
}
