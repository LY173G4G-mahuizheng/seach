package com.example.demo.ElectronicJournal.cnki;

import com.example.demo.util.common;

import java.util.HashMap;
import java.util.Map;

public class paraset {



    public static Map<String,String> make_parameters(String search){
        HashMap<String, String> parameter = new HashMap<>();
        parameter.put("action","");
        parameter.put("NaviCode","*");
        parameter.put("ua","1.21");
        parameter.put("PageName","ASP.brief_result_aspx");
        parameter.put("DbPrefix","SCDB");
        parameter.put("DbCatalog","中国学术文献网络出版总库");
        parameter.put("ConfigFile","SCDB.xml");
        parameter.put("db_opt","CJFQ,CDFD,CMFD,CPFD,IPFD,CCND,CCJD");
        parameter.put("isinEn","1");
        parameter.put("publishdate_from","");
        parameter.put("publishdate_to","");
        parameter.put("his","0");
        parameter.put("__", common.getDate());
        parameter.put("txt_1_relation","#CNKI_AND");
        parameter.put("txt_1_special1","=");
        parameter.put("txt_1_sel","SU$%=|");
        parameter.put("txt_1_value1",search);
        return parameter;
    }
    public static Map<String,String> query_string(){
        HashMap<String, String> query = new HashMap<>();
        query.put("curpage","1");
        query.put("RecordsPerPage","50");
        query.put("QueryID","16");
        query.put("ID","");
        query.put("turnpage","1");
        query.put("DbCatalog","中国学术文献网络出版总库");
        query.put("ConfigFile","SCDB.xml");
        query.put("tpagemode","L");
        query.put("dbPrefix","SCDB");
        query.put("Fields","");
        query.put("DisplayMode","listmode");
        query.put("isinEn","1");
        query.put("research","off");
        query.put("PageName","ASP.brief_result_aspx");
        query.put("sKuaKuID","16");
        query.put("t",System.currentTimeMillis()+"");
        return query;
    }

}
