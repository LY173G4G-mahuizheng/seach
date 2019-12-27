package com.example.demo.paraset.data.bjadks;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class bjadks {

    private static final String rul = "https://wb.bjadks.com";

    public static  List<JSONObject> reconnection(String name){
        String url = "https://wb.bjadks.com/Search/Index?keyword=" + name + "&selVal=-1&PageIndex=1";
        Connection con = Jsoup.connect(url);
        try {
            Connection.Response execute = con.method(Connection.Method.GET).timeout(5000).execute();
            Document parse = Jsoup.parse(execute.body());
            List<JSONObject> bjadks = getBjadks(parse);
            return bjadks;
        } catch (IOException e) {
            try {
                TimeUnit.SECONDS.sleep(3);
            }catch (Exception s){
            }
        }
        return null;
    }

    public static List<JSONObject> getBjadks(Document doucuDocument){

        //            System.out.println(doucuDocument);

        ArrayList<JSONObject> list = new ArrayList<>();
        String css = "div.courseListVer";
        Elements selects = doucuDocument.select(css);
        String []str = {"aother","type","time","born",""};
        for (int i = 0; i < 10; i++) {
            JSONObject object = new JSONObject();
            String cssQuery = "dl";
            try {
                Element select = selects.select(cssQuery).get(i);
                for (int j = 0; j < 3; j++) {
                    String cssQ = "dd p.info em";
                    Element element = select.select(cssQ).get(j);
                    String text = element.text();
    //                  System.out.println(text);
                    object.put(str[j], text);
                }
                object.put("born", "爱迪科森");
                //名字
                String cssQue = "dl dd h3";
                Elements elements = select.select(cssQue);
                String banem = elements.text();
                object.put("name", banem);

                String cssQu = "dl dt a";
                Elements element = select.select(cssQu);
                String href = element.attr("href");
                object.put("url",rul + href);
            } catch (Exception E){
                return null;
            }
            object.put("about", "bjadks");
            list.add(object);
        }
        return list;
    }

//    public static void main(String[] args) {
//        reconnection("机器");
//    }

}
