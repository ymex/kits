package cn.ymex.kits.request.http;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class T<T> {
    public static AtomicInteger GK = new AtomicInteger(0);
    private WeakReference<Noticeable> noticeable;

    public T() {
        this(null);
    }

    public T(Noticeable noticeable) {
        this.noticeable = new WeakReference<Noticeable>(noticeable);
    }

    /**
     * 模式预处理
     *
     * @return
     */
    public ObservableTransformer<T, T> transformer() {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource apply(@NonNull Observable upstream) {

                return upstream
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (noticeable.get() != null && !noticeable.get().isShow() && noticeable.get().isVisibleToUser()) {
                                    noticeable.get().showNotice();
                                }
                                if (noticeable.get() != null) {
                                    noticeable.get().setDisposable(GK.addAndGet(1), disposable);
                                }
                            }
                        })
                        .doOnError(new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (noticeable.get() != null) {
                                    noticeable.get().dismissNotice();
                                }
                            }
                        })
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (noticeable.get() != null) {
                                    noticeable.get().dismissNotice();
                                }
                            }
                        })
                        .doOnDispose(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (noticeable.get() != null) {
                                    noticeable.get().dismissNotice();
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
