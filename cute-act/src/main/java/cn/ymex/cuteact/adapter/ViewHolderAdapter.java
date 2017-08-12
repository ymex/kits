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
package cn.ymex.cuteact.adapter;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.ymex.cuteact.Cuteact;
import cn.ymex.cuteact.kits.Optional;

/**
 * 替代系统的BaseAdapter
 *
 * @param <E>
 * @param <V>
 */
public abstract class ViewHolderAdapter<E extends Object, V extends ViewHolderAdapter.ViewHolder> extends BaseAdapter {


    protected List<E> mData; //数据列表

    public ViewHolderAdapter() {
        this(null);
    }

    public ViewHolderAdapter(List<E> dataList) {
        super();
        this.mData = dataList;
    }

    public List<E> getData() {
        if (mData == null) {
            mData = new ArrayList<E>();
        }
        return mData;
    }

    /**
     * inflate Item View layout
     *
     * @param context
     * @return
     */
    public abstract View inflateItemView(Context context);


    /**
     * viewHolder
     *
     * @param view item view
     * @return
     */
    public abstract V onCreateViewHolder(View view);

    /**
     * like baseAdapter.getView()
     *
     * @param position
     * @param convertView
     * @param parent
     * @param hold
     */
    public abstract void onBindViewHolder(int position, View convertView, ViewGroup parent, V hold);


    /**
     * append entities
     *
     * @param data
     */
    public void appendData(List<E> data) {
        if (Optional.isNull(data)) {
            Log.e(Cuteact.TAG_E, "At BaseCuteAdapter.appendData(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
            return;
        }
        this.getData().addAll(data);
        this.notifyDataSetChanged();
    }

    /**
     * reset entities
     *
     * @param data
     */
    public void resetData(List<E> data) {
        if (Optional.isNull(data)) {
            Log.e(Cuteact.TAG_E, "At BaseCuteAdapter.resetData(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
            return;
        }

        this.getData().clear();
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }

    /**
     * add single data entity
     *
     * @param data
     */
    public void appendData(E data) {
        if (Optional.isNull(data)) {
            Log.e(Cuteact.TAG_E, "At BaseCuteAdapter.appendData(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
            return;
        }
        this.getData().add(data);
        this.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return getData().size();
    }


    @Override
    public E getItem(int position) {
        if (position > getCount() || position < 0) {
            throw new ArrayIndexOutOfBoundsException(position);//数组越界
        }
        return getData().get(position);
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
            viewHolder = onCreateViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (V) convertView.getTag();
        }
        onBindViewHolder(position, convertView, parent, viewHolder);
        return convertView;
    }

    /**
     * 布局缓存类
     */
    public abstract static class ViewHolder {
        public ViewHolder(View view) {
        }
    }
}
