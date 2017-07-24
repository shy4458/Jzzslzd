package com.bksx.jzzslzd.bo;

/**
 * 代码表
 * Created by YangJ on 2017/6/29.
 */
public class CodeTable {
    private String id;
    private String name;

    public CodeTable() {

    }

    public CodeTable(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
