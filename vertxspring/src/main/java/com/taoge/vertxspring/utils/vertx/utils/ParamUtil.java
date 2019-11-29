package com.taoge.vertxspring.utils.vertx.utils;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 参数 工具类
 */
public final class ParamUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamUtil.class);


    /**
     * 请求参数转换
     *
     * @param ctx 根据vertx-web的上下文来获取参数
     */
    public static JsonObject getRequest(RoutingContext ctx) {
        MultiMap paramMap = ctx.request().params();
        JsonObject param = new JsonObject();
        //ip
        //params.put("serverIp", ctx.request().localAddress().host());
        //params.put("clientIp", ctx.request().remoteAddress().host());
        if (!paramMap.isEmpty()) {
            Iterator iter = paramMap.entries().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                if (param.containsKey(entry.getKey())) {//多值
                    if (!(param.getValue(entry.getKey()) instanceof JsonArray)) {
                        List<String> arry = paramMap.getAll(entry.getKey());
                        for (int i = 0; i < arry.size(); i++) {
                            arry.set(i, arry.get(i));
                        }
                        param.put(entry.getKey(), arry);
                    }
                } else {
                    param.put(entry.getKey(), entry.getValue());
                }
            }
        }
        if (param.isEmpty())
            LOGGER.debug("HttpServerRequest无请求参数! ");
        return getParamPage(param);
    }

    public static JsonObject getRequestBody(RoutingContext ctx){
        return ctx.getBodyAsJson();
    }

    /**
     * 默认处理分页
     *
     * @param params
     */
    private static JsonObject getParamPage(JsonObject params) {
        if (params != null) {
            if (!params.containsKey("limit")) {
                params.put("limit", 20);
            } else {
                int limit = Integer.valueOf(params.getValue("limit").toString());
                limit = limit < 0 ? 20 : limit;
                limit = limit > 50 ? 50 : limit;
                params.put("limit", limit);
            }
            if (!params.containsKey("page")) {
                params.put("page", 1);
            } else {
                int page = Integer.valueOf(params.getValue("page").toString());
                page = page < 0 ? 0 : page;
                params.put("page", page);
            }
        }
        return params;
    }


}
