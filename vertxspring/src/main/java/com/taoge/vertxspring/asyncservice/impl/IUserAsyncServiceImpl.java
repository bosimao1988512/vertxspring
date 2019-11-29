package com.taoge.vertxspring.asyncservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taoge.vertxspring.asyncservice.IUserAsyncService;
import com.taoge.vertxspring.entity.User;
import com.taoge.vertxspring.service.IUserService;
import com.taoge.vertxspring.utils.annotation.AsyncServiceHandler;
import com.taoge.vertxspring.utils.vertx.utils.BaseAsyncService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AsyncServiceHandler
public class IUserAsyncServiceImpl implements IUserAsyncService, BaseAsyncService {
    @Autowired
    IUserService iUerService;

    @Override
    public void listUsers(User user, Handler<AsyncResult<List<User>>> resultHandler) {
        try {
            List<User> userList = iUerService.list(new QueryWrapper<>(user));
            Future.succeededFuture(userList).setHandler(resultHandler);
        } catch (Exception e) {
            e.printStackTrace();
            resultHandler.handle(Future.failedFuture(e));
        }
    }
}
