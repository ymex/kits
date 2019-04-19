package cn.ymex.sample.base;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * <p>
 * Email:ymex@foxmail.com  (www.ymex.cn)
 *
 * @author ymex
 *         date: 16/4/23
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener, NetworkStatus.NetworkStatusListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onNetworkBreak(NetworkInfo networkInfo) {
    }

    @Override
    public void onNetworkConnected(NetworkInfo networkInfo, boolean isWIFI) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkStatus.registeStatusListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NetworkStatus.unregisteStatusListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
