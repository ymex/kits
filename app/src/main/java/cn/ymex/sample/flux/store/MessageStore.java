package cn.ymex.sample.flux.store;


import android.support.annotation.NonNull;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;

import cn.ymex.sample.flux.action.FluxActAction;
import cn.ymex.sample.flux.action.RequestAction;
import cn.ymex.sample.flux.store.entity.MovieEntity;
import cn.ymex.sample.flux.store.service.MovieService;
import cn.ymex.kits.mode.flux.Action;
import cn.ymex.kits.mode.flux.Params;
import cn.ymex.kits.mode.flux.Store;
import cn.ymex.kits.mode.flux.StoreAction;
import cn.ymex.log.L;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
        switch (action.getType()) {
            case FluxActAction.ACTION_CACEL_REQUEST:
                //cancel();
                if (observable != null) {
                    observable.unsubscribeOn(Schedulers.io());
                }
                break;

            case FluxActAction.ACTION_GET_TOP250_MOVIES:
                emitStoreChange(RequestAction.Start());
                getMovewWithRxRetrofit((Params) action.getData());
                return false;
        }
        return false;
    }

    //进行网络请求 retrofit
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
                emitStoreChange(StoreAction.bulid().action(new FluxActAction(FluxActAction.ACTION_GET_TOP250_MOVIES)).result("成功"));
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                emitStoreChange(RequestAction.FAILURE());
            }
        });
    }
    Observable<MovieEntity> observable;

    private void getMovewWithRxRetrofit(Params params) {
        int start = params.integer("start");
        int count = params.integer("count");

        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MovieService movieService = retrofit.create(MovieService.class);
        observable = movieService.getTopMoview(start, count);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieEntity>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }


                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        L.d("onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(MovieEntity entity) {
                        L.d("onNext:" + entity.getTitle());
                        movieEntity = entity;
                        emitStoreChange(FluxActAction.bulid(FluxActAction.ACTION_GET_TOP250_MOVIES),"成功");
                    }

                });

    }
}
