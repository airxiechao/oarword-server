package com.airxiechao.oarword.doc;

import com.airxiechao.axcboot.annotation.Description;
import com.airxiechao.axcboot.communication.common.Response;
import com.airxiechao.axcboot.communication.common.annotation.Param;
import com.airxiechao.axcboot.communication.rest.annotation.Get;
import com.airxiechao.axcboot.communication.rest.annotation.Post;
import com.airxiechao.axcboot.communication.rest.util.RestUtil;
import io.undertow.server.HttpServerExchange;

import java.nio.file.*;

public class DocRestHandler {

    @Description("载入文档")
    @Get("/doc/load")
    @Param(value = "name", required = true)
    public static Response loadDoc(HttpServerExchange exchange) throws Exception {
        String name = RestUtil.queryStringParam(exchange, "name");
        Path path = Paths.get("doc", name+".json").normalize();
        if(!path.startsWith(Paths.get("doc"))){
            throw new Exception("invalid name");
        }
        if(!Files.exists(path)){
            throw new Exception("no doc");
        }

        String doc = Files.readString(path);
        return new Response().data(doc);
    }

    @Description("保存文档")
    @Post("/doc/save")
    @Param(value = "name", required = true)
    @Param(value = "doc", required = true)
    public static Response saveDoc(HttpServerExchange exchange) throws Exception {
        String name = RestUtil.formStringData(exchange, "name");
        String doc = RestUtil.formStringData(exchange, "doc");
        Path path = Paths.get("doc", name+".json");
        if(!path.startsWith(Paths.get("doc"))){
            throw new Exception("invalid name");
        }
        if(!Files.exists(path)){
            throw new Exception("no doc");
        }

        Files.writeString(path, doc, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return new Response();
    }
}
