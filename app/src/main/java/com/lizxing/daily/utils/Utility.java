package com.lizxing.daily.utils;

import com.google.gson.Gson;
import com.lizxing.daily.gson.Articles;
import com.lizxing.daily.gson.ArticlesList;
import com.lizxing.daily.gson.English;
import com.lizxing.daily.gson.EnglishList;
import com.lizxing.daily.gson.HistoryList;
import com.lizxing.daily.gson.NewsList;
import com.lizxing.daily.gson.PoemsList;
import com.lizxing.daily.gson.QuotesList;
import com.lizxing.daily.gson.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

public class Utility {
    /**
     * 天气相关
     * 将返回的JSON解析
     */
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新闻相关
     * 将返回的JSON解析
     */
    public static NewsList handleNewsResponse(final String requestText){
        return new Gson().fromJson(requestText, NewsList.class);
    }

    /**
     * 英语相关
     * 将返回的JSON解析
     */
    public static EnglishList handleEnglishResponse(final String requestText){
        return new Gson().fromJson(requestText, EnglishList.class);
    }

    /**
     * 文章相关
     * 将返回的JSON解析
     */
    public static ArticlesList handleArticlesResponse(final String requestText){
        return new Gson().fromJson(requestText, ArticlesList.class);
    }

    /**
     * 历史相关
     * 将返回的JSON解析
     */
    public static HistoryList handleHistoryResponse(final String requestText){
        return new Gson().fromJson(requestText, HistoryList.class);
    }

    /**
     * 名言相关
     * 将返回的JSON解析
     */
    public static QuotesList handleQuotesResponse(final String requestText){
        return new Gson().fromJson(requestText, QuotesList.class);
    }

    /**
     * 诗词相关
     * 将返回的JSON解析
     */
    public static PoemsList handlePoemsResponse(final String requestText){
        return new Gson().fromJson(requestText, PoemsList.class);
    }
}


