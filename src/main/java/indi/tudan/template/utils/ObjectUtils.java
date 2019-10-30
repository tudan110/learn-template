package indi.tudan.template.utils;

/**
 * 对象工具类
 *
 * @author wangtan
 * @date 2019-10-30 22:51:41
 * @since 1.0
 */
public class ObjectUtils {

    /**
     * 判断对象是否为 Null
     *
     * @param obj 对象
     * @return boolean
     * @date 2019-10-30 22:52:26
     */
    public static boolean isNotNull(Object obj) {
        return null != obj && !obj.equals((Object) null);
    }
}
