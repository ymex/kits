package cn.ymex.cute.widget.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.ymex.cute.kits.Optional;
import cn.ymex.cute.kits.ViewKit;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/11/2
 * RecyclerView.Adapter 实现点击事件 ,添加头部尾部
 *
 * @author ymexc
 */
public abstract class RecyclerViewAdapter<E, VH extends RecyclerViewAdapter.ViewHolder>
        extends RecyclerView.Adapter<VH> implements View.OnClickListener, View.OnLongClickListener {

    private final int TYPE_HEADER = Integer.MIN_VALUE;
    private final int TYPE_FOOTER = Integer.MIN_VALUE + 1;
    private static final int DEFAULT_RES = -0x0001;
    private View mHeaderView;
    private View mFooterView;
    private int mHeaderViewRes = DEFAULT_RES;
    private int mFooterViewRes = DEFAULT_RES;

    private List<E> mData; //数据列表

    public List<E> getData() {
        if (mData == null) {
            mData = new ArrayList<E>();
        }
        return mData;
    }

    public RecyclerViewAdapter() {
        this(null);
    }

    public RecyclerViewAdapter(List<E> dataList) {
        super();
        this.mData = dataList;
    }


    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType, boolean content);

    public abstract void onBindViewHolder(VH holder, int position, int index, int type);


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return (VH) new ViewHolder(getHeaderView(parent));
        } else if (viewType == TYPE_FOOTER) {
            return (VH) new ViewHolder(getFooterView(parent));
        }
        return onCreateViewHolder(parent, viewType, true);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        this.itemClickSupport(holder, position, onItemClickListener != null, onItemLongClickListener != null);
        if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
            return;
        }
        int fixpos = position - (hasHeaderView() ? 1 : 0);
        this.onBindViewHolder(holder, position, fixpos, getItemViewType(position));
    }

    private void itemClickSupport(VH holder, int position, boolean flag, boolean longflag) {
        if (flag || longflag) {
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);
            holder.itemView.setOnLongClickListener(this);
        }
        return;

    }


    public void addHeaderView(View view) {
        this.mHeaderView = view;
        this.notifyDataSetChanged();
    }

    public void addFooterView(View view) {
        this.mFooterView = view;
        this.notifyDataSetChanged();
    }

    public void addHeaderView(@LayoutRes int layoutRes) {
        this.mHeaderViewRes = layoutRes;
        this.notifyDataSetChanged();
    }

    public void addFooterView(@LayoutRes int layoutRes) {
        this.mFooterViewRes = layoutRes;
        this.notifyDataSetChanged();
    }

    public boolean hasHeaderView() {
        return this.mHeaderView != null || this.mHeaderViewRes != DEFAULT_RES;
    }

    public boolean hasFooterView() {
        return this.mFooterView != null || this.mFooterViewRes != DEFAULT_RES;
    }

    public View getHeaderView(ViewGroup parent) {
        return mHeaderView = ViewKit.inflate(parent.getContext(), mHeaderViewRes, parent);
    }

    public View getFooterView(ViewGroup parent) {
        return mFooterView = ViewKit.inflate(parent.getContext(), mFooterViewRes, parent);
    }

    public void removeHeaderView() {
        mHeaderView = null;
        mHeaderViewRes = DEFAULT_RES;
        this.notifyDataSetChanged();
    }

    /**
     * remove footer view
     */
    public void removeFooterView() {
        mFooterView = null;
        mFooterViewRes = DEFAULT_RES;
        this.notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (hasHeaderView() && position == 0) {
            return TYPE_HEADER;
        } else if (hasFooterView() && position == (getItemCount() - 1)) {
            return TYPE_FOOTER;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return getData().size() + (hasHeaderView() ? 1 : 0) + (hasFooterView() ? 1 : 0);
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.OnItemClick(v, (Integer) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (onItemLongClickListener != null) {
            onItemLongClickListener.onItemLongClick(v, (Integer) v.getTag());
        }
        return false;
    }


    /**
     * append entities
     *
     * @param dataList
     */
    public void appendData(List<E> dataList) {
        Optional.checkNull(dataList, "At RecyclerViewAdapter.appendData(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
        this.getData().addAll(dataList);
        this.notifyDataSetChanged();
    }

    /**
     * reset entities
     *
     * @param dataList
     */
    public void resetData(List<E> dataList) {
        Optional.checkNull(dataList, "At RecyclerViewAdapter.appendData(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
        this.getData().clear();
        this.mData.addAll(dataList);
        this.notifyDataSetChanged();
    }

    /**
     * add single data entity
     *
     * @param data
     */
    public void appendData(E data) {
        if (Optional.isNull(data)) {
            Log.e("WARN", "appendData data is null");
        }
        this.getData().add(data);
        this.notifyDataSetChanged();
    }


    public E getItem(int position) {
        if (position >= getItemCount() || position < 0) {
            throw new ArrayIndexOutOfBoundsException(position);//数组越界
        }
        return null != mData ? mData.get(position) : null;
    }

    private OnItemClickListener onItemClickListener;

    private OnItemLongClickListener onItemLongClickListener;

    /**
     * @param onItemClickListener
     * @deprecated
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * @param onItemLongClickListener
     * @deprecated
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(View itemView, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


    /**
     * 当recyclerview布局为GridLayout 或 StaggeredGridLayout时需要调用此方法兼容
     *
     * @param view
     * @param adapter
     */
    public static void compatGridLayoutManager(RecyclerView view, RecyclerViewAdapter adapter) {
        RecyclerView.LayoutManager manager = view.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            ((GridLayoutManager) manager).setSpanSizeLookup(adapter.new GS(((GridLayoutManager) manager).getSpanCount()));
        } else if (manager instanceof StaggeredGridLayoutManager) {
            SGLM sglm = new SGLM(((StaggeredGridLayoutManager) manager).getSpanCount(), ((StaggeredGridLayoutManager) manager).getOrientation());
            sglm.setSpanSizeLookup(adapter.new GS(((StaggeredGridLayoutManager) manager).getSpanCount()));
            view.setLayoutManager(sglm);
        }
    }

    public class GS extends GridLayoutManager.SpanSizeLookup {
        private int spanSize = 1;

        public GS(int span) {
            this.spanSize = span;
        }

        @Override
        public int getSpanSize(int position) {
            if ((position == (getItemCount() - 1) && hasFooterView()) || (position == 0 && hasHeaderView())) {
                return this.spanSize;
            }
            return 1;
        }
    }

    public static class SGLM extends StaggeredGridLayoutManager {

        private final String TAG = getClass().getSimpleName();

        GridLayoutManager.SpanSizeLookup mSpanSizeLookup;

        public SGLM(int spanCount, int orientation) {
            super(spanCount, orientation);
        }

        /**
         * Returns the current used by the GridLayoutManager.
         *
         * @return The current used by the GridLayoutManager.
         */
        public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
            return mSpanSizeLookup;
        }

        /**
         * 设置某个位置的item的跨列程度，这里和GridLayoutManager有点不一样，
         * 如果你设置某个位置的item的span大于1了，那么这个item会占据所有列
         *
         * @param spanSizeLookup instance to be used to query number of spans
         *                       occupied by each item
         */
        public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
            mSpanSizeLookup = spanSizeLookup;
        }

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
            for (int i = 0; i < getItemCount(); i++) {

                if (mSpanSizeLookup.getSpanSize(i) > 1) {
                    //Log.d(TAG, "lookup > 1 = " + i);
                    try {
                        //fix 动态添加时报IndexOutOfBoundsException
                        View view = recycler.getViewForPosition(i);
                        if (view != null) {
                            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                            lp.setFullSpan(true);
                        }
                        // recycler.recycleView(view);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            super.onMeasure(recycler, state, widthSpec, heightSpec);
        }
    }
}

