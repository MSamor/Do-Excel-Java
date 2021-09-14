package com.example.excel.vip.maosi.service;

import com.example.excel.vip.maosi.util.Response;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @Author Maosi
 * @Date 2021-09-12 19:34
 * @Describe
 */
@Service
@Data
@ConfigurationProperties(prefix = "download")
@Slf4j
public class DownloadFileService {

    private String path;

    public Response downloadFile(HttpServletResponse response, String fileName){
        File file = new File(path +'/'+ fileName);
        if(!file.exists()){
            return new Response("404","下载文件不存在");
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        try {
            //处理文件中的特殊符号
            response.setHeader("Content-disposition", "attachment; filename="+ new String(URLEncoder.encode(fileName,"utf-8").getBytes("UTF-8"), "ISO-8859-1") + ".xls");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
            return null;
        } catch (IOException e) {
            log.error("{}",e);
            return new Response("405","下载失败");
        }
    }
}
