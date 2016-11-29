package com.example.zemoso.uistuff;

import android.graphics.Bitmap;

/**
 * Created by zemoso on 28/11/16.
 */

public class Data {
    public Data(String name, Boolean flag, Bitmap bm) {
        this.name = name;
        this.flag = flag;
        this.bm = bm;
    }

    private String name;
    private Boolean flag;
    private Bitmap bm;

    public Data() {
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public String getName() {

        return name;
    }
    public Boolean getFlag() {

        return flag;
    }
    public Bitmap getImg() {
        return bm;
    }
}
