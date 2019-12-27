package com.example.demo.paraset.data.wxxzx;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class wxxzxParaset {

    private static final String rul = "http://lzmvc.chineseall.cn";

    public static List<JSONObject> reconnection(String name){
        String url = "http://lzmvc.chineseall.cn/org/show/4072/search/" + name + "/0";
        Connection con = Jsoup.connect(url);
        try {
            Connection.Response execute = con.method(Connection.Method.GET).timeout(5000).execute();
            Document parse = Jsoup.parse(execute.body());
            List<JSONObject> bjadks = getBjadks(parse);
//            System.out.println(parse);
            return bjadks;
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }

    public static List<JSONObject> getBjadks(Document doucuDocument){


        ArrayList<JSONObject> list = new ArrayList<>();
        String css = "div.bb1.yuelanshiList div.con";
        Elements selects = doucuDocument.select(css);
       // String []str = {"aother","type","time","born",""};
        for (int i = 0; i < 30; i++) {
            JSONObject object = new JSONObject();
            try {
                String cssQuery = "div.boxListLi5";
                Element select = selects.select(cssQuery).get(i);

                String cssQ = "div.clearfix.other span";
                String element1 = select.select(cssQ).text();

                int i1 = element1.indexOf("/",0);
                int i2 = element1.indexOf("/", i1+1);

                object.put("author",element1.substring(0,i1));
                object.put("born",element1.substring(i1+1,i2));
                object.put("time",element1.substring(i2+1));
                object.put("type","图书");
                String cssQu = "div.boxListLi5 h2 a";
                Element element = select.select(cssQu).first();
                String name = element.text();
                object.put("name", name);

                String cssQue = "div.boxListLi5 h2 a";
                Element elements = select.select(cssQue).first();
                String href = elements.attr("href");
                object.put("href",rul + href);
            } catch (Exception e) {
                return null;
            }

            object.put("about", "jbadks");
            list.add(object);
        }
        return list;
    }

    public static void main(String[] args) {
        reconnection("女性");


    }
}
