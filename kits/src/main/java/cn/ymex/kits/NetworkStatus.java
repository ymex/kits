package cn.ymex.kits;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络状态 ， 及其变化事件监听
 * todo:过滤抖动
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
    private List<SoftReference<NetworkStatusListener>> listeners;

    public static void init(Context context) {
        thisInstance = new NetworkStatus(context);
        thisInstance.mIsWifi = checkIsWifi(context);
        thisInstance.startListening(context);
    }

    private NetworkStatus(Context context) {
        mState = checkNetworkConnected(context);
        mHandler = new MessageHandler();
        mReceiver = new ConnectivityBroadcastReceiver();
        listeners = new ArrayList<>();
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
                if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
                    mState = State.CONNECTED;
                    message.what = State.CONNECTED.VALUE;
                } else {
                    mState = State.CONNECTED_BREAK;
                    message.what = State.CONNECTED_BREAK.VALUE;
                }
            } else {
                mState = State.CONNECTED_BREAK;
                message.what = State.CONNECTED_BREAK.VALUE;
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
     * Description:: 检查是否有wifi 联接
     *
     * @param context
     * @return
     * @return: boolean
     */
    public static boolean checkIsWifi(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) (context
                .getSystemService(Context.CONNECTIVITY_SERVICE));
        if (connectivity != null) {
            NetworkInfo networkinfo = connectivity.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isAvailable()) {
                if (networkinfo.getType() == ConnectivityManager.TYPE_WIFI) {
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
                return State.CONNECTED_BREAK;
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
        UNKNOWN(1), CONNECTED(2), CONNECTED_BREAK(3);
        public int VALUE;

        State(int value) {
            this.VALUE = value;
        }
    }


    private void setOnNetworkStatusListener(
            NetworkStatusListener networkStatusListener) {
        if (containsListener(networkStatusListener) >= 0) {
            return;
        }
        this.listeners.add(new SoftReference<>(networkStatusListener));
    }

    private void removeOnNetworkStatusListener(NetworkStatusListener networkStatusListener) {
        int i = containsListener(networkStatusListener);
        if (i >= 0) {
            listeners.remove(i);
        }
    }

    private int containsListener(NetworkStatusListener listener) {

        for (int i = 0; i < listeners.size(); i++) {
            if (listeners.get(i).get() == listener) {
                return i;
            }
        }
        return -1;

    }

    public static void registeStatusListener(
            NetworkStatusListener networkStatusListener) {
        if (thisInstance != null) {
            thisInstance.setOnNetworkStatusListener(networkStatusListener);
        }
    }

    public static void unregisteStatusListener(
            NetworkStatusListener networkStatusListener) {
        if (thisInstance != null) {
            thisInstance.removeOnNetworkStatusListener(networkStatusListener);
        }
    }


    public interface NetworkStatusListener {
        /**
         * Description: 网络断开事件
         *
         * @param networkInfo
         */
        void onNetworkBreak(NetworkInfo networkInfo);

        /**
         * Description: 网络连接事件
         *
         * @param networkInfo
         * @param isWIFI
         */
        void onNetworkConnected(NetworkInfo networkInfo, boolean isWIFI);
    }

    private MessageHandler mHandler;

    private class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;
            if (what == State.CONNECTED_BREAK.VALUE) {
                for (SoftReference<NetworkStatusListener> l : listeners) {
                    if (l.get() != null) {
                        l.get().onNetworkBreak(mNetworkInfo);
                    }
                }
            }
            if (what == State.CONNECTED.VALUE) {
                for (SoftReference<NetworkStatusListener> l : listeners) {
                    if (l.get() != null) {
                        l.get().onNetworkConnected(mNetworkInfo, mIsWifi);
                    }
                }
            }
        }
    }

}
