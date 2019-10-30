package indi.tudan.template.utils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * 字符串模板
 *
 * @author wangtan
 * @date 2019-10-12 15:24:32
 * @since 1.0
 */
public class FreeMarkerUtils {

    private static final String TEMPLATE_NAME = "default-template-name";

    /**
     * @param template 字符串模板
     * @param model    数据
     * @return String 解析后内容
     * @throws IOException
     * @throws TemplateException
     * @date 2019-10-12 16:04:56
     */
    public static String process(String template, Map<String, ?> model)
            throws IOException, TemplateException {
        return process(template, model, null);
    }

    /**
     * 解析字符串模板,通用方法
     *
     * @param template      字符串模板
     * @param model         数据
     * @param configuration 配置
     * @return String 解析后内容
     * @date 2019-10-12 16:05:23
     */
    public static String process(String template, Map<String, ?> model, Configuration configuration)
            throws IOException, TemplateException {
        if (template == null) {
            return "";
        }
        if (configuration == null) {
            configuration = getConfiguration();
        }
        StringWriter stringWriter = new StringWriter();
        new Template(TEMPLATE_NAME, new StringReader(template), configuration).process(model, stringWriter);
        return stringWriter.toString();
    }

    /**
     * 配置 freemarker configuration
     *
     * @return Configuration
     * @date 2019-10-12 15:56:02
     */
    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(templateLoader);
        configuration.setDefaultEncoding("UTF-8");
        return configuration;
    }

}
