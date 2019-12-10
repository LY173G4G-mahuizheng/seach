package com.example.demo.data.wanfang;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class wanfangParaset {

    public static void reconnection(String name) {
        String url = "http://www.wanfangdata.com.cn/search/searchList.do?searchType=all&showType=&pageSize=&searchWord=" + name + "&isTriggerTag=";
        Connection con = Jsoup.connect(url);
        try {
            Connection.Response execute = con.method(Connection.Method.GET).execute();
            Document parse = Jsoup.parse(execute.body());
            System.out.println(parse);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {



    }

}
