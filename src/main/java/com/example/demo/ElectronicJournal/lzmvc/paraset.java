package com.example.demo.ElectronicJournal.lzmvc;

import com.example.demo.util.common;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class paraset {

    public static void reconnection(String name){
        String url = "http://lzmvc.vip.qikan.com/SearchByName.aspx?t=4&o=1&k=" + common.getgb2312( name);
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
