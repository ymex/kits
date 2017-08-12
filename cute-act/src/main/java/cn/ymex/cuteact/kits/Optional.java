package cn.ymex.cuteact.kits;

/**
 * Optional null 处理
 * Created by ymexc on 2016/8/12.
 */
public class Optional {

    /**
     * 检查对象是否为空，为空抛出 NullPointerException
     *
     * @param notice 提示信息
     * @param t
     * @param <T>
     * @return
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
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T checkNull(T t) {
        return checkNull(t, null);
    }

    /**
     * 判断对象是否为空
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isNull(T t) {
        if (null == t) {
            return true;
        }
        return false;
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
     * 对象是否为空， 返回默认值。不为空将返回原值。
     *
     * @param value
     * @param defValue
     * @param <T>
     * @return
     */
    public static <T> T or(final T value, T defValue) {
        if (isNull(value)) {
            return defValue;
        }
        return value;
    }
}
