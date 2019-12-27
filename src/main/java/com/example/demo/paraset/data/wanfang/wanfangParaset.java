package com.example.demo.paraset.data.wanfang;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class wanfangParaset {

    private static final String rul = "http://www.wanfangdata.com.cn";

    public static  List<JSONObject> reconnection(String name) {
        ArrayList<JSONObject> list = new ArrayList<>();
        try {
            for (int i = 1; i < 6; i++) {

        String url = "http://www.wanfangdata.com.cn/search/searchList.do?beetlansyId=aysnsearch&" +
                "searchType=all&pageSize=20&page="+i+"&searchWord=" + name + "&order=correlation&showType=detail&isCheck=check&" +
                "isHit=&isHitUnit=&firstAuthor=false&corePerio=false&rangeParame=&navSearchType=all";
        Connection con = Jsoup.connect(url);

            Connection.Response execute = con.method(Connection.Method.GET).timeout(5000).execute();
            Document parse = Jsoup.parse(execute.body());
            List<JSONObject> wanfang = getWanfang(parse);
                list.addAll(wanfang);
        }
            list.forEach(l -> System.out.println(l));
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }

    public static List<JSONObject> getWanfang(Document doucuDocument){
        ArrayList<JSONObject> list = new ArrayList<>();
        String css1 = "div.ResultBlock div.ResultList";


        String css = "div.ResultBlock";
        Elements selects = doucuDocument.select(css);
        Elements selects1 = doucuDocument.select(css1);
        //String []str = {"aother","born","time","type",""};
        for (int i = 0; i < selects1.size(); i++) {
            JSONObject object = new JSONObject();
            try {
                String cssQuery = "div.ResultList div.ResultCont";
                Element select = selects.select(cssQuery).get(i);

                String cssQ = "div.ResultMoreinfo div.author a";
                Elements e = select.select(cssQ);
                String author = e.text();
                object.put("author", author);

                String c1 = "div.ResultMoreinfo div.author span";
                Elements e1 = select.select(c1);
                String type = e1.text();
                object.put("type", type);

                String c2 = "div.ResultMoreinfo div.Source a";
                Elements e2 = select.select(c2);
                String born = e2.text();
                object.put("born", born);

                String c3 = "div.ResultMoreinfo div.Volume a";
                Elements e3 = select.select(c3);
                String time = e3.text();
                object.put("time", time);

                String cssQu = "div.title a";
                Element element = select.select(cssQu).first();
                String name = element.text();
                object.put("name", name);

                String cssQue = "div.title a";
                Element elements = select.select(cssQue).first();
                String href = elements.attr("href");
                object.put("href",rul + href);
            } catch (Exception ex) {
                return null;
            }

            object.put("about", "万方");
            list.add(object);
        }
        //list.forEach(l -> l.forEach((k,v) -> System.out.println(k + "----------------->" + v)));


        return list;
    }


    public static void main(String[] args) {

        reconnection("机器");


    }

}
