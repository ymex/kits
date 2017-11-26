package cn.ymex.kits.request.http;

/**
 * 继承重写 isShow showNotice dismissNotice。
 */

public interface NoticeViewable {
    boolean isShow();

    void showNotice();

    void dismissNotice();
}
