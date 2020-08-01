package com.airxiechao.oarword.rest;

import com.airxiechao.axcboot.communication.rest.security.AuthPrincipal;
import com.airxiechao.axcboot.communication.rest.server.RestServer;
import com.airxiechao.oarword.OarwordConfig;
import com.airxiechao.oarword.doc.DocRestHandler;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpServerExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OarwordRestServer {

    private static final Logger logger = LoggerFactory.getLogger(OarwordRestServer.class);
    private static OarwordRestServer instance = new OarwordRestServer();
    public static OarwordRestServer getInstance() { return instance; }

    private RestServer restServer;
    private int port;

    private OarwordRestServer(){
        this.port = OarwordConfig.getRestServerPort();
        String basePath = OarwordConfig.getRestServerBasePath();
        this.restServer = new RestServer("0.0.0.0", this.port, basePath, (builder)->{
            builder.setServerOption(UndertowOptions.MAX_ENTITY_SIZE, 1024*1024L);
        }, this::hasRole);

        this.register();
    }

    public void start(){
        restServer.start();
        logger.info("agent rest server start at port [{}]", this.port);
    }

    public void stop(){
        restServer.stop();
        logger.info("agent rest server stop");
    }

    private boolean hasRole(HttpServerExchange exchange, AuthPrincipal principal, String[] roles){
        return true;
    }

    /**
     * 注册rest处理器
     */
    private void register(){

        // 注册doc处理器
        restServer.registerHandler(DocRestHandler.class);

        // 注册ui页面
        String uiDir = OarwordConfig.getRestServerUiDir();
        restServer.registerStatic("ui", uiDir, null, null, null);

    }
}
