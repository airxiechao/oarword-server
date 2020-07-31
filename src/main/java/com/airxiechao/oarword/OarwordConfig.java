package com.airxiechao.oarword;

import com.airxiechao.axcboot.util.StringUtil;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class OarwordConfig {

    private static final String CONF_FILE = "oarword.conf";
    private static final Config config = ConfigFactory.load(CONF_FILE);

    public static String getRestServerBasePath(){
        String basePath = config.getString("oarword.rest.server.basePath");
        if(StringUtil.isBlank(basePath) || "/".equals(basePath)){
            basePath = "";
        }else{
            if(!basePath.startsWith("/")){
                basePath = "/" + basePath;
            }
            if(basePath.endsWith("/")){
                basePath = basePath.substring(0, basePath.length() - 1);
            }
        }

        return basePath;
    }

    public static int getRestServerPort() {
        return config.getInt("oarword.rest.server.port");
    }

    public static String getRestServerUiDir(){
        return config.getString("oarword.rest.server.uiDir");
    }

}
