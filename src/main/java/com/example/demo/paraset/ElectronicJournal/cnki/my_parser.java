package com.example.demo.paraset.ElectronicJournal.cnki;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class my_parser {

    private static final String rul = "https://kns.cnki.net/KCMS/detail/detail.aspx?";

    public static Self initself(Self self) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Connection", "Keep-Alive");
        map.put("Accept", "text/html,*/*");
        map.put("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
        self.setHeader(map);
        return self;
    }

    public static Self reconnection(Self self) {
        self = initself(self);
        String url = "http://kns.cnki.net/KNS/request/SearchHandler.ashx?";
        Connection con = Jsoup.connect(url);
        Connection headers = con.headers(self.getHeader());
        try {
            Connection.Response execute = headers.data(paraset.make_parameters(self.getName()))
                    .method(Connection.Method.GET).execute();
            self.setCookie(execute.cookies());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return self;
    }

    public static List<JSONObject> goto_page_helper(Self self) {
        ArrayList<JSONObject> list = new ArrayList<>();
        self = initself(self);
        try {
            for (int i = 1; i < 6; i++) {
                String url = "http://kns.cnki.net/kns/brief/brief.aspx?&turnpage=1&dbPrefix=SCDB&DbCatalog=中国学术文献" +
                        "网络出版总库&curpage=" + i + "&ConfigFile=SCDB.xml&QueryID=16&isinEn=1&PageName=ASP.brief_result_aspx&" +
                        "RecordsPerPage=50&tpagemode=L&research=off&DisplayMode=listmode&Fields=&t=" +
                        System.currentTimeMillis() + "sKuaKuID=16&ID=";

                Connection con = Jsoup.connect(url);
                Connection headers = con.headers(self.getHeader());
                Connection.Response execute = headers.cookies(reconnection(self).getCookie()).method(Connection.Method.GET).execute();

                Document doucuDocument = Jsoup.parse(execute.body());
                List<JSONObject> json = getJson(doucuDocument);
                list.addAll(json);
            }
//            list.forEach(l -> System.out.println(l));
//            System.out.println(list.size());
            return list;
        } catch (Exception e) {
            try {
                TimeUnit.SECONDS.sleep(3000);
            } catch (Exception s) {

            }
        }
        return null;
    }

    public static List<JSONObject> getJson(Document doucuDocument) {

        //            System.out.println(doucuDocument);

        ArrayList<JSONObject> list = new ArrayList<>();


        String css = "tbody table.GridTableContent tbody";
        Elements selects = doucuDocument.select(css);
        String[] str = {"", "name", "aother", "born", "time", "type"};
        String cssQuery = "tr";
        //System.out.println(selects);
        for (int i = 1; i < selects.select(cssQuery).size(); i++) {
            JSONObject object = new JSONObject();

            String href = null;
            try {
                Element select = selects.select(cssQuery).get(i);
//                System.out.println(select);
                for (int j = 1; j < 6; j++) {
                    String cssQ = "td";
                    Element element = select.select(cssQ).get(j);
                    String text = element.text();
                    //                  System.out.println(text);
                    object.put(str[j], text);
                }
                String cssQu = "td a.fz14";
                Elements element = select.select(cssQu);
                href = element.attr("href");
                int i1 = href.indexOf("FileName=", 0);
                int i2 = href.indexOf("yx=", 0);
                href.substring(i1, i2);
            } catch (Exception e) {
                return null;
            }
            object.put("url", rul + href);
            object.put("about", "知网");
            list.add(object);
        }
        return list;
    }


//    public static void main(String[] args) {
//        Self self = new Self().setName("周恩来心目中的毛泽东");
//        goto_page_helper(self);
//    }
}
