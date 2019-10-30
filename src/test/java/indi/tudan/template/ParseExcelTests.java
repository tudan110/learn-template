package indi.tudan.template;

import indi.tudan.template.service.GenerateEventDataFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParseExcelTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void test() throws FileNotFoundException {

        String excelPath = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/测试文件.xlsx").getAbsolutePath();

        GenerateEventDataFile.builder()
                .excelPath(excelPath)
                .sheetIndex(0)
                .outPath("C:/Users/tudan/Desktop/天津/测试文件")
                .build()
                .parse();
    }

}
