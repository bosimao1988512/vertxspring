package com.taoge.vertxspring.restapi;

import com.taoge.vertxspring.utils.annotation.RouteHandler;
import com.taoge.vertxspring.utils.annotation.RouteMapping;
import com.taoge.vertxspring.utils.annotation.RouteMethod;
import com.taoge.vertxspring.utils.resultvo.ResultBean;
import com.taoge.vertxspring.utils.resultvo.ResultConstant;
import com.taoge.vertxspring.utils.vertx.utils.HttpUtil;
import com.taoge.vertxspring.utils.vertx.utils.VertxUtil;
import io.vertx.core.Handler;
import io.vertx.core.file.FileSystem;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

import java.util.Set;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by 滔哥 on 2019/11/19
 */
@RouteHandler
public class UploadAndDownRestApi {

    /**
     * 文件上传
     *
     * @return
     */
    @RouteMapping(value = "/upload", method = RouteMethod.POST)
    public Handler<RoutingContext> upload() {
        return ctx -> {
            Set<FileUpload> uploads = ctx.fileUploads();
            FileSystem fs = VertxUtil.getVertxInstance().fileSystem();
            uploads.forEach(fileUpload -> {
                String path = "D:/upload/" + fileUpload.fileName();
                fs.copy(fileUpload.uploadedFileName(), path, ar -> {
                    if (ar.succeeded()) {
                        fs.deleteBlocking(fileUpload.uploadedFileName());
                    }
                });
            });
            HttpUtil.jsonResponse(ctx.response(), HTTP_OK, ResultBean.build().setResultConstant(ResultConstant._000));
        };
    }

    /**
     * 文件下载
     * @return
     */
    @RouteMapping(value = "/down", method = RouteMethod.GET)
    public Handler<RoutingContext> download(){
        return ctx->{
            String filePath="D:/upload/osi七层.png";
            ctx.response().putHeader("content-Type", "application/x-png");
            ctx.response().putHeader("Content-Disposition", "attachment;filename="+"hahaha.png");
            ctx.response().sendFile(filePath);
        };
    }
}
