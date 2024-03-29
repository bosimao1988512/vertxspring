package com.taoge.vertxspring.utils.vertx.utils;

import io.vertx.core.Vertx;

import java.util.Objects;

/**
 * 全局vertx单例
 */
public final class VertxUtil {

    private static Vertx singletonVertx;

    private VertxUtil() {

    }

    public static void init(Vertx vertx) {
        Objects.requireNonNull(vertx, "未初始化Vertx");
        singletonVertx = vertx;
    }

    public static Vertx getVertxInstance() {
        Objects.requireNonNull(singletonVertx, "未初始化Vertx");
        return singletonVertx;
    }
}
