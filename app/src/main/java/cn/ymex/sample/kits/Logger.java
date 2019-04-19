package cn.ymex.sample.kits;

import android.util.Log;

public class Logger {
    public static  <T> void log(T host,String text) {
        Log.d("----->" + host.getClass().getName(), text);
    }
}
