package com.lizxing.daily.items;

public class ArticlesItem {
    private String title;
    private String descr;
    private String imageUrl;
    private String uri;
    private String nickname;

    public ArticlesItem(String title, String descr, String imageUrl, String uri, String nickname){
        this.title = title;
        this.imageUrl = imageUrl;
        this.descr = descr;
        this.uri = uri;
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescr() {
        return descr;
    }

    public String getUri() {
        return uri;
    }

    public String getNickname(){
        return  nickname;
    }
}
