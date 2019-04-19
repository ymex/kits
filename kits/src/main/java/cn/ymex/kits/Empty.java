package cn.ymex.kits;

import java.util.Collection;
/**
 * Email:ymex@foxmail.com  (www.ymex.cn)
 * @author ymex
 *
 * 空类型相关
 * 判空处理
 */
public class Empty {
    /**
     * 字符串为null或空字符串，返回true
     *
     * @param text 字串
     * @return boolean
     */
    public static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0;
    }

    /**
     * 集合为null或长度==0，返回true
     *
     * @param collection 集合
     * @return boolean
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * 集合不为null且长度大于0，返回true
     *
     * @param collection 字串
     * @return boolean
     */
    public static boolean isNotEmpty(Collection collection) {
        return collection != null && collection.size() > 0;
    }

    /**
     * 检查对象是否为空，为空抛出 NullPointerException
     *
     * @param notice 提示信息
     * @param t      检查对象
     * @param <T>    类型
     * @return 返回检查对象
     */
    public static <T> T checkNull(T t, String notice) {
        if (null == t) {
            if (notice == null) {
                throw new NullPointerException();
            }
            throw new NullPointerException(notice);
        }
        return t;
    }

    /**
     * 检查对象是否为空，为空抛出 NullPointerException
     *
     * @param t   检查对象
     * @param <T> 类型
     * @return 返回检查对象
     */
    public static <T> T checkNull(T t) {
        return checkNull(t, null);
    }

    /**
     * 判断对象是否为空
     *
     * @param t   检查对象
     * @param <T> 类型
     * @return boolean
     */
    public static <T> boolean isNull(T t) {
        return null == t;
    }

    /**
     * 判断对象是否为空
     *
     * @param t   检查对象
     * @param <T> 类型
     * @return boolean
     */
    public static <T> boolean isNotNull(T t) {
        return null != t;
    }

    /**
     * 检查对象是否为空， 不为空将返回原值 、为空则抛出异常
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T get(final T value) {
        return checkNull(value, null);
    }

    /**
     * isNull(value) ? defValue : value;
     * 对象是否为空， 返回默认值。不为空将返回原值。
     *
     * @param value    value
     * @param defValue default Value
     * @param <T>      type
     * @return type obj
     */
    public static <T> T or(final T value, T defValue) {
        return isNull(value) ? defValue : value;
    }

}
