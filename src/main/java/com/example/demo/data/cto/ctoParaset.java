package com.example.demo.data.cto;

import com.example.demo.util.common;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ctoParaset {
    public static void reconnection(String name){
        String url = "http://e-learning.51cto.com/search?keyword=" +name;
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
        reconnection("经济");
    }


}
