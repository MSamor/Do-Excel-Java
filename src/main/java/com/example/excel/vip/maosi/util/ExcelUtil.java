package com.example.excel.vip.maosi.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.example.excel.vip.maosi.excelEntity.ExcelData;
import com.example.excel.vip.maosi.listener.ExcelDataListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * @Author Maosi
 * @Date 2021-08-16 20:27
 * @Describe
 */

@Component
@Slf4j
public class ExcelUtil {
    public void readExcel(MultipartFile file){
        ExcelReader excelReader = null;
        try {
            try {
                //第一种读：读指定表
                //读的逻辑
                excelReader = EasyExcel.read(file.getInputStream(), ExcelData.class, new ExcelDataListener()).build();
                //读第几张表
                ReadSheet readSheet = EasyExcel.readSheet(0).build();
                //多行头
                //ReadSheet readSheet = EasyExcel.readSheet(0).headRowNumber(2).build();
                //开始读
                excelReader.read(readSheet);

                //第二种读：读所有表
                // EasyExcel.read(file.getInputStream(), ExcelData.class, new ExcelDataListener()).doReadAll();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }
}
