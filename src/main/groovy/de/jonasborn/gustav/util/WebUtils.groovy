package de.jonasborn.gustav.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.mashape.unirest.http.Unirest

class WebUtils {

    static Gson gson = new Gson()

    public static JsonObject get(String url, Map<String, Object> params = [:]) {
        url = url + "?" + params.collect {it.key + "=" + it.value}.join("&")
        println url
        jo(Unirest.get(url).asString().body)
    }

    public static JsonObject post(String url, Map<String, Object> map = [:]) {
        println url
        jo(Unirest.post(url).body(gson.toJson(map)).asString().body)
    }

    private static JsonObject jo(String s) {
        return gson.fromJson(s, JsonObject.class)
    }

    private static String join(String...params) {
        params.join("")
    }

}
