package com.example.demo.data.gbp;

import com.example.demo.util.common;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class paraset {

    public static void reconnection(String name){
        String url = "http://gpd.sunwayinfo.com.cn/NewProductSearch.aspx?key=" + name;
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
        reconnection("机器");
    }

}
