package com.example.demo.ElectronicJournal.cnki;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

public class my_parser {

    private static final int MAX_CONNECTION = 3,TIME_OUT = 15,WAIT_TIME = 10;

    public static Self initself(Self self) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Connection","Keep-Alive");
        map.put("Accept","text/html,*/*");
        map.put("User-Agent","Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
        self.setHeader(map);
        return self;
    }

    public static Self reconnection(Self self){
        self = initself(self);
        String url = "http://kns.cnki.net/KNS/request/SearchHandler.ashx?";
        Connection con = Jsoup.connect(url);
        Connection headers = con.headers(self.getHeader());
        try {
            Connection.Response execute = headers.data(paraset.make_parameters(self.getName())).method(Connection.Method.GET).execute();
            self.setCookie(execute.cookies());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return self;
    }

    public static void goto_page_helper(Self self){
        self = initself(self);
        String url = "http://kns.cnki.net/kns/brief/brief.aspx?";
        Connection con = Jsoup.connect(url);
        Connection headers = con.headers(self.getHeader());
        try {
            Connection.Response execute = headers.data(paraset.query_string()).cookies(reconnection(self).getCookie()).method(Connection.Method.GET).execute();
            Document doucuDocument = Jsoup.parse(execute.body());
            System.out.println(doucuDocument);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
       Self self = new Self().setName("毛泽东");
        goto_page_helper(self);
    }
}
