package com.taoge.vertxspring.utils.vertx.utils;

import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ServiceProxyBuilder;

public class AsyncServiceInstanceUtil {

    public static <T> T getAsyncServiceInstance(Class<T> asClazz, Vertx vertx) {
        return new ServiceProxyBuilder(vertx).setAddress(asClazz.getName()).build(asClazz);
    }

    public static <T> T getAsyncServiceInstance(Class<T> asClazz) {
        return new ServiceProxyBuilder(VertxUtil.getVertxInstance()).setAddress(asClazz.getName()).build(asClazz);
    }
}
