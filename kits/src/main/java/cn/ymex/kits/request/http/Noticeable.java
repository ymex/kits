package cn.ymex.kits.request.http;


import io.reactivex.disposables.Disposable;

public interface Noticeable {

    /**
     * 提示的当前 状态
     * @return
     */
    boolean isShow();


    /**
     * 显示提示
     */
    void showNotice();

    /**
     * 用于隐藏提示
     */
    void dismissNotice();

    /**
     * 当前面用户是否可见,如fragment 对用户不可见,当其更新数据时不能出现对话框
     * @return
     */
    boolean isVisibleToUser();


    /**
     * 用于取消订阅
     * @param key
     * @param disposable
     */
    void setDisposable(int key, Disposable disposable);


}
