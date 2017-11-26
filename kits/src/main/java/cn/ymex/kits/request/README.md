
```java

@Singleton
@Provides
public OkHttpClient provideOkHttpClient() {
    return new OkHttpClient.Builder()
            .addInterceptor(new LogInterceptor())
            .connectTimeout(20, TimeUnit.SECONDS)
            .build();
}

@Singleton
@Provides
public Retrofit provideRetrofit(OkHttpClient client) {
    return new Retrofit.Builder()
            .client(client)
            .baseUrl(Constant.APIURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}


//retrofit2 请求接口

public interface QueryService {
    /**
     *menu/search?key=51c39ddd603b&cid=0010001007&page=1&size=20
     * key String key,
     * cid String cid,
     * page int page,
     * size int size
     *
     * @param options
     * @return
     */
    @GET("menu/search")
    Observable<ResultRecipe> getQueryRecipe(@QueryMap Params options );
}

//具体方法请求事例
public class QueryRepository {


    /**
     * 分类查询
     * @param noticeable
     * @param observer
     * @param cid 分类id
     * @param page 页数
     */
    public void getCookingMenu(String cid , int page,Noticeable noticeable, Observer<ResultRecipe> observer) {
        Params params = Params.stream()
                .with("key",Constant.APP_KEY)
                .with("cid",cid)
                .with("page",page)
                .with("size",Constant.PAGE_SIZE);

        AppContext.getAppComponent()
                .getRetrofit()
                .create(QueryService.class)
                .getQueryRecipe(params)
                .compose(new T<ResultRecipe>(noticeable).transformer())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
```