package com.lizxing.daily.gson;

import com.google.gson.annotations.SerializedName;

public class Quotes {

    public String content;

    @SerializedName("mrname")
    public String author;
}
