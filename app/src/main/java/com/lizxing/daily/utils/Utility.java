package com.lizxing.daily.utils;

import com.google.gson.Gson;
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

}


