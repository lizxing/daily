package com.lizxing.daily.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PoemsList {

    public int code;

    public String msg;

    @SerializedName("newslist")
    public List<Poems> poemsList;
}
