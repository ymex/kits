package cn.ymex.kits.request.widget;



import cn.ymex.kits.request.http.NoticeViewable;
import cn.ymex.widget.swipe.SwipeRefreshLayout;

/**
 * SwipeRefreshLayout作为加载提醒控件
 */

public class SwipeRefreshNoticeView implements NoticeViewable {
    private SwipeRefreshLayout refreshLayout;

    public SwipeRefreshNoticeView(SwipeRefreshLayout layout) {
        this.refreshLayout = layout;
    }

    @Override
    public boolean isShow() {
        return refreshLayout.isRefreshing();

    }


    @Override
    public void showNotice() {
        refreshLayout.setRefresh(true);
    }

    @Override
    public void dismissNotice() {
        refreshLayout.finishRefresh(800);
    }
}
