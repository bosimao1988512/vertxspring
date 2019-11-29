package com.taoge.vertxspring.utils.vertx.utils;

import com.taoge.vertxspring.utils.resultvo.ResultBean;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by 滔哥 on 2019/11/14
 */
public class HttpUtil {

    /**
     * json格式
     */
    public static void jsonResponse(HttpServerResponse response, ResultBean resultBean) {
        response.putHeader("content-type", "application/json; charset=utf-8").end(resultBean.toString());
    }
    public static void jsonResponse(HttpServerResponse response, int statusCode, ResultBean resultBean) {
        response.putHeader("content-type", "application/json; charset=utf-8").setStatusCode(statusCode).end(resultBean.toString());
    }

    /**
     * 文本数据String
     */
    public static void textResponse(RoutingContext routingContext, String text) {
        routingContext.response().putHeader("content-type", "text/html; charset=utf-8").end(text);
    }

}
