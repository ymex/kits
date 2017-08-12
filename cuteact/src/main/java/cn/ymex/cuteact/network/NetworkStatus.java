/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * <p>
 * Email:ymex@foxmail.com  (www.ymex.cn)
 * @author ymex
 * date: 16/4/21
 */
package cn.ymex.cuteact.network;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

/**
 * 网络状态 ， 及其变化事件监听
 */
@SuppressLint("HandlerLeak")
public class NetworkStatus {

	private State mState;
	private static NetworkStatus thisInstance;
	private Context mContext;
	private NetworkInfo mNetworkInfo;
	private NetworkInfo mOtherNetworkInfo;
	private boolean mListening;
	private String mReason;
	private boolean mIsFailOver;
	private boolean mIsWifi;
	private ConnectivityBroadcastReceiver mReceiver;

	public static void init(Context context) {
		thisInstance = new NetworkStatus(context);
		thisInstance.mIsWifi = checkIsWifi(context);
		thisInstance.startListening(context);
	}

	private NetworkStatus(Context context) {
		mState = checkNetworkConnected(context);
		mHandler = new MessageHandler();
		mReceiver = new ConnectivityBroadcastReceiver();
	}

	public static NetworkStatus getInstance() {
		return thisInstance;
	}

	private class ConnectivityBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			Message message = mHandler.obtainMessage();
			String action = intent.getAction();
			ConnectivityManager connectivity = (ConnectivityManager) (context.getSystemService(Context.CONNECTIVITY_SERVICE));

			if (!action.equals(ConnectivityManager.CONNECTIVITY_ACTION) || mListening == false) {
				return;
			}

			boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

			if (connectivity != null) {
				mNetworkInfo = connectivity.getActiveNetworkInfo();
				if (mNetworkInfo!=null && mNetworkInfo.isConnected()){
					mState = State.CONNECTED;
					message.what = State.CONNECTED.VALUE;
				}else {
					mState = State.NOT_CONNECTED;
					message.what = State.NOT_CONNECTED.VALUE;
				}
			}else {
				mState = State.NOT_CONNECTED;
				message.what = State.NOT_CONNECTED.VALUE;
			}
			mOtherNetworkInfo = (NetworkInfo) intent
					.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

			mReason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
			mIsFailOver = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);

			mIsWifi = checkIsWifi(mContext);
			message.sendToTarget();
		}
	}

	public synchronized void startListening(Context context) {
		if (!mListening) {
			mContext = context;

			IntentFilter filter = new IntentFilter();
			filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			context.registerReceiver(mReceiver, filter);
			mListening = true;
		}
	}

	/**
	 * This method stops this class from listening for network changes.
	 */
	public synchronized void stopListening() {
		if (mListening) {
			mContext.unregisterReceiver(mReceiver);
			mContext = null;
			mNetworkInfo = null;
			mOtherNetworkInfo = null;
			mIsFailOver = false;
			mReason = null;
			mListening = false;
		}
	}

	/**
	 * 
	 * Description:: 检查是否有wifi 联接
	 * @param context
	 * @return
	 * @return: boolean
	 */
	public static boolean checkIsWifi(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) (context
				.getSystemService(Context.CONNECTIVITY_SERVICE));
		if (connectivity != null) {
			NetworkInfo networkinfo = connectivity.getActiveNetworkInfo();
			if (networkinfo!=null&&networkinfo.isAvailable()){
				if (networkinfo.getType() == ConnectivityManager.TYPE_WIFI){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检测是否有网络联接
	 * 
	 * @param context
	 * @return
	 */
	public State checkNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return State.CONNECTED;
			} else {
				return State.NOT_CONNECTED;
			}
		}
		return State.UNKNOWN;
	}

	public NetworkInfo getNetworkInfo() {
		return mNetworkInfo;
	}

	public NetworkInfo getOtherNetworkInfo() {
		return mOtherNetworkInfo;
	}

	public boolean isFailover() {
		return mIsFailOver;
	}

	public String getReason() {
		return mReason;
	}

	public boolean isWifi() {
		return mIsWifi;
	}

	public State getState() {
		return mState;
	}

	public enum State {
		UNKNOWN(1), CONNECTED(2), NOT_CONNECTED(3);
		public int VALUE;

		private State(int value) {
			this.VALUE = value;
		}
	}

	private NetworkStatusListener networkStatusListener;

	private void setOnNetworkStatusListener(
			NetworkStatusListener networkStatusListener) {
		this.networkStatusListener = networkStatusListener;
	}

	public static void setNetworkStatusListener(
			NetworkStatusListener networkStatusListener) {
		thisInstance.setOnNetworkStatusListener(networkStatusListener);
	}

	public interface NetworkStatusListener {
		/**
		 * @param networkInfo 
		 * Description: 网络断开事件
		 */
		public void onStateNotConnected(NetworkInfo networkInfo);
		/**
		 * 
		 * @param networkInfo
		 * @param isWIFI
		 * Description: 网络连接事件
		 */
		public void onStateConnected(NetworkInfo networkInfo, boolean isWIFI);
	}

	private MessageHandler mHandler;

	private class MessageHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			int what = msg.what;
			if (what == State.NOT_CONNECTED.VALUE) {
				if (networkStatusListener != null) {
					networkStatusListener.onStateNotConnected(mNetworkInfo);
				}
			}
			if (what == State.CONNECTED.VALUE) {
				if (networkStatusListener != null) {
					networkStatusListener.onStateConnected(mNetworkInfo, mIsWifi);
				}
			}
		}
	}

}
