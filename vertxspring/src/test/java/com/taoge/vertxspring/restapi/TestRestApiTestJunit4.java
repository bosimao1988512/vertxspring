package com.taoge.vertxspring.restapi;

import com.taoge.vertxspring.utils.vertx.factory.RouterHandlerFactory;
import com.taoge.vertxspring.utils.vertx.utils.VertxUtil;
import com.taoge.vertxspring.utils.vertx.verticle.AsyncRegistVerticle;
import com.taoge.vertxspring.utils.vertx.verticle.RouterRegistryVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

/**
 * TestRestApi Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十一月 20, 2019</pre>
 */
@RunWith(VertxUnitRunner.class)
@SpringBootTest
public class TestRestApiTestJunit4 {

    private Vertx vertx;

    @Before
    public void before(TestContext context) throws Exception {

        /*vertx=  Vertx.vertx();
        VertxUtil.init(vertx);
        Router router = new RouterHandlerFactory("com.taoge.vertxspring.restapi").createRouter();
        // REST
        vertx.deployVerticle(new RouterRegistryVerticle(router, 8089),context.asyncAssertSuccess());
        // 异步服务
        vertx.deployVerticle(new AsyncRegistVerticle("com.taoge.vertxspring.asyncservice.impl"), new DeploymentOptions().setWorker(true),
                context.asyncAssertSuccess());*/
    }

    @After
    public void after(TestContext context) throws Exception {
        /*vertx.close(context.asyncAssertSuccess());*/
    }

    /**
     * Method: myTest()
     */
    @Test
    public void testMyTest(TestContext context) throws Exception {
        final Async async = context.async();
        vertx=Vertx.vertx();
        /*WebClient client = WebClient.create(vertx);
        client.get(8989, "localhost", "/myTest/1").send(
                ar -> {
                    if (ar.succeeded()) {
                        // 获取响应
                        HttpResponse<Buffer> response = ar.result();

                        System.err.println("Received response with status code" + response.statusCode());
                        System.err.println(response.bodyAsString());
                    } else {
                        System.err.println("Something went wrong " + ar.cause().getMessage());
                    }
                    async.complete();

                }
        );*/

        vertx.createHttpClient().get(8989, "127.0.0.1", "/myTest/1", response -> {
            response.result().body(System.err::println);
            //context.assertTrue(response.toString().contains("Hello"));
            async.complete();
        }).end();
        //async.awaitSuccess(5000);
    }


} 
