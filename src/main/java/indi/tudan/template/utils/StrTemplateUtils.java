package indi.tudan.template.utils;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;

import java.util.Map;

/**
 * 字符串模板引擎工具类
 *
 * @author wangtan
 * @date 2019-10-12 16:13:33
 * @since 1.0
 */
public class StrTemplateUtils {

    /**
     * 渲染字符串模板
     * <p>
     * 依赖 hutool，自动根据用户引入的模板引擎库的 jar 来选择使用的引擎
     *
     * @param templateStr 模板名称
     * @param data        模板数据
     * @return String 解析后内容
     */
    public static String render(String templateStr, Map<?, ?> data) {

        // TemplateConfig 为模板引擎的选项，可选内容有字符编码、模板路径、模板加载方式等，默认通过模板字符串渲染
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());

        // 假设我们引入的是 FreeMarker 引擎，则：
        Template template = engine.getTemplate(templateStr);

        // Dict本质上为 Map，此处可用 Map
        return template.render(data);
    }

}
