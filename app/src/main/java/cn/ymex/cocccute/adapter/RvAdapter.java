package cn.ymex.cocccute.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ymex.cocccute.R;
import cn.ymex.cocccute.entity.ItemEntity;
import cn.ymex.cute.kits.ViewKit;
import cn.ymex.cute.widget.adapter.RecyclerViewAdapter;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/11/3
 *
 * @author ymexc
 */
public class RvAdapter extends RecyclerViewAdapter<ItemEntity,RvAdapter.ViewHolder> {


    public RvAdapter() {
        List<ItemEntity> mData = new ArrayList<ItemEntity>() {
            {
                for (int i = 0; i < 20; i++) {
                    ItemEntity entity = new ItemEntity();
                    entity.setTitle("Title: " + i);
                    entity.setDetail("Detail " + i);
                    add(entity);
                }
            }
        };
        appendData(mData);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean content) {
        return new ViewHolder(ViewKit.inflate(parent.getContext(),R.layout.rv_item_demo,parent));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, int idex, int type) {
        ItemEntity entity = getItem(idex);
        holder.tvTitle.setText(entity.getTitle());
        holder.tvDetail.setText(entity.getDetail());
    }



    public class ViewHolder extends RecyclerViewAdapter.ViewHolder {

        public TextView tvTitle;
        public TextView tvDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = ViewKit.find(itemView, R.id.tv_title);
            tvDetail = ViewKit.find(itemView, R.id.tv_detail);
        }
    }
}