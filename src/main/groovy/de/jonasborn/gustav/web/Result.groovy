package de.jonasborn.gustav.web

import com.google.gson.Gson
import io.javalin.http.Context

class Result {

    static Gson gson = new Gson()

    int status
    String message
    Object value

    Result(int status, String message, Object value) {
        this.status = status
        this.message = message
        this.value = value
    }

    public void into(Context context) {
        context.contentType("application/json")
        context.result(gson.toJson(this))
    }
}
