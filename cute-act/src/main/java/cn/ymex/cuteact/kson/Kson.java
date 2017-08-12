package cn.ymex.cuteact.kson;

import android.support.v4.util.ArrayMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ymex@foxmail.com
 * 2017/6/28.
 */

public final class Kson {

    private String json;

    private ArrayMap<String, Object> arrayMap;
    private List<String> keys;

    private Kson() {
    }

    private Kson(String json) {
        this.json = json;
        this.arrayMap = new ArrayMap<>();
        this.keys = new ArrayList<>();
    }

    /**
     * reset json resource
     * @param json
     * @return
     */
    public Kson  json(final String json) {
         this.json = json;
        return this;
    }

    /**
     * find the value of the existing keys
     * @return
     */
    public  Kson refind() {
        return this.find((String[]) keys.toArray(new String[keys.size()]));
    }


    /**
     * Kson instance
     * @param json
     * @return
     */
    public static Kson stream(final String json) {
        if (null == json || json.length() <= 0) {
            throw new RuntimeException("kson stream value not allow null or empty!");
        }
        return new Kson(json);
    }

    /**
     * find the value of keys
     * not allow null or empty
     * @param keys
     * @return
     */
    public Kson find(String... keys) {

        if (keys == null || keys.length <= 0) {
            throw new RuntimeException("kson find() value not allow null or empty!");
        }
        clear();
        this.keys.addAll(Arrays.asList(keys));
        for (String key : keys) {
            if (null == key || key.length() <= 0) {
                continue;
            }
            parseLink(key);
        }
        return this;
    }

    private void clear() {
        this.keys.clear();
        this.arrayMap.clear();
    }

    private Kson parseLink(String key) {
        String aliakey = aliasKey(key);
        String realkey = realKey(key);
        String[] subkeys = realkey.split("->");
        Object obresult = null;
        try {
            if (subkeys.length == 1) {//单记录查询
                obresult = parse(json, realkey);
            } else {
                for (String subkey : subkeys) {//data->income[0][22][0]
                    if (obresult == null) {
                        obresult = parse(json, subkey);
                    } else {
                        obresult = parse(obresult.toString(), subkey);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            obresult = null;
        }
        if (realkey.equals(aliakey)) {
            arrayMap.put(realkey, obresult);
        } else {
            arrayMap.put(aliakey, obresult);
            arrayMap.put(realkey, obresult);
        }
        return this;
    }

    private Object parse(String json, String key) throws JSONException {
        Object obResult = null;
        List<Integer> indexes = getMutArrayIndexs(key);
        if (isArrayKey(indexes.size(), key)) {
            String arrayName = getMutArrayName(key);
            JSONArray jsonArray;
            if (arrayName.equals("")) {
                jsonArray = new JSONArray(json);
            } else {
                jsonArray = new JSONObject(json).getJSONArray(arrayName);
            }
            for (int index = 0; index < indexes.size(); index++) {
                if (index == indexes.size() - 1) {
                    obResult = jsonArray.opt(indexes.get(index));
                } else {
                    jsonArray = jsonArray.getJSONArray(indexes.get(index));
                }
            }
            return obResult;
        }
        return new JSONObject(json).opt(key);
    }

    /**
     * 判断key 是否是数组key
     * @param indexs
     * @param key
     * @return
     */
    private boolean isArrayKey(int indexs, String key) {
        return indexs >= 1 && key.endsWith("]");
    }

    /**
     * 获取数组名
     * @param key
     * @return
     */
    private String getMutArrayName(String key) {
        return key.substring(0, key.indexOf("["));
    }

    /**
     * 获取[] 中的数字
     * @param managers
     * @return
     */
    private List<Integer> getMutArrayIndexs(String managers) {
        List<Integer> ls = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?<=\\[)(.+?)(?=\\])");
        Matcher matcher = pattern.matcher(managers);
        while (matcher.find()) {
            ls.add(Integer.valueOf(matcher.group()));
        }
        return ls;
    }


    private String realKey(String key) {
        int index = key.indexOf(":");
        if (index <= 0 || index == key.length() - 1) {
            return key;
        }
        return key.substring(index + 1, key.length());
    }

    private String aliasKey(String key) {
        int index = key.indexOf(":");
        if (index <= 0 || index == key.length() - 1) {
            return key;
        }
        return key.substring(0, index);

    }
    /**
     * get the first value in the array of keys
     * @return
     */
    public KsonHelper get() {
        return getfirst();
    }

    /**
     * get the first value in the array of keys
     * @return
     */
    public KsonHelper getfirst() {
        checkKeys();
        return get(realKey(keys.get(0)));
    }

    /**
     * get the last value in the array of keys
     * @return
     */
    public KsonHelper getlast() {
        checkKeys();
        return get(realKey(keys.get(keys.size() - 1)));
    }

    /**
     * get the value of key
     * @param key
     * @return
     */
    public KsonHelper get(String key) {
        checkKeys();
        return new KsonHelper(arrayMap.get(key));
    }

    /**
     * get the value of key by array index
     * @param index
     * @return
     */
    public KsonHelper get(int index) {
        checkKeys();
        if (index < 0 || index >= keys.size()) {
            throw new RuntimeException("kson get() index value is out of array length!");
        }
        return get(keys.get(index));
    }

    /**
     * get all values
     * @return
     */
    public ArrayMap<String, Object> getall() {
        ArrayMap<String, Object> map = new ArrayMap<>(this.arrayMap.size());
        for (String key : this.arrayMap.keySet()) {
            map.put(key, this.arrayMap.get(key));
        }
        return map;
    }

    private void checkKeys() {
        if (keys == null || keys.size() <= 0) {
            throw new RuntimeException("please call kson find(...) func first before call get()!");
        }
    }
}


