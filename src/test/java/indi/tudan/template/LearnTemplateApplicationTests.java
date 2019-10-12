package indi.tudan.template;

import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Dict;
import freemarker.template.TemplateException;
import indi.tudan.template.utils.FreeMarkerUtils;
import indi.tudan.template.utils.StrTemplateUtils;
import indi.tudan.template.utils.TemplateConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnTemplateApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testStringTemplate() {

        Console.log(StrTemplateUtils.render("Hello ${name}", Dict.create().set("name", "tudan110")));
    }

    @Test
    public void testFreeMarkerStrTemplate() throws IOException, TemplateException {
        // 测试模板
        String template = "姓名：${name}；年龄：${age}";

        // map，需要动态填充的数据
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", "25");

        // 解析字符串模板的方法，并返回处理后的字符串
        String resultStr = FreeMarkerUtils.process(template, map);
        Console.log(resultStr);
    }

    @Test
    public void testTemplateCOnverter() throws IOException, TemplateException {
        String template = TemplateConverter.aPairOfBraces2Freemarkertemplate("hello, {name}; age, {age}");
        Console.log(template);

        // map，需要动态填充的数据
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", "25");

        Console.log(FreeMarkerUtils.process(template, map));
    }

}
