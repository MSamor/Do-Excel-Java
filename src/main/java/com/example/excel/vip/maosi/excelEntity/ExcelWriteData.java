package com.example.excel.vip.maosi.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.Data;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @Author Maosi
 * @Date 2021-09-12 19:40
 * @Describe
 */
@Data
//行高和列宽
//@ContentRowHeight(10)
//@HeadRowHeight(20)
@ColumnWidth(25)

//颜色风格
// 头背景设置成红色 IndexedColors.RED.getIndex()
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 9)
// 头字体设置成20
//@HeadFontStyle(fontHeightInPoints = 20)
// 内容的背景设置成绿色 IndexedColors.GREEN.getIndex()
//@ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 17)
// 内容字体设置成20
//@ContentFontStyle(fontHeightInPoints = 20)
public class ExcelWriteData {
    @ExcelProperty("第一列")
    private String line1;
    @ExcelProperty("第二列")
    private String line2;
    @ExcelProperty("第三列")
    private String line3;

}
