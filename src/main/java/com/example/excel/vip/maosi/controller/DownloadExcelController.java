package com.example.excel.vip.maosi.controller;

import com.example.excel.vip.maosi.service.DownloadFileService;
import com.example.excel.vip.maosi.service.WriteExcelService;
import com.example.excel.vip.maosi.util.Response;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author Maosi
 * @Date 2021-09-11 18:46
 * @Describe
 */
@Slf4j
@Controller
@Data
public class DownloadExcelController {

    @Autowired
    DownloadFileService downloadFileService;

    @Autowired
    WriteExcelService writeExcel;

    @GetMapping("/downloadExcel")
    public Response downloadFile(HttpServletResponse response, @RequestParam(value = "fileName",required = false) String fileName){
        try {
            if (fileName != null && !fileName.equals("")){
                downloadFileService.downloadFile(response, fileName);
            }else {
                String newFile = writeExcel.writeExcel();
                downloadFileService.downloadFile(response, newFile);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("500","下载错误");
        }
    }
}
