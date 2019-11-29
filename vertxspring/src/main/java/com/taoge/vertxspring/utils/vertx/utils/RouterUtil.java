package com.taoge.vertxspring.utils.vertx.utils;

import io.vertx.ext.web.Router;

/**
 * 全局router单例
 */
public final class RouterUtil {

    private static Router router;

    private RouterUtil() {
    }

    public static Router getRouter() {
        if (null == router) {
            router = Router.router(VertxUtil.getVertxInstance());
        }
        return router;
    }
}
