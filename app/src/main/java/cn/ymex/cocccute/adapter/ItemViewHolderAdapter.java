package cn.ymex.cocccute.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.ymex.cocccute.R;
import cn.ymex.cocccute.entity.ItemEntity;
import cn.ymex.cuteact.adapter.ViewHolderAdapter;
import cn.ymex.cuteact.kits.Finder;

public class ItemViewHolderAdapter extends ViewHolderAdapter<ItemEntity,ItemViewHolderAdapter.ViewHoder> {

    @Override
    public View inflateItemView(Context context) {
        return Finder.inflate(context,R.layout.item_main);
    }

    @Override
    public ViewHoder onCreateViewHolder(View view) {
        return new ItemViewHolderAdapter.ViewHoder(view);
    }


    @Override
    public void onBindViewHolder(int position, View convertView, ViewGroup parent, ItemViewHolderAdapter.ViewHoder hold) {
        ItemEntity entity = getItem(position);
        hold.tvTitle.setText(entity.getTitle());
        hold.tvDetail.setText(entity.getDetail());

    }

    public static class ViewHoder extends ViewHolderAdapter.ViewHolder {

        private TextView tvTitle;
        private TextView tvDetail;

        public ViewHoder(View convertView) {
            super(convertView);
            tvTitle = Finder.find(convertView,R.id.tv_title);
            tvDetail = Finder.find(convertView,R.id.tv_detail);
        }
    }
}
