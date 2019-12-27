package com.example.demo.paraset.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

public class common {

    public static String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss",java.util.Locale.US);
        return simpleDateFormat.format(System.currentTimeMillis()) + " GMT+0800 (中国标准时间)";
    }

    public static String getStr(String str)  {
        try {
            return URLEncoder.encode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "字符串异常";
        }
    }

    public static String getgb2312(String str)  {
        try {
            return URLEncoder.encode(str,"gb2312");
        } catch (UnsupportedEncodingException e) {
            return "字符串异常";
        }
    }

    public static String get8859_1(String str)  {
        try {
            return new String(str.getBytes("GB2312"),"8859_1");
        } catch (UnsupportedEncodingException e) {
            return "字符串异常";
        }
    }
}
