package cn.ymex.cute.widget.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.ymex.cute.Cute;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/11/2
 * RecyclerView.Adapter 实现点击事件
 *
 * @author ymexc
 */
public abstract class RecyclerViewAdapter<E extends Object, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements View.OnClickListener, View.OnLongClickListener {

    protected List<E> mData; //数据列表

    public RecyclerViewAdapter() {
        this(null);
    }

    public RecyclerViewAdapter(List<E> dataList) {
        super();
        this.mData = dataList;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        itemClickSupport(holder, position, onItemClickListener != null , onItemLongClickListener != null);
    }

    private void itemClickSupport(VH holder, int position, boolean flag,boolean longflag) {
        if (flag || longflag) {
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);
            holder.itemView.setOnLongClickListener(this);
        }
        return;

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

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    /**
     * append entities
     *
     * @param dataList
     */
    public void appendData(List<E> dataList) {
        if (null == dataList) {
            Log.e(Cute.TAG_E, "At BaseCuteAdapter.appendDataList(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
            return;
        }
        if (null == this.mData) {
            this.mData = new ArrayList<E>();
        }
        this.mData.addAll(dataList);
        this.notifyDataSetChanged();
    }

    /**
     * reset entities
     *
     * @param dataList
     */
    public void resetData(List<E> dataList) {
        if (null == dataList) {
            Log.e(Cute.TAG_E, "At BaseCuteAdapter.resetDataList(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
            return;
        }
        if (null == mData) {
            this.mData = new ArrayList<E>();
        } else {
            this.mData.clear();
        }
        this.mData.addAll(dataList);
        this.notifyDataSetChanged();
    }

    /**
     * add single data entity
     *
     * @param data
     */
    public void appendData(E data) {
        if (null == data) {
            Log.e(Cute.TAG_E, "At BaseCuteAdapter.appendData(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
            return;
        }
        if (null == this.mData) {
            this.mData = new ArrayList<E>();
        }
        this.mData.add(data);
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
}
