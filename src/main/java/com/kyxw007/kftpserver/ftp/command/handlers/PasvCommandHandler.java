package com.kyxw007.kftpserver.ftp.command.handlers;

import com.kyxw007.kftpserver.ftp.controller.FtpContext;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SocketServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by kyxw007 on 15/5/2.
 */
public class PasvCommandHandler implements CommandHandler {

    private FtpContext ftpContext;
    private static Logger logger = Logger.getLogger("KFTP:");

    public PasvCommandHandler(FtpContext ftpContext) {
        this.ftpContext = ftpContext;
    }

    @Override
    public void execute(String cmd) {

        logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> " + cmd);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(ftpContext.getSocketClient().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ftpContext.isLogin()) {
            ServerSocket ss = null;
            while (true) {
                //获取服务器空闲端口
                ftpContext.setPort_high();
                ftpContext.setPort_low();
                int porttem  = 0;
                try {
                    porttem = (ftpContext.getPort_high() *256)+ftpContext.getPort_low();
                    ss = new ServerSocket(porttem);
                    System.out.println("##################PORT:"+porttem);
                    break;
                } catch (IOException e) {
                    continue;
                }
            }
            InetAddress i = null;
            try {
                i = InetAddress.getLocalHost();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
            pw.println("227 Entering Passive Mode ("+i.getHostAddress().replace(".", ",")+","+ftpContext.getPort_high()+","+ftpContext.getPort_low()+")");
            pw.flush();
            logger.info("(" + ftpContext.getUsername()  + ") (" + ftpContext.getClientIp() + ")> 227 Entering Passive Mode (" + i.getHostAddress().replace(".", ",") + "," + ftpContext.getPort_high() + "," + ftpContext.getPort_low() + ")");
            try {
                //被动模式下的socket
                ftpContext.setTempsocket(ss.accept());
                ss.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
                for(StackTraceElement ste : e.getStackTrace()){
                    logger.error(ste.toString());
                }
            }

        }else {
            pw.println(ftpContext.getLOGIN_WARNING());
            pw.flush();
            logger.info("("+ftpContext.getUsername() +") ("+ftpContext.getClientIp()+")> "+ftpContext.getLOGIN_WARNING());
        }


    }
}
