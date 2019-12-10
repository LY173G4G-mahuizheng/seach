package com.example.demo.data.bjadks;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class bjadks {
    public static void reconnection(String name){
        String url = "https://wb.bjadks.com/Search/Index?keyword=" + name + "&selVal=-1&PageIndex=1";
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
