package com.taoge.vertxspring.restapi;

import com.taoge.vertxspring.utils.vertx.factory.RouterHandlerFactory;
import com.taoge.vertxspring.utils.vertx.utils.VertxUtil;
import com.taoge.vertxspring.utils.vertx.verticle.AsyncRegistVerticle;
import com.taoge.vertxspring.utils.vertx.verticle.RouterRegistryVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TestRestApi Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十一月 20, 2019</pre>
 */
@SpringBootTest
public class TestRestApiTestJunit5 {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestRestApiTestJunit5.class);

    private Vertx vertx;

    @BeforeEach
    public void before() throws Exception {
        vertx=VertxUtil.getVertxInstance();
        Router router = new RouterHandlerFactory("com.taoge.vertxspring.restapi").createRouter();
        // REST
        vertx.deployVerticle(new RouterRegistryVerticle(router, 8089));
        // 异步服务
        vertx.deployVerticle(new AsyncRegistVerticle("com.taoge.vertxspring.asyncservice.impl"), new DeploymentOptions().setWorker(true));
    }

    @AfterEach
    public void after() throws Exception {
        vertx.close();
    }

    /**
     * Method: myFilter()
     */
    @Test
    public void testMyFilter() throws Exception {
        //final Async async = context.async();
        vertx.createHttpClient().getNow(8089, "localhost", "/myTest/1", response -> {
            System.err.println(response.toString());
            LOGGER.error(response.toString());

            //context.assertTrue(response.toString().contains("Hello"));
            //async.complete();
        });
    }

    /**
     * Method: myTest()
     */
    @Test
    public void testMyTest() throws Exception {
//TODO: Test goes here... 
    }


} 
