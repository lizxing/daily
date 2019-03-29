package com.lizxing.daily.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EnglishList {

    public int code;

    public String msg;

    @SerializedName("newslist")
    public List<English> EnglishList ;

}
