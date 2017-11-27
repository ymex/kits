package cn.ymex.sample.request;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import cn.ymex.kits.Finder;
import cn.ymex.rxretrofit.BaseNoticeActivity;
import cn.ymex.rxretrofit.OkHttpBuilder;
import cn.ymex.rxretrofit.http.ResultObserver;
import cn.ymex.rxretrofit.http.T;
import cn.ymex.rxretrofit.widget.SwipeRefreshNoticeView;
import cn.ymex.sample.R;
import cn.ymex.sample.flux.store.entity.MovieEntity;
import cn.ymex.widget.swipe.SwipeRefreshLayout;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class QueryHttpActivity extends BaseNoticeActivity {

    SwipeRefreshLayout layout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Finder finder = Finder.build(this);
        layout = finder.find(R.id.refresh_layout);
        textView = finder.find(R.id.tv_content);

        setNoticeView(new SwipeRefreshNoticeView(layout));

        provideRetrofit().create(TopServer.class)
                .getTopMoview(0, 10)
                .compose(T.create(this).<MovieEntity>transformer())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver<MovieEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);

                    }

                    @Override
                    public void onResult(MovieEntity movieEntity) {
                        super.onResult(movieEntity);
                        textView.setText(movieEntity.getTitle());
                    }
                });
    }


    public Retrofit provideRetrofit() {
        String baseUrl = "https://api.douban.com/v2/movie/";
        return new Retrofit.Builder()
                .client(OkHttpBuilder.getInstance().defaultClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public interface TopServer {
        @GET("top250")
        Observable<MovieEntity> getTopMoview(@Query("start") int start, @Query("count") int count);
    }
}
