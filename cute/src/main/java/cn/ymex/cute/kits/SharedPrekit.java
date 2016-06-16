package cn.ymex.cute.kits;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * Created by ymexc on 2016/6/16.
 */
public final class SharedPrekit{

    private static Context mContext;
    private SharedPreferences mSharedPreferences;

    private static boolean sInitialed;
    private static SharedPrekit sharedPrekit;

    private SharedPrekit(){
        String spname = "cute_sp";
        mSharedPreferences = mContext.getSharedPreferences(spname, Context.MODE_PRIVATE);
    }

    public static void init(@NonNull Context context) {
        if (sInitialed||context == null) {
            throw new IllegalArgumentException("context not allow null");
        }
        mContext = context;
        sInitialed = true;
    }

    public static SharedPrekit instance() {
        if (mContext==null){
            throw new IllegalArgumentException("context is null, please init SharedPrekit in application!");
        }
        SharedPrekit sp = sharedPrekit;
        if (sharedPrekit == null) {
            synchronized (SharedPrekit.class) {
                sp = sharedPrekit;
                if (sp == null) {
                    sp = new SharedPrekit();
                    sharedPrekit =sp;
                }
            }
        }
        return sharedPrekit;
    }

    /**
     * Set a String value in the preferences editor, to be written back once
     */
    public void putString(@NonNull String key,@Nullable String value) {
       mSharedPreferences.edit().putString(key,value).commit();
    }

    /**
     * Set a set of String values in the preferences editor
     */

    public void putStringSet(String key, Set<String> values) {
        mSharedPreferences.edit().putStringSet(key,values).commit();
    }

    /**
     * Set an int value in the preferences editor
     */
    public void putInt(@NonNull String key, int value) {
       mSharedPreferences.edit().putInt(key,value).commit();
    }

    /**
     * Set a long value in the preferences editor
     */
    public void putLong(@NonNull String key, long value) {
        mSharedPreferences.edit().putLong(key,value).commit();

    }

    /**
     * Set a float value in the preferences editor
     */
    public void putFloat(String key, float value) {
        mSharedPreferences.edit().putFloat(key,value).commit();
    }

    /**
     * Set a boolean value in the preferences editor
     */

    public void putBoolean(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key,value).commit();
    }

    /**
     * Mark in the editor that a preference value should be removed
     * @param key
     */
    public void remove(String key) {
        mSharedPreferences.edit().remove(key).commit();
    }

    /**
     * Mark in the editor to remove <em>all</em> values from the
     * preferences.
     */

    public void clear() {
        mSharedPreferences.edit().clear().commit();
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
     *
     * @throws NullPointerException
     */
    public Map<String, ?> getAll(){
        return mSharedPreferences.getAll();
    }

    /**
     * Retrieve a String value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a String.
     */
    @Nullable
    String getString(String key, @Nullable String defValue){
        return mSharedPreferences.getString(key,defValue);
    }

    /**
     * Retrieve a set of String values from the preferences.
     *
     * <p>Note that you <em>must not</em> modify the set instance returned
     * by this call.  The consistency of the stored data is not guaranteed
     * if you do, nor is your ability to modify the instance at all.
     *
     * @param key The name of the preference to retrieve.
     * @param defValues Values to return if this preference does not exist.
     *
     * @return Returns the preference values if they exist, or defValues.
     * Throws ClassCastException if there is a preference with this name
     * that is not a Set.
     */
    @Nullable
    Set<String> getStringSet(String key, @Nullable Set<String> defValues){
        return mSharedPreferences.getStringSet(key,defValues);
    }

    /**
     * Retrieve an int value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * an int.
     */
    public int getInt(String key, int defValue){
        return getInt(key, defValue);
    }

    /**
     * Retrieve a long value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a long.
     */
    public long getLong(String key, long defValue){
        return mSharedPreferences.getLong(key,defValue);
    }

    /**
     * Retrieve a float value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a float.
     *
     */
    public float getFloat(String key, float defValue){
        return mSharedPreferences.getFloat(key,defValue);
    }

    /**
     * Retrieve a boolean value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a boolean.
     */
    public boolean getBoolean(String key, boolean defValue){
       return mSharedPreferences.getBoolean(key,defValue);
    }

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param key The name of the preference to check.
     * @return Returns true if the preference exists in the preferences,
     *         otherwise false.
     */
    public boolean contains(String key){
        return mSharedPreferences.contains(key);
    }
}
