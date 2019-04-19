package cn.ymex.kits;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Map;
import java.util.Set;

/**
 * SharedPreferences 帮助类
 * Created by ymexc on 2016/6/16.
 */
public final class Storage {

    private static Context mContext;
    private static volatile Storage instance;

    private SharedPreferences mPreferences;
    private static boolean sInitialed;

    private Storage() {
        mPreferences = mContext.getSharedPreferences("KITS_SP_STORAGE", Context.MODE_PRIVATE);
    }

    public static void init(@NonNull Context context) {
        if (sInitialed || context == null) {
            throw new IllegalArgumentException("context not allow null");
        }
        mContext = context;
        sInitialed = true;
    }

    public static Storage instance() {
        if (mContext == null) {
            throw new IllegalArgumentException("context is null, please init Storage in application!");
        }
        Storage sp = instance;
        if (sp == null) {
            synchronized (Storage.class) {
                sp = instance;
                if (sp == null) {
                    sp = new Storage();
                    instance = sp;
                }
            }
        }
        return sp;
    }

    public SharedPreferences preferences() {
        return mPreferences;
    }

    public <T> void put(@NonNull String key, @NonNull T value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);
        } else {
            editor.putString(key, "");
        }
        editor.apply();
    }

    public <E> E get(String key) {
        return get(key, null);
    }

    public <E> E get(String key, E def) {
        Map map = mPreferences.getAll();
        if (map.containsKey(key)) {
            return (E) map.get(key);
        }
        return def;
    }

    /**
     * Mark in the editor that a preference value should be removed
     *
     * @param key
     */
    public void remove(String key) {
        mPreferences.edit().remove(key).apply();
    }

    /**
     * Mark in the editor to remove <em>all</em> values from the
     * preferences.
     */

    public void clear() {
        mPreferences.edit().clear().apply();
    }


    /**
     * Retrieve all values from the preferences.
     *
     * <p>Note that you <em>must not</em> modify the collection returned
     * by this method, or alter any of its contents.  The consistency of your
     * stored data is not guaranteed if you do.
     *
     * @return Returns a map containing a list of pairs key/value representing
     * the preferences.
     * @throws NullPointerException
     */
    public Map<String, ?> getAll() {
        return mPreferences.getAll();
    }

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param key The name of the preference to check.
     * @return Returns true if the preference exists in the preferences,
     * otherwise false.
     */
    public boolean contains(String key) {
        return mPreferences.contains(key);
    }
}
