package com.example.excel.vip.maosi.util;

/**
 * @Author Maosi
 * @Date 2021-09-11 18:48
 * @Describe
 */
public class Response {
    private String msg;
    private String code;
    private Object data;

    public Response(String code) {
        this(null,code);
    }

    public Response( String code,String msg) {
        this(code,msg,null);
    }

    public Response(String code, String msg,  Object data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
