package com.taoge.vertxspring.utils.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 路由发布
 */
public class RouterRegistryVerticle extends AbstractVerticle {

    private static final Logger LOGGER= LoggerFactory.getLogger(RouterRegistryVerticle.class);
    // 前端发送给后端数据的最大值，默认值65536 (64KB)
    private static final int MAX_WEBSOCKET_FRAME_SIZE = 65536;
    private static final int HTTP_PORT = 8080;
    private int port = HTTP_PORT;
    private HttpServer server;
    private Router router;

    public RouterRegistryVerticle(Router router) {
        this.router = router;
    }

    public RouterRegistryVerticle(Router router, int port) {
        this.router = router;
        if (port > 0) {
            this.port = port;
        }
    }

    @Override
    public void start(Promise<Void> startFuture) throws Exception {
        LOGGER.debug("To start listening to port {} ......", port);
        super.start();
        HttpServerOptions options = new HttpServerOptions().setMaxWebsocketFrameSize(MAX_WEBSOCKET_FRAME_SIZE).setPort(port);
        server = vertx.createHttpServer(options);
        server.requestHandler(router::handle);
        server.listen(result -> {
            if (result.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(result.cause());
            }
        });
    }

    @Override
    public void stop(Promise<Void> stopFuture) throws Exception {
        super.stop();
        if (server == null) {
            stopFuture.complete();
            return;
        }
        server.close(result -> {
            if (result.failed()) {
                stopFuture.fail(result.cause());
            } else {
                stopFuture.complete();
            }
        });
    }
}
