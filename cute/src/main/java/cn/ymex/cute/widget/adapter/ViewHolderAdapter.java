package cn.ymex.cute.widget.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cn.ymex.cute.log.L;
import cn.ymex.cute.log.Printer;


/**
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * <p/>
 * Email:ymex@foxmail.com  (www.ymex.cn)
 *
 * @author ymex
 * @date 16/4/23
 * 计时器
 */

/**
 * 替代系统的BaseAdapter
 *
 * @param <E>
 * @param <V>
 */
public abstract class ViewHolderAdapter<E extends Object, V extends AdapterViewHolder> extends BaseAdapter {


    protected List<E> mDataList; //数据列表
    protected Context mContext;

    public ViewHolderAdapter(Context context) {
        this(context, null);
    }

    public ViewHolderAdapter(Context context, List<E> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    /**
     * 添加数据列表
     *
     * @param dataList
     */
    public void appendDataList(List<E> dataList) {
        if (null == dataList) {
            L.e(Printer.TAG_E, "At BaseCuteAdapter.appendDataList(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
            return;
        }
        if (null == this.mDataList) {
            this.mDataList = new ArrayList<E>();
        }
        this.mDataList.addAll(dataList);
        this.notifyDataSetChanged();
    }

    /**
     * 重置数据源
     *
     * @param dataList
     */
    public void resetDataList(List<E> dataList) {
        if (null == dataList) {
            L.e(Printer.TAG_E, "At BaseCuteAdapter.resetDataList(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
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
     * 添加单条数据
     *
     * @param data
     */
    public void appendDataItem(E data) {
        if (null == data) {
            L.e(Printer.TAG_E, "At BaseCuteAdapter.appendData(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
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
            convertView = inflateItemView();
            viewHolder = instanceViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (V) convertView.getTag();
        }
        getItem(position, convertView, parent, viewHolder);
        return convertView;
    }


    /**
     * 实例子布局
     *
     * @return
     */
    public abstract View inflateItemView();


    /**
     * viewHolder view hold
     *
     * @return
     */
    public abstract V instanceViewHolder(View view);

    /**
     * 绑定数据 与 baseAdapter getitem 功能相同
     * @param position
     * @param convertView
     * @param parent
     * @param hold viewHolder 持有itemview实例
     */
    public abstract void getItem(int position, View convertView, ViewGroup parent, V hold);


}
