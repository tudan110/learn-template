package indi.tudan.template;

import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Dict;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnTemplateApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testStringTemplate() {

        // 自动根据用户引入的模板引擎库的 jar 来自动选择使用的引擎
        // TemplateConfig 为模板引擎的选项，可选内容有字符编码、模板路径、模板加载方式等，默认通过模板字符串渲染
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());

        // 假设我们引入的是 FreeMarker 引擎，则：
        Template template = engine.getTemplate("Hello ${name}");

        // Dict本质上为 Map，此处可用 Map
        String result = template.render(Dict.create().set("name", "tudan110"));

        // 输出：Hello Hutool
        Console.log(result);
    }

}
