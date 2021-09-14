package com.example.excel.vip.maosi.controller;

import com.example.excel.vip.maosi.util.ExcelUtil;
import com.example.excel.vip.maosi.util.Response;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @Author Maosi
 * @Date 2021-09-11 18:46
 * @Describe
 */
@Controller
@Data
@ConfigurationProperties(prefix = "upload")
public class UpfileController {

    private String path;
    private List<String> endWiths;

    @Autowired
    ExcelUtil excelUtil;


    @ResponseBody
    @PostMapping("/upFile")
    public Response Upfile(@RequestPart("file") MultipartFile uploadFile){

        //判断是否有文件
        if (uploadFile.isEmpty()){
            return new Response("405","文件为空");
        }

        int flag = 0;
        //判断是否为指定的格式
        for (String item : endWiths) {
            if (!Objects.requireNonNull(uploadFile.getOriginalFilename()).endsWith(item)){
                flag++;
            }
        }
        if (endWiths.size() == flag){
            return new Response("405","不是指定的格式");
        }

        try {
            //多线程处理excel
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    excelUtil.readExcel(uploadFile);
                }
            };
            new Thread(runnable).start();

            //保存文件
            uploadFile.transferTo(new File(path, Objects.requireNonNull(uploadFile.getOriginalFilename())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //成功
        return new Response("200","上传成功");
    }
}
