package cn.ymex.kits.request;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import cn.ymex.kits.request.http.NoticeViewable;
import cn.ymex.kits.request.http.Noticeable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ymex on 2017/8/15.
 */

public class BaseHttpActivity extends AppCompatActivity implements Noticeable {

    protected NoticeViewable noticeView;
    protected boolean mIsVisibleToUser;
    protected HashMap<Integer, Disposable> mDisposableMap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mIsVisibleToUser = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mIsVisibleToUser = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelDisposables();
    }

    public void setNoticeView(NoticeViewable noticeView) {
        this.noticeView = noticeView;
    }

    //<editor-fold desc="Noticeable 接口方法">
    @Override
    public boolean isShow() {
        if (noticeView == null) {
            return false;
        }
        return noticeView.isShow();
    }

    @Override
    public boolean isVisibleToUser() {
        return mIsVisibleToUser;
    }

    @Override
    public void showNotice() {
        if (noticeView != null && !noticeView.isShow()) {
            noticeView.showNotice();
        }
    }

    @Override
    public void dismissNotice() {
        if (noticeView != null) {
            noticeView.dismissNotice();
        }
    }

    @Override
    public void setDisposable(int key, Disposable disposable) {
        getDisposableMap().put(key, disposable);
    }

    public HashMap<Integer, Disposable> getDisposableMap() {
        return mDisposableMap == null ? new HashMap<Integer, Disposable>() : mDisposableMap;

    }

    /**
     * 取消所有的订阅
     */
    public void cancelDisposables() {
        for (Map.Entry<Integer, Disposable> item : getDisposableMap().entrySet()) {
            Disposable disposable = item.getValue();
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        getDisposableMap().clear();
        dismissNotice();
    }

    public void cancelDisposable(int key) {
        Disposable disposable = getDisposableMap().get(key);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            getDisposableMap().put(key, null);
        }
        return;
    }


    public void cancelDisposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        return;
    }

    //</editor-fold>
}
