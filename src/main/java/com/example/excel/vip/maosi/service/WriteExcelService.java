package com.example.excel.vip.maosi.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.example.excel.vip.maosi.excelEntity.ExcelWriteData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author Maosi
 * @Date 2021-09-12 19:39
 * @Describe
 */
@Data
@Service
@ConfigurationProperties(prefix = "write.excel")
public class WriteExcelService {

    private String path;

    public String writeExcel(){
        String fileName = System.currentTimeMillis() + ".xlsx";
        String filePath= path + "/" + fileName;
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = null;
        try {
            //导出data中所有数据
//            excelWriter = EasyExcel.write(filePath, ExcelWriteData.class).build();
//            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
//            excelWriter.write(data(), writeSheet);

            //导出指定数据
            Set<String> excludeColumnFiledNames = new HashSet<String>();
            excludeColumnFiledNames.add("line2");
            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            EasyExcel.write(filePath, ExcelWriteData.class)
                    .excludeColumnFiledNames(excludeColumnFiledNames)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet("模板")
                    .doWrite(data());
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
        return fileName;
    }

    private List data() {
        List<ExcelWriteData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelWriteData excelWriteData = new ExcelWriteData();
            excelWriteData.setLine1("第1列第"+i+"行数据");
            excelWriteData.setLine2("第2列第"+i+"行数据");
            excelWriteData.setLine3("第3列第"+i+"行数据");
            list.add(excelWriteData);
        }
        return list;
    }
}
