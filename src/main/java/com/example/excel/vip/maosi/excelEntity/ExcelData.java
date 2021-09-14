package com.example.excel.vip.maosi.excelEntity;

import lombok.Data;

/**
 * @Author Maosi
 * @Date 2021-09-12 15:54
 * @Describe
 */
@Data
public class ExcelData {

    private String line1;
    private String line2;
    private String line3;
    private String line4;
    private String line5;
    private String line6;

    //   读取指定的列
    //   强制读取第三个 这里不建议 index 和 name 同时用，要么一个对象只用index，要么一个对象只用name去匹配
    //   @ExcelProperty(index = 2)
    //   private Double doubleData;
    //    用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
    //   @ExcelProperty("字符串标题")
    //   private String string;
    //   @ExcelProperty("日期标题")
    //   private Date date;
}
