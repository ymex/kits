package cn.ymex.cocccute.flux.store.service;

import cn.ymex.cocccute.flux.store.entity.MovieEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ymex on 2016/10/27.
 */

public interface MovieService {
    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
}
