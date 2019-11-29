package com.taoge.vertxspring.utils.vertx.factory;

/**
 * Created by 滔哥 on 2019/11/19
 */

import com.taoge.vertxspring.utils.annotation.RouteHandler;
import com.taoge.vertxspring.utils.annotation.RouteMapping;
import com.taoge.vertxspring.utils.annotation.RouteMethod;
import com.taoge.vertxspring.utils.common.ReflectionUtil;
import com.taoge.vertxspring.utils.vertx.utils.RouterUtil;
import io.vertx.core.Handler;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.vertx.core.http.HttpHeaders.*;

/**
 * 创建Router对象
 */
public class RouterHandlerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterHandlerFactory.class);
    private static volatile Reflections reflections;
    private static final String GATEWAY_PREFIX = "/";
    private volatile String gatewayPrefix = GATEWAY_PREFIX;

    //String... routerScanAddress
    public RouterHandlerFactory(String... routerScanAddress) {
        Objects.requireNonNull(routerScanAddress, "The router package address scan is empty.");
        reflections = ReflectionUtil.getReflections(routerScanAddress);
    }

    public RouterHandlerFactory(String routerScanAddress, String gatewayPrefix) {
        Objects.requireNonNull(routerScanAddress, "The router package address scan is empty.");
        reflections = ReflectionUtil.getReflections(routerScanAddress);
        this.gatewayPrefix = gatewayPrefix;
    }

    /**
     * 扫描路由router并注册处理器handler
     */
    public Router createRouter() {
        Router router = RouterUtil.getRouter();
        router.route().handler(ctx -> {
            //设置header
            LOGGER.debug("The HTTP service request address information ===>path:{}, uri:{}, method:{}",
                    ctx.request().path(), ctx.request().absoluteURI(), ctx.request().method());
            ctx.response().headers().add(CONTENT_TYPE, "application/json; charset=utf-8");
            ctx.response().headers().add(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            ctx.response().headers().add(ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, PUT, DELETE, HEAD");
            ctx.response().headers().add(ACCESS_CONTROL_ALLOW_HEADERS,
                    "X-PINGOTHER, Origin,Content-Type, Accept, X-Requested-With, Dev, Authorization, Version, Token");
            ctx.response().headers().add(ACCESS_CONTROL_MAX_AGE, "1728000");
            ctx.next();
        });

        Set<HttpMethod> method = new HashSet<HttpMethod>() {{
            add(HttpMethod.GET);
            add(HttpMethod.POST);
            add(HttpMethod.OPTIONS);
            add(HttpMethod.PUT);
            add(HttpMethod.DELETE);
            add(HttpMethod.HEAD);
        }};
        /* 添加跨域的方法 **/
        router.route().handler(CorsHandler.create("*").allowedMethods(method));
        router.route().handler(BodyHandler.create());

        //扫描处理器handler并注册到路由router，路由地址相同则比对两个路由权重顺序
        List<Class<?>> sortedHandlers = reflections.getTypesAnnotatedWith(RouteHandler.class).stream().sorted((m1, m2) -> {
            RouteHandler mapping1 = m1.getAnnotation(RouteHandler.class);
            RouteHandler mapping2 = m2.getAnnotation(RouteHandler.class);
            return Integer.compare(mapping2.order(), mapping1.order());
        }).collect(Collectors.toList());
        for (Class<?> handler : sortedHandlers) {
            registerNewHandler(router, handler);
        }
        return router;
    }

    /**
     * 映射路由router到处理器handler
     */
    private void registerNewHandler(Router router, Class<?> handler) {
        //默认api前缀
        String root=gatewayPrefix;
        if (!root.startsWith("/")) {
            root = "/" + root;
        }
        if (!root.endsWith("/")) {
            root = root + "/";
        }

        // 扫描RouteHandler注解的类
        if (handler.isAnnotationPresent(RouteHandler.class)) {
            RouteHandler routeHandler = handler.getAnnotation(RouteHandler.class);
            root = root + routeHandler.value();
        }
		
        List<Method> methodList = Stream.of(handler.getMethods()).filter(method -> method.isAnnotationPresent(RouteMapping.class)).sorted((m1, m2) -> {
            RouteMapping mapping1 = m1.getAnnotation(RouteMapping.class);
            RouteMapping mapping2 = m2.getAnnotation(RouteMapping.class);
            return Integer.compare(mapping2.order(), mapping1.order());
        }).collect(Collectors.toList());

        try {
            Object instance = handler.newInstance();
            for (Method method : methodList) {
                if (method.isAnnotationPresent(RouteMapping.class)) {
                    RouteMapping mapping = method.getAnnotation(RouteMapping.class);
                    RouteMethod routeMethod = mapping.method();
                    String routeUrl;
                    if (mapping.value().startsWith("/:")) {
                        routeUrl = method.getName() + mapping.value();// 注意
                    } else {
                        routeUrl = (mapping.value().endsWith(method.getName()) ?
                                mapping.value() : (mapping.isCover() ? mapping.value() : mapping.value() + method.getName()));
                        if (routeUrl.startsWith("/")) {
                            routeUrl = routeUrl.substring(1);
                        }
                    }
                    String url = root.endsWith("/") ? root.concat(routeUrl) : root.concat("/" + routeUrl);

                    Handler<RoutingContext> methodHandler = (Handler<RoutingContext>) method.invoke(instance);
                    LOGGER.debug("Register New Handler -> {}:{}", routeMethod, url);
                    switch (routeMethod) {
                        case GET:
                            router.get(url).handler(methodHandler);
                            break;
                        case POST:
                            router.post(url).handler(methodHandler);
                            break;
                        case PUT:
                            router.put(url).handler(methodHandler);
                            break;
                        case DELETE:
                            router.delete(url).handler(methodHandler);
                            break;
                        case ROUTE:
                            router.route(url).handler(methodHandler); // get、post、delete、put
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LOGGER.error("Obtain Handler Fail，Error details：{}", ex.getMessage());
        }
    }
}
