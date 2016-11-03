package com.lsh.gank.bean;

import java.util.ArrayList;
import java.util.List;


public class WordData {

    public BasicBean basic = new BasicBean();// 单词的基本解释，句子翻译时只有translation没有basic

    public class BasicBean {
        public List<String> explains=new ArrayList<>(); // 含义有多个
        public String phonetic;//普通发音
        public String uk_phonetic;// 英式
        public String us_phonetic;// 美式
    }

    public int errorCode;// 0 为正确
    public String query;// 所查询的单词
    public List<String> translation;// 单词的翻译

    public List<WebBean> web;// 更多联想

    public static class WebBean {
        public String key;// 联想的单词
        public List<String> value;// 联想单词对应的意思
    }
}
