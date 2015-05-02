package com.kyxw007.kftpserver.ftp.controller;

import com.kyxw007.kftpserver.ftp.command.handlers.CommandHandler;
import com.kyxw007.kftpserver.ftp.command.CommandHandlerFactory;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by kyxw007 on 15/5/1.
 */
public class FtpListenThread extends Thread {

    Logger logger = Logger.getLogger("KFTP:");
    private FtpContext ftpContext = new FtpContext();

    FtpListenThread(Socket client, String F_DIR) {
        this.ftpContext.setSocketClient(client);
        this.ftpContext.setDir(F_DIR);
    }

    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = ftpContext.getSocketClient().getInputStream();
            os = ftpContext.getSocketClient().getOutputStream();
        } catch (IOException e) {
            logger.error("socket 出错");
        }
        PrintStream pw = new PrintStream(os);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        ftpContext.setClientIp(ftpContext.getSocketClient().getInetAddress().toString().substring(1));//记录客户端IP

        String command = null;

        //打印欢迎信息
        pw.println("220-Welcome To Rumpus!");
        pw.println("220 Service ready for new user");
        pw.flush();
        logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")>  welcome message..Connected, sending.");
        logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")>  220-FTP Server A version 1.0 written by Leon Guo");

        boolean b = true;
        while (b) {
            try {
                command = br.readLine();
                //获取用户输入的命令
                logger.info("CMD:" + command);
                if (null == command) break;
            } catch (IOException e) {
                pw.println("331 Failed to get command");
                pw.flush();
                logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")>  331 Failed to get command");
                logger.error("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> " + e.getMessage());
                for (StackTraceElement ste : e.getStackTrace()) {
                    logger.error(ste.toString());
                }
                b = false;
            }
            CommandHandler handler = CommandHandlerFactory.createCommandHandler(command, ftpContext);
            handler.execute(command);
        }

    }
}
