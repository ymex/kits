package cn.ymex.cocccute.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.ymex.cocccute.R;
import cn.ymex.cocccute.entity.ItemEntity;
import cn.ymex.cute.kits.Finder;
import cn.ymex.cute.widget.adapter.AdapterViewHolder;
import cn.ymex.cute.widget.adapter.ViewHolderAdapter;

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
 * @date 16/4/24
 * ViewHolderAdapter 用法：举个栗子
 */
public class ItemViewHolderAdapter extends ViewHolderAdapter<ItemEntity,ItemViewHolderAdapter.ViewHoder> {


    public ItemViewHolderAdapter(Context context) {
        super(context);
    }

    public ItemViewHolderAdapter(Context context, List<ItemEntity> dataList) {
        super(context, dataList);
    }

    /**
     * 实例子布局
     *
     * @return
     */
    @Override
    public View inflateItemView() {
        return Finder.inflate(mContext,R.layout.item_main);
    }

    /**
     * viewHolder view hold
     *
     * @param view
     * @return
     */
    @Override
    public ViewHoder instanceViewHolder(View view) {
        return new ViewHoder(view);
    }

    /**
     * 绑定数据 与 baseAdapter getitem 功能相同
     *
     * @param position
     * @param convertView
     * @param parent
     * @param hold        viewHolder 持有itemview实例
     */
    @Override
    public void getItem(int position, View convertView, ViewGroup parent, ViewHoder hold) {
        ItemEntity entity = getItem(position);
        hold.tvTitle.setText(entity.getTitle());
        hold.tvDetail.setText(entity.getDetail());

    }

    public static class ViewHoder extends AdapterViewHolder{

        private TextView tvTitle;
        private TextView tvDetail;

        public ViewHoder(View convertView) {
            super(convertView);
            tvTitle = Finder.find(convertView,R.id.tv_title);
            tvDetail = Finder.find(convertView,R.id.tv_detail);
        }
    }
}
