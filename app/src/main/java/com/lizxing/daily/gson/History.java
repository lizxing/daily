package com.lizxing.daily.gson;

import com.google.gson.annotations.SerializedName;

public class History {
    public String title;

    @SerializedName("lsdate")
    public String date;

}
