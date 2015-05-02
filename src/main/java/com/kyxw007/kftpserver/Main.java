package com.kyxw007.kftpserver;

import com.kyxw007.kftpserver.ftp.controller.KftpController;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by kyxw007 on 15/5/1.
 */
public class Main {
    public static void main(String[] args){
        Logger.getRootLogger();
        Logger logger = Logger.getLogger(Main.class.getName());
        PropertyConfigurator.configure("log4j.properties");
        logger.info("启动FTP服务器");
        KftpController kftp = new KftpController();
        kftp.startServer();
    }
}
