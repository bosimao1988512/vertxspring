package com.taoge.vertxspring.asyncservice;

import com.taoge.vertxspring.entity.User;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * 对原service进行vertx异步封装
 *
 */
@ProxyGen
public interface IUserAsyncService {
    void listUsers(User user, Handler<AsyncResult<List<User>>> resultHandler);

}
