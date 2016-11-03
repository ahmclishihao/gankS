package com.lsh.gank.bean;

import java.util.ArrayList;
import java.util.List;

public class ListData {
    public boolean error;

    public List<ListBean> results = new ArrayList<>();

    public class ListBean {
        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
        public List<String> images;
    }

}
