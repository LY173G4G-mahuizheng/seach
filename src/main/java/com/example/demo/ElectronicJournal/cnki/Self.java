package com.example.demo.ElectronicJournal.cnki;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Self {

    private String name;
    private HashMap<String,String> Header;
    private Map<String,String> cookie;
    private String opener;


}
