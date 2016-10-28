package cn.ymex.cocccute.flux.store;


import android.support.annotation.NonNull;

import java.util.ArrayList;

import cn.ymex.cocccute.flux.Params;
import cn.ymex.cocccute.flux.action.FluxActAction;
import cn.ymex.cocccute.flux.action.RequestAction;
import cn.ymex.cocccute.flux.store.entity.MovieEntity;
import cn.ymex.cocccute.flux.store.service.MovieService;
import cn.ymex.cute.mode.flux.Action;
import cn.ymex.cute.mode.flux.Store;
import cn.ymex.cute.mode.flux.StoreAlter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/10/27
 *
 * @author ymexc
 */
public class MessageStore extends Store {

    private MovieEntity movieEntity;
    private ArrayList<Call> calls;

    private void addCall(@NonNull Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void cancel() {
        if (calls == null || calls.size() <= 0) {
            return;
        }
        for (Call call : calls) {
            call.cancel();
        }
    }

    public MovieEntity getMovieEntity() {
        return movieEntity;
    }


    @Override
    public boolean onStoreAction(Action action) {
        Params params = (Params) action.getData();
        switch (action.getType()) {
            case FluxActAction.ACTION_CACEL_REQUEST:
                cancel();
                break;

            case FluxActAction.ACTION_GET_TOP250_MOVIES:
                emitStoreChange(RequestAction.Start());
                getMovie((Params) action.getData());
                return false;
        }
        return false;
    }

    //进行网络请求
    private void getMovie(Params params) {
        int start = (int) params.get("start");
        int count = (int) params.get("count");

        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieEntity> call = movieService.getTopMovie(start, count);
        addCall(call);
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                movieEntity = response.body();
                emitStoreChange(StoreAlter.bulid().action(new FluxActAction(FluxActAction.ACTION_GET_TOP250_MOVIES, null)).result("成功"));
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                emitStoreChange(RequestAction.FAILURE());
            }
        });
    }
}
