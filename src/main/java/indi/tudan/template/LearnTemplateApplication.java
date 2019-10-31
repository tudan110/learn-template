package indi.tudan.template;

import indi.tudan.template.service.GenerateEventDataFile;
import indi.tudan.template.utils.ClassUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearnTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnTemplateApplication.class, args);

        //String outPath = System.getProperty("user.dir");
        String outPath = ClassUtils.getCurrentProgramPath(LearnTemplateApplication.class);
        String excelPath = outPath + "/测试文件.xlsx";
        GenerateEventDataFile.builder()
                .excelPath(excelPath)
                .sheetIndex(0)
                .outPath(outPath)
                .build()
                .parse();
    }

}
