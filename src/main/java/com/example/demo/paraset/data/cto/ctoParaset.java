package com.example.demo.paraset.data.cto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.paraset.data.gbp.R;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ctoParaset {

    private static final String rul = "http://e-learning.51cto.com";

    //需用远程测试
    public static  List<JSONObject> reconnection(String name){
        String url = "http://e-learning.51cto.com/search?keyword=" +name;
        Connection con = Jsoup.connect(url);
        try {
            Connection.Response execute = con.method(Connection.Method.GET).timeout(3000).execute();
            Document parse = Jsoup.parse(execute.body());
            List<JSONObject> bjadks = getBjadks(parse);
            return bjadks;
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }

    public static List<JSONObject> getBjadks(Document doucuDocument){

        //            System.out.println(doucuDocument);

        ArrayList<JSONObject> list = new ArrayList<>();
        String css = "div.search_bottom_box.w1200 ul.search_course_box_list";
        String cssQuery = "li";
        Elements selects = doucuDocument.select(css);
        Elements select1 = selects.select(cssQuery);
        for (int i = 0; i < select1.size(); i++) {
            JSONObject object = new JSONObject();
            Element select = selects.select(cssQuery).get(i);

            try {
                //名字
                String cssQue = "a.right div.tit fl";
                Elements elements = select.select(cssQue);
                String banem = elements.text();
                object.put("name", banem);

                object.put("born", "51cto");

                //作者
                String cssQue1 = "div.right div.con_03.mt10";
                Elements elements1 = select.select(cssQue1);
                String author = elements1.first().text();
                object.put("author", author);

                object.put("type", "视频");

                object.put("time", "最新一期");

                //url
                String cssQu = "a.a_img.fl";
                Elements element = select.select(cssQu);
                String href = element.attr("href");
                object.put("url",rul + href);
            } catch (Exception e) {
                return null;
            }

            object.put("about", "cto");
            list.add(object);
        }
        return list;
    }

}
