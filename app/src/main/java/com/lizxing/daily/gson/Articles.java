package com.lizxing.daily.gson;

import com.google.gson.annotations.SerializedName;

public class Articles {
    @SerializedName("ctime")
    public String time;

    public String title;

    public String description;

    public String picUrl;

    public String url;

    public String nickname;
}
