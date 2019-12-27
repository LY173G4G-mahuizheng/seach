package com.example.demo.paraset.book.ss;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.paraset.data.gbp.FormData;
import com.example.demo.paraset.data.gbp.R;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chaoxin {

    private static final String href = "http://www.sslibrary.com";

    public static List<JSONObject> reconnection(String name) {
        String url = "http://www.sslibrary.com/book/search";
        Connection con = Jsoup.connect(url);
        try {
            Connection.Response execute = con.data(fromMap.query_string(name)).method(Connection.Method.GET).execute();
            Map<String, String> cookies = execute.cookies();
            Jsoup.parse(execute.body());
            Document document = getDocument(name, cookies);
            System.out.println(document);
            List<JSONObject> can = getCan(document);
            return can;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Document getDocument(String name, Map<String, String> cookies) {

        HashMap<String, String> map = new HashMap<>();
        map.put("Connection", "Keep-Alive");
        map.put("Accept", "text/html,*/*");
        map.put("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");

        String url = "http://www.sslibrary.com/book/search/do";
        Connection con = Jsoup.connect(url);
        Connection headers = con.headers(map);
        try {
            Connection.Response execute = headers.data(fromMap.query(name)).timeout(5000)
                    .cookies(cookies).method(Connection.Method.POST).execute();
            Document parse = Jsoup.parse(execute.body());
            return parse;
        } catch (Exception e) {
        }
        return null;
    }

    public static List<JSONObject> getCan(Document parse) {
        ArrayList<JSONObject> list = new ArrayList<>();
        try {
        String css = "body";
        String selects = parse.select(css).text();
        selects.replace("\\","");

        selects = StringEscapeUtils.unescapeJava(selects);
        Response response = JSON.parseObject(selects, Response.class);

            for (int i = 0; i < response.getData().getResult().size(); i++) {
                JSONObject object = new JSONObject();
                object.put("name", response.getData().getResult().get(i).getBookName());
                object.put("author", response.getData().getResult().get(i).getAuthor());
                object.put("born", response.getData().getResult().get(i).getPublisher());
                object.put("time", response.getData().getResult().get(i).getDate());
                object.put("type", "图书");
                object.put("url", href + "cnFenlei=" + response.getData().getResult().get(i).getCnFenlei() + "&ssid=" + response.getData().getResult().get(i).getSsid()
                        + "&d=" + response.getData().getResult().get(i).getD() + "&isFromBW=" + response.getData().getResult().get(i).getIsFromBW()
                        + "&isjgptjs=" + "false");
                object.put("about", "超星");
                list.add(object);
            }
        } catch (Exception e) {
            return null;
        }
        return list;
    }

}
