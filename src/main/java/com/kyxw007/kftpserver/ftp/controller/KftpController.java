package com.kyxw007.kftpserver.ftp.controller;

import com.kyxw007.kftpserver.util.UtilTools;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by kyxw007 on 15/5/1.
 */
public class KftpController implements FtpControlable {
    private FtpListenThread ftpListenthread;
    private final int PORT = 2100;
    private final String F_DIR = "/Users/kyxw007";
    Logger logger = Logger.getLogger("KFTP:");

    public void startServer() {
        ServerSocket s = null;
        try {
            s = new ServerSocket(PORT);
            while( true ){
                logger.info("等待请求");
                //接受客户端请求
                Socket client = s.accept();
                //创建服务线程
                new FtpListenThread(client, F_DIR).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            UtilTools.close(s);
        }




    }

    public void stopServer() {
        ftpListenthread.interrupt();
    }
}
