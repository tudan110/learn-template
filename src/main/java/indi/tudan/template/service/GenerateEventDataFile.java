package indi.tudan.template.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONObject;
import indi.tudan.template.utils.StringUtils;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * 解析 Excel 信息点及维度信息，并生成信息点文件
 *
 * @author wangtan
 * @date 2019-10-30 15:34:41
 * @since 1.0
 */
@Data
@Slf4j
@Builder
public class GenerateEventDataFile {

    /**
     * Excel 文件路径
     */
    private String excelPath;

    /**
     * 工作簿序号
     */
    private int sheetIndex;

    /**
     * 输出文件路径
     */
    private String outPath;

    /**
     * 读取 Excel
     *
     * @return 处理后的数据
     * @date 2019-10-30 15:56:39
     */
    public JSONObject read() {

        // 所有表结构对象
        JSONObject result = new JSONObject(true);

        // 读取 Excel
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(excelPath), sheetIndex);
        List<List<Object>> readAll = reader.read();

        // 能力中心名称
        String deptName = "";

        // 当前正在处理的信息点编码
        String eventCode = "";

        // 开始遍历数据
        for (List<Object> row : readAll) {
//            Console.log(row);

            if (ObjectUtil.isEmpty(row)) {
                continue;
            }

            // 如果是第一行
            if (isDeptRow(row)) {
                deptName = StringUtils.getStr(row.get(0));
            }

            // 如果是事件第一行
            if (isEventFirstRow(row)) {
                eventCode = StringUtils.getStr(row.get(3));
                result.put(eventCode, new JSONObject(true)
                        .fluentPut("deptName", deptName)
                        .fluentPut("eventName", StringUtils.getStr(row.get(1)))
                        .fluentPut("freq", StringUtils.getStr(row.get(2)))
                        .fluentPut("eventCode", eventCode)
                        .fluentPut("fields", new JSONObject()
                                .fluentPut(StringUtils.getStr(row.get(5)), StringUtils.getStr(row.get(6)))));
//                Console.log(eventCode);
            } else {
                result.getJSONObject(eventCode).getJSONObject("fields")
                        .put(StringUtils.getStr(row.get(5)), StringUtils.getStr(row.get(6)));
            }
        }

        // 关闭文件
        reader.close();

        return result;
    }

    /**
     * 解析数据并写入文件
     *
     * @date 2019-10-30 15:57:20
     */
    public void parse() {

        JSONObject data = read();

        for (Object value : data.values()) {

            JSONObject json = (JSONObject) value;
            String fileName = getFileName(json.getString("eventCode"), getFreq(json.getString("freq")));
            FileWriter writer = new FileWriter(StrUtil.format("{}/{}/{}/{}",
                    outPath,
                    json.getString("deptName"),
                    json.getString("eventName"),
                    fileName));
            writer.write(json.getJSONObject("fields").toString());
        }
    }

    /**
     * 获取文件名
     *
     * @param eventCode 信息点编码
     * @param freq      频度编码
     * @return 文件名
     * @date 2019-10-30 16:02:26
     */
    private String getFileName(String eventCode, String freq) {
        return StrUtil.format("BOSSNM_1_{}_{}_{}_000_0000{}_000_0000.json",
                eventCode,
                freq,
                DateUtil.format(new Date(), "yyyyMMdd"),
                getSeqNum(freq));
    }

    /**
     * 获取序列号
     *
     * @param freq 频度编码
     * @return 序列号
     * @date 2019-10-30 16:22:13
     */
    private String getSeqNum(String freq) {
        long betweenMin = DateUtil.between(DateUtil.beginOfDay(new Date()), new Date(), DateUnit.MINUTE);
        if ("H1".equals(freq)) {
            return leftPad(StringUtils.getStr(betweenMin / 15));
        } else if ("H2".equals(freq)) {
            return leftPad(StringUtils.getStr(betweenMin / 5));
        } else if ("H3".equals(freq)) {
            return leftPad(StringUtils.getStr(betweenMin / 30));
        } else if ("M1".equals(freq)) {
            return leftPad(StringUtils.getStr(betweenMin / 60));
        } else if ("M3".equals(freq)) {
            return leftPad(StringUtils.getStr(betweenMin / 180));
        } else {
            return "0001";
        }
    }

    /**
     * 补全序列
     *
     * @param str 字符串
     * @return 序列
     * @date 2019-10-30 16:33:29
     */
    private String leftPad(String str) {
        return org.apache.commons.lang3.StringUtils.leftPad(str, 4, '0');
    }

    /**
     * 获取频度编码
     *
     * @param freq 频度
     * @return 解析后的频度编码
     * @date 2019-10-30 16:08:47
     */
    private String getFreq(String freq) {
        if ("随时".equals(freq)) {
            return "H0";
        } else if ("15分钟".equals(freq)) {
            return "H1";
        } else if ("5分钟".equals(freq)) {
            return "H2";
        } else if ("30分钟".equals(freq)) {
            return "H3";
        } else if ("1小时".equals(freq)) {
            return "M1";
        } else if ("3小时".equals(freq)) {
            return "M3";
        } else if ("1天".equals(freq)) {
            return "L1";
        } else if ("1月".equals(freq)) {
            return "L2";
        } else {
            return "H0";
        }
    }

    /**
     * 是否能力中心行
     *
     * @param row 行数据
     * @return boolean
     * @date 2019-10-30 16:52:16
     */
    private boolean isDeptRow(List<Object> row) {
        return StringUtils.isNotBlank(StringUtils.getStr(row.get(0)));
    }

    /**
     * 是否信息点第一行
     *
     * @param row 行数据
     * @return boolean
     * @date 2019-10-30 16:20:29
     */
    private boolean isEventFirstRow(List<Object> row) {
        return StringUtils.isNotBlank(StringUtils.getStr(row.get(1)))
                && StringUtils.isNotBlank(StringUtils.getStr(row.get(2)))
                && StringUtils.isNotBlank(StringUtils.getStr(row.get(3)));
    }
}
