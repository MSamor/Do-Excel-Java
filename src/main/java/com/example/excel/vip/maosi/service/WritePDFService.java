package com.example.excel.vip.maosi.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;


/**
 * @Author Maosi
 * @Date 2021-09-12 20:16
 * @Describe
 */
@Data
@Service
@ConfigurationProperties(prefix = "write.pdf")
public class WritePDFService {

    // 最大宽度
    private static int maxWidth = 520;

    private String path;
    // 定义全局的字体静态变量
    private static Font titlefont;
    private static Font headfont;
    private static Font keyfont;
    private static Font textfont;

    // 静态代码块
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 16, Font.BOLD);
            headfont = new Font(bfChinese, 14, Font.BOLD);
            keyfont = new Font(bfChinese, 10, Font.BOLD);
            textfont = new Font(bfChinese, 10, Font.NORMAL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String PrintPDF() throws FileNotFoundException, DocumentException {
        String fileName = System.currentTimeMillis()+".pdf";
        String filePath = path+"/"+fileName;
        Document document = new Document(PageSize.A4,50,50,30,20);
        //获取一个pdfWriter实例
        PdfWriter.getInstance(document,new FileOutputStream(filePath));
        document.open();
        //文档编排开始
        document.add(new Paragraph("Hello World!"));

        // 表格
        PdfPTable table = createTable(new float[] { 40, 120, 120, 120, 80, 80 });
        table.addCell(createCell("美好的一天", headfont, Element.ALIGN_LEFT, 6, false));
        table.addCell(createCell("早上9:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("中午11:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("中午13:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("下午15:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("下午17:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("晚上19:00", keyfont, Element.ALIGN_CENTER));
        Integer totalQuantity = 0;
        for (int i = 0; i < 5; i++) {
            table.addCell(createCell("起床", textfont));
            table.addCell(createCell("吃午饭", textfont));
            table.addCell(createCell("午休", textfont));
            table.addCell(createCell("下午茶", textfont));
            table.addCell(createCell("回家", textfont));
            table.addCell(createCell("吃晚饭", textfont));
            totalQuantity ++;
        }
        table.addCell(createCell("总计", keyfont));
        table.addCell(createCell("", textfont));
        table.addCell(createCell("", textfont));
        table.addCell(createCell("", textfont));
        table.addCell(createCell(String.valueOf(totalQuantity) + "件事", textfont));
        table.addCell(createCell("", textfont));
        document.add(table);
        //文档编排结束

        //文档信息
        document.addTitle("maosi");// 标题
        document.addAuthor("maosi");// 作者
        document.addSubject("maosi");// 主题
        document.addKeywords("maosi");// 关键字
        document.addCreator("maosi");// 创建者
        document.close();

        return fileName;
    }


/**------------------------创建表格单元格的方法start----------------------------*/
    /**
     * 创建单元格(指定字体)
     * @param value
     * @param font
     * @return
     */
    public PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平..）
     * @param value
     * @param font
     * @param align
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param boderFlag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        if (!boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        } else if (boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(15.0f);
        }
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平..、边框宽度：0表示无边框、内边距）
     * @param value
     * @param font
     * @param align
     * @param borderWidth
     * @param paddingSize
     * @param flag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, float[] borderWidth, float[] paddingSize, boolean flag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setBorderWidthLeft(borderWidth[0]);
        cell.setBorderWidthRight(borderWidth[1]);
        cell.setBorderWidthTop(borderWidth[2]);
        cell.setBorderWidthBottom(borderWidth[3]);
        cell.setPaddingTop(paddingSize[0]);
        cell.setPaddingBottom(paddingSize[1]);
        if (flag) {
            cell.setColspan(2);
        }
        return cell;
    }
/**------------------------创建表格单元格的方法end----------------------------*/

/**--------------------------创建表格的方法start------------------- ---------*/
    /**
     * 创建默认列宽，指定列数、水平(居中、右、左)的表格
     * @param colNumber
     * @param align
     * @return
     */
    public PdfPTable createTable(int colNumber, int align) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(align);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    /**
     * 创建指定列宽、列数的表格
     * @param widths
     * @return
     */
    public PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    /**
     * 创建空白的表格
     * @return
     */
    public PdfPTable createBlankTable() {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.addCell(createCell("", keyfont));
        table.setSpacingAfter(20.0f);
        table.setSpacingBefore(20.0f);
        return table;
    }
/**--------------------------创建表格的方法end------------------- ---------*/
}
