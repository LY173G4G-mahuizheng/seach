package com.example.demo.paraset.book.xz;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class xzParaset {

    public static final String her = "http://172.16.104.43";

    public static List<JSONObject> reconnection(String name){
        String url = "http://172.16.104.43/web/PageList.aspx?strwhere=" +name;
        Connection con = Jsoup.connect(url);
        try {
            Connection.Response execute = con.method(Connection.Method.GET).timeout(3000).execute();
            Document parse = Jsoup.parse(execute.body());
            System.out.println(parse);
            List<JSONObject> bjadks = getBjadks(parse);
            return bjadks;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<JSONObject> getBjadks(Document doucuDocument){


        ArrayList<JSONObject> list = new ArrayList<>();
        String css = "div.g-02box div.g-con div.g-conRig div.conTab div.g-list1 ul#books1";
        try {
            Elements selects = doucuDocument.select(css);
            String css1 ="li";
            Elements selects1 = selects.select(css1);
            // String []str = {"aother","type","time","born",""};
            for (int i = 0; i < selects1.size(); i = i+4) {
                JSONObject object = new JSONObject();
                Element select = selects.select(css1).get(i);

                String cssQ = "div.g-txt div.g-info div.g-tit";
                Elements element1 = select.select(cssQ);
                element1.select("span").remove();
                String text = element1.text();
                object.put("author", text);

                String cssQu1 = "div.g-txt div.g-info p";
                Element element2 = select.select(cssQu1).first();
                String born = element2.text();
                object.put("born", born);


                object.put("time", "完本");
                object.put("type", "图书");


                String cssQu = "div.g-txt div.g-tit";
                Element element = select.select(cssQu).first();
                String name = element.attr("title");
                object.put("name", name);

                String cssQue = "a.g-img";
                Element elements = select.select(cssQue).first();
                String href = elements.attr("href");
                object.put("href",her + href);

                object.put("about", "xingzhi");
                list.add(object);
            }
        } catch (Exception e) {
            return null;
        }
        return list;
    }


}
