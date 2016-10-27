/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * <p>
 * Email:ymex@foxmail.com  (www.ymex.cn)
 *
 * @author ymex
 */
package cn.ymex.cute.adapter;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.ymex.cute.Cute;

/**
 * 替代系统的BaseAdapter
 *
 * @param <E>
 * @param <V>
 */
public abstract class ViewHolderAdapter<E extends Object, V extends AdapterViewHolder> extends BaseAdapter {


    protected List<E> mDataList; //数据列表

    public ViewHolderAdapter() {
        this(null);
    }

    public ViewHolderAdapter(List<E> dataList) {
        super();
        this.mDataList = dataList;
    }

    /**
     * inflate Item View layout
     * @param context
     * @return
     */
    public abstract View inflateItemView(Context context);


    /**
     * viewHolder
     * @param view item view
     * @return
     */
    public abstract V createViewHolder(View view);

    /**
     * like baseAdapter.getView()
     * @param position
     * @param convertView
     * @param parent
     * @param hold
     */
    public abstract void getItemView(int position, View convertView, ViewGroup parent, V hold);



    /**
     * append entities
     *
     * @param dataList
     */
    public void appendDataList(List<E> dataList) {
        if (null == dataList) {
            Log.e(Cute.TAG_E, "At BaseCuteAdapter.appendDataList(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
            return;
        }
        if (null == this.mDataList) {
            this.mDataList = new ArrayList<E>();
        }
        this.mDataList.addAll(dataList);
        this.notifyDataSetChanged();
    }

    /**
     * reset entities
     *
     * @param dataList
     */
    public void resetDataList(List<E> dataList) {
        if (null == dataList) {
            Log.e(Cute.TAG_E, "At BaseCuteAdapter.resetDataList(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
            return;
        }
        if (null == mDataList) {
            this.mDataList = new ArrayList<E>();
        } else {
            this.mDataList.clear();
        }
        this.mDataList.addAll(dataList);
        this.notifyDataSetChanged();
    }

    /**
     * add single data entity
     *
     * @param data
     */
    public void appendDataItem(E data) {
        if (null == data) {
            Log.e(Cute.TAG_E, "At BaseCuteAdapter.appendData(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
            return;
        }
        if (null == this.mDataList) {
            this.mDataList = new ArrayList<E>();
        }
        this.mDataList.add(data);
        this.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return null == mDataList ? 0 : mDataList.size();
    }


    @Override
    public E getItem(int position) {
        if (position > getCount() || position < 0) {
            throw new ArrayIndexOutOfBoundsException(position);//数组越界
        }
        return null != mDataList ? mDataList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V viewHolder = null;
        if (convertView == null) {
            convertView = inflateItemView(parent.getContext());
            viewHolder = createViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (V) convertView.getTag();
        }
        getItemView(position, convertView, parent, viewHolder);
        return convertView;
    }
}
