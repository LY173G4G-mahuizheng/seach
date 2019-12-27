package com.example.demo.paraset.ElectronicJournal.lzmvc;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.paraset.util.common;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class lyParaset {


    private static final String rul = "http://lzmvc.vip.qikan.com";


    public static List<JSONObject> reconnection(String name){
        try {
        String url = "http://lzmvc.vip.qikan.com/SearchByName.aspx?t=4&o=1&k=" + common.getgb2312( name);
        Connection con = Jsoup.connect(url);

            Connection.Response execute = con.method(Connection.Method.GET).timeout(5000).execute();
            Document parse = Jsoup.parse(execute.body());
            List<JSONObject> lzmvc = getLzmvc(parse);
            return lzmvc;
        } catch (IOException e) {
            try {
                TimeUnit.SECONDS.sleep(3);
            }catch (Exception s){

            }
        }

            return null;
    }

    public static List<JSONObject> getLzmvc(Document doucuDocument){

        //            System.out.println(doucuDocument);

        ArrayList<JSONObject> list = new ArrayList<>();
        String css = "div#result";
        Elements selects = doucuDocument.select(css);
        String []str = {"name","aother","born","time","type",""};
        for (int i = 0; i < 8; i++) {
            JSONObject object = new JSONObject();
           try {
               String cssQuery = "dl";
               Element select = selects.select(cssQuery).get(i);
               for (int j = 0; j < 2; j++) {
                   String cssQ = "dd";
                   Element element = select.select(cssQ).get(j);
                   String text = element.text();
//                  System.out.println(text);
                   object.put(str[j], text);
               }
               object.put("born", "龙源");
               object.put("time", "最新一期");
               object.put("type", "期刊");
               String cssQu = "dl dt a";
               Elements element = select.select(cssQu);
               String href = element.attr("href");
               object.put("url",rul + href);
               object.put("about", "longyuan");
               list.add(object);
           }catch (Exception e){
               return null;
           }
        }
        return list;
    }

    public static void main(String[] args) {
        reconnection("职业");
    }

}
