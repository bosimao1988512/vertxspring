package com.taoge.vertxspring.restapi;

import com.taoge.vertxspring.asyncservice.IUserAsyncService;
import com.taoge.vertxspring.entity.User;
import com.taoge.vertxspring.utils.annotation.RouteHandler;
import com.taoge.vertxspring.utils.annotation.RouteMapping;
import com.taoge.vertxspring.utils.annotation.RouteMethod;
import com.taoge.vertxspring.utils.resultvo.ResultBean;
import com.taoge.vertxspring.utils.resultvo.ResultConstant;
import com.taoge.vertxspring.utils.vertx.utils.HttpUtil;
import com.taoge.vertxspring.utils.vertx.utils.ParamUtil;
import com.taoge.vertxspring.utils.vertx.utils.VertxUtil;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.serviceproxy.ServiceProxyBuilder;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 滔哥
 * @since 2019-11-19
 */
@RouteHandler("user")
public class UserRestApi {

    private UserAsyncService userAsyncService = AsyncServiceUtil.getAsyncServiceInstance(UserAsyncService.class);

    /**
     * 演示路径参数
     *
     * @return
     */
    @RouteMapping(value = "/test/:id", method = RouteMethod.GET)
    public Handler<RoutingContext> myTest() {
        return ctx -> {
            JsonObject param = ParamUtil.getRequest(ctx);
            HttpUtil.jsonResponse(ctx.response(), HTTP_OK, ResultBean.build().setResultConstant(ResultConstant._000, param.encode()));
        };
    }


    /**
     * 演示服务调用
     *
     * @return
     */
    @RouteMapping(value = "/listUsers", method = RouteMethod.POST)
    public Handler<RoutingContext> listUsers() {
        return ctx -> {
            JsonObject param = ParamUtil.getRequest(ctx);
            if (param.containsKey("age")) {
                param.put("age", Integer.valueOf(param.getValue("age").toString()));
            }
            User user = new User(param);
            userAsyncService.listUsers(user, ar -> {
                if (ar.succeeded()) {
                    List<User> userList = ar.result();
                    HttpUtil.jsonResponse(ctx.response(), HTTP_OK, ResultBean.build().setResultConstant(ResultConstant._000, userList));
                } else {
                    HttpUtil.jsonResponse(ctx.response(), HTTP_INTERNAL_ERROR,
                            ResultBean.build().setData(ar.cause().getMessage()).setCode(String.valueOf(HTTP_INTERNAL_ERROR)));
                    ar.cause().printStackTrace();
                }
            });
        };
    }
}
