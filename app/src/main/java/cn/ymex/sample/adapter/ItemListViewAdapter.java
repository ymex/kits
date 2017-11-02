package cn.ymex.sample.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.ymex.adapter.pure.ListViewAdapter;
import cn.ymex.sample.R;
import cn.ymex.sample.entity.ItemEntity;
import cn.ymex.kits.Finder;
//import cn.ymex.pure.adapter.ListViewAdapter;

public class ItemListViewAdapter extends ListViewAdapter<ItemEntity,ItemListViewAdapter.ViewHoder> {


    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int position) {
        return new ItemListViewAdapter.ViewHoder(Finder.inflate(parent.getContext(),R.layout.item_main));
    }

    @Override
    public void onBindViewHolder(int position, View convertView, ViewGroup parent, ItemListViewAdapter.ViewHoder hold) {
        ItemEntity entity = getItem(position);
        hold.tvTitle.setText(entity.getTitle());
        hold.tvDetail.setText(entity.getDetail());

    }

    public static class ViewHoder extends ListViewAdapter.ViewHolder {

        private TextView tvTitle;
        private TextView tvDetail;

        public ViewHoder(View convertView) {
            super(convertView);
            tvTitle = Finder.find(convertView,R.id.tv_title);
            tvDetail = Finder.find(convertView,R.id.tv_detail);
        }
    }
}
