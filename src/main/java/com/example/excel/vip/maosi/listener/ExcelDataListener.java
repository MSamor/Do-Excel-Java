package com.example.excel.vip.maosi.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.example.excel.vip.maosi.excelEntity.ExcelData;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Maosi
 * @Date 2021-09-12 15:55
 * @Describe
 */
@Slf4j
public class ExcelDataListener extends AnalysisEventListener<ExcelData> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<ExcelData> list = new ArrayList<ExcelData>();


    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param
     */
//    private ExcelDao excelDao;
//    public ExcelDataListener(ExcelDao excelDao) {
//        this.excelDao = excelDao;
//    }

    //这个每一条数据解析都会来调用
    @Override
    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
        list.add(excelData);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    /***
     * @Author 魏鹏 MAOSI-AMOR
     * @Description //所有数据解析完成了 都会来调用
     * @Date 16:20 2021-09-12
     * @Param [analysisContext]
     * @return void
     **/
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库

        //模拟解析时间长
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        saveData();
    }

    /***
     * @Author 魏鹏 MAOSI-AMOR
     * @Description //读excel的第一行，就是标题栏
     * @Date 16:19 2021-09-12
     * @Param [headMap, context]
     * @return void
     **/
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println(headMap.toString());
    }

    /***
     * @Author 魏鹏 MAOSI-AMOR
     * @Description //保存数据到数据库
     * @Date 16:19 2021-09-12
     * @Param []
     * @return void
     **/
    private void saveData() {
        for (ExcelData data : list) {
            System.out.println("data = " + data);
        }
    }

    /***
     * @Author 魏鹏 MAOSI-AMOR
     * @Description //如果这里不抛出异常则 继续读取下一行。
     * @Date 16:45 2021-09-12
     * @Param [exception, context]
     * @return void
     **/
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex());
        }
    }
}
