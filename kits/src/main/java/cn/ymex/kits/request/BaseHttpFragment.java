package cn.ymex.kits.request;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import cn.ymex.kits.request.http.NoticeViewable;
import cn.ymex.kits.request.http.Noticeable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ymex on 2017/8/15.
 */

public class BaseHttpFragment extends Fragment implements Noticeable {


    protected NoticeViewable noticeView;
    protected boolean mIsVisibleToUser;
    protected HashMap<Integer, Disposable> mDisposableMap;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setNoticeView(NoticeViewable noticeView) {
        this.noticeView = noticeView;
    }

    @Override
    public boolean isShow() {
        if (noticeView == null) {
            return false;
        }
        return noticeView.isShow();
    }


    @Override
    public void showNotice() {
        if (noticeView != null&&!noticeView.isShow()) {
            noticeView.showNotice();
        }
    }

    @Override
    public void dismissNotice() {
        if (noticeView != null) {
            noticeView.dismissNotice();
        }
    }


    /**
     * fragment 是否对用户可见,此处不可见时取消提示框
     */

    public void onHiddenChanged(boolean hidden) {
        this.mIsVisibleToUser = !hidden;
        if (hidden && noticeView != null && noticeView.isShow()) {
            noticeView.dismissNotice();
        }
    }


    @Override
    public boolean isVisibleToUser() {
        return mIsVisibleToUser;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelDisposables();
    }
}
