package com.taoge.vertxspring;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.taoge.vertxspring.utils.vertx.factory.RouterHandlerFactory;
import com.taoge.vertxspring.utils.vertx.utils.VertxUtil;
import com.taoge.vertxspring.utils.vertx.verticle.AsyncRegistVerticle;
import com.taoge.vertxspring.utils.vertx.verticle.DeployVertxServer;
import com.taoge.vertxspring.utils.vertx.verticle.RouterRegistryVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.ext.web.Router;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@MapperScan("com.taoge.vertxspring.mapper")
public class VertxspringApplication {

    /**
     * web api所在包路径
     */
    @Value("${rest-packages}")
    private String webApiPackages;

    /**
     * 异步服务所在包路径
     */
    @Value("${async-service-impl-packages}")
    private String asyncServiceImplPackages;

    /**
     * http服务器端口号
     */
    @Value("${http-server-port}")
    private int httpServerPort;

    /**
     * 工作线程池大小（可根据实际情况调整）
     */
    @Value("${worker-pool-size}")
    private int workerPoolSize;

    /**
     * 异步服务实例数量（建议和CPU核数相同）
     */
    @Value("${async-service-instances}")
    private int asyncServiceInstances;

    /**
     * mybatis-plus分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @EventListener
    public void deployVerticles(ApplicationReadyEvent event) {
        EventBusOptions eventBusOptions = new EventBusOptions();
        Vertx vertx = Vertx.vertx(
                new VertxOptions().setWorkerPoolSize(workerPoolSize)
                        .setEventBusOptions(eventBusOptions)
                        .setBlockedThreadCheckInterval(1000L)
                        .setMaxEventLoopExecuteTime(Long.MAX_VALUE)
        );
        VertxUtil.init(vertx);
        try {
            Router router = new RouterHandlerFactory(webApiPackages).createRouter();

            DeployVertxServer.startDeploy(router, asyncServiceImplPackages, httpServerPort, asyncServiceInstances);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(VertxspringApplication.class, args);
    }

}
