package indi.tudan.template.utils;

import cn.hutool.core.util.ReUtil;

/**
 * 模板内容转换器
 *
 * @author wangtan
 * @date 2019-10-12 16:30:05
 * @since 1.0
 */
public class TemplateConverter {

    /**
     * FreeMarker 字符串替换模板
     */
    private static final String Free_Marker_REPLACEMENT_TEMPLATE = "${$1}";

    /**
     * 匹配一对大括号的正则表达式
     */
    private static final String A_PAIR_OF_BRACES = "\\{([^}]*)}";

    /**
     * 单对大括号转 FreeMarker 模板
     * <p>
     * hello, {name} => hello, ${name}
     *
     * @param content 内容，例如：hello, {name}
     * @return String
     * @date 2019-10-12 16:33:21
     */
    public static String aPairOfBraces2Freemarkertemplate(String content) {
        return toFreeMarkerTemplate(content, A_PAIR_OF_BRACES);
    }

    /**
     * 字符串转 FreeMarker 模板
     *
     * @param content 内容
     * @param regex   正则表达式
     * @return String
     * @date 2019-10-12 17:31:22
     */
    public static String toFreeMarkerTemplate(String content, String regex) {
        return ReUtil.replaceAll(content, regex, Free_Marker_REPLACEMENT_TEMPLATE);
    }

}
