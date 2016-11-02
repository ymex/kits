package cn.ymex.cocccute.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.ymex.cocccute.R;
import cn.ymex.cocccute.entity.ItemEntity;
import cn.ymex.cute.widget.adapter.ViewHolderAdapter;
import cn.ymex.cute.kits.ViewKit;

public class ItemViewHolderAdapter extends ViewHolderAdapter<ItemEntity,ItemViewHolderAdapter.ViewHoder> {

    @Override
    public View inflateItemView(Context context) {
        return ViewKit.inflate(context,R.layout.item_main);
    }

    @Override
    public ViewHoder createViewHolder(View view) {
        return new ItemViewHolderAdapter.ViewHoder(view);
    }


    @Override
    public void getItemView(int position, View convertView, ViewGroup parent, ItemViewHolderAdapter.ViewHoder hold) {
        ItemEntity entity = getItem(position);
        hold.tvTitle.setText(entity.getTitle());
        hold.tvDetail.setText(entity.getDetail());

    }

    public static class ViewHoder extends ViewHolderAdapter.ViewHolder {

        private TextView tvTitle;
        private TextView tvDetail;

        public ViewHoder(View convertView) {
            super(convertView);
            tvTitle = ViewKit.find(convertView,R.id.tv_title);
            tvDetail = ViewKit.find(convertView,R.id.tv_detail);
        }
    }
}
