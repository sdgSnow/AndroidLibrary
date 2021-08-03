package com.sdg.adapter.test;

public class LibraryBean {

    public String title;
    public String desc;
    public int state;//状态 0未开始 1进行中 2已完成

    public LibraryBean(String title, String desc,int state) {
        this.title = title;
        this.desc = desc;
        this.state = state;
    }
}
