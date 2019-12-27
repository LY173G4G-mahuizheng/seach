package com.example.demo.paraset.data.gbp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class gbpParaset {

    private static final String rul = "http://gpd.sunwayinfo.com.cn";

    public static List<JSONObject> reconnection(String name) {
        String url = "http://gpd.sunwayinfo.com.cn/NewProductSearch.aspx?key=" + name;
        Connection con = Jsoup.connect(url);
        try {
            Connection.Response execute = con.method(Connection.Method.GET).execute();
            Map<String, String> cookies = execute.cookies();
            Jsoup.parse(execute.body());
            Document document = getDocument(name, cookies);
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

        String url = "http://gpd.sunwayinfo.com.cn/AjaxHandler/LuceneAjaxHandler.ashx?optype=searchRecord&pageIndex=1&pageSize=10";
        Connection con = Jsoup.connect(url);
        Connection headers = con.headers(map);
        try {
            Connection.Response execute = headers.data(FormData.query_string(name)).timeout(5000)
                    .cookies(cookies).method(Connection.Method.POST).execute();
            Document parse = Jsoup.parse(execute.body());
            return parse;
        } catch (Exception e) {
        }
        return null;
    }

    public static List<JSONObject> getCan(Document parse) {
        ArrayList<JSONObject> list = new ArrayList<>();
        String css = "body";
        String selects = parse.select(css).text();
        selects = StringEscapeUtils.unescapeJava(selects);
        try {
        R r = JSON.parseObject(selects, R.class);

            for (int i = 0; i < r.getRecord().size(); i++) {
                JSONObject object = new JSONObject();
                object.put("name",r.getRecord().get(i).getProductName());
                object.put("author",r.getRecord().get(i).getComName());
                object.put("born",r.getRecord().get(i).getKeyword());
                object.put("time",r.getRecord().get(i).getPutdate());
                object.put("type",r.getRecord().get(i).getFileTypes());
                object.put("url",rul + "/ProductDetail.aspx?id=" + r.getRecord().get(i).getGuidNum());
                object.put("about","尚唯");
                list.add(object);
            }
        } catch (Exception e) {
            return null;
        }
        return list;
    }

    public static void main(String[] args) {
        reconnection("机器");
    }
}
