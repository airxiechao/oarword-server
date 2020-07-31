package com.airxiechao.oarword;

import com.airxiechao.axcboot.process.threadpool.ThreadPoolManager;
import com.airxiechao.axcboot.task.ScheduleTaskManager;
import com.airxiechao.oarword.rest.OarwordRestServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Oarword {

    private static final Logger logger = LoggerFactory.getLogger(Oarword.class);

    public static void main(String[] args){
        Oarword oarword = new Oarword();
        oarword.start();
    }
    /**
     * 启动
     */
    public void start(){
        logger.info("● oarword start");

        OarwordRestServer.getInstance().start();
    }

    /**
     * 停止
     */
    public void stop(){

        OarwordRestServer.getInstance().stop();

        // 关闭线程池
        ThreadPoolManager.getInstance().shutdownAll();

        // 关闭定时任务
        ScheduleTaskManager.getInstance().shutdownAll();
    }
}
