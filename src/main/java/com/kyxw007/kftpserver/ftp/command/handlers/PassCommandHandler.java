package com.kyxw007.kftpserver.ftp.command.handlers;

import com.kyxw007.kftpserver.ftp.controller.FtpContext;
import com.kyxw007.kftpserver.ftp.user.FtpUserAuthorize;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by kyxw007 on 15/5/2.
 */
public class PassCommandHandler implements CommandHandler {
    private FtpContext ftpContext;
    private static Logger logger = Logger.getLogger("KFTP:");
    public PassCommandHandler(FtpContext ftpContext){
        this.ftpContext = ftpContext;
    }

    @Override
    public void execute(String cmd) {
        try {
            OutputStream os = ftpContext.getSocketClient().getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            String pass = cmd.substring(4).trim();
            FtpUserAuthorize fua = new FtpUserAuthorize();
            if (fua.authorize(ftpContext.getTempUsername(),pass)){
                ftpContext.setPass(pass);
                pw.println("230 Logged on");
                pw.flush();
                ftpContext.setUsername(ftpContext.getTempUsername());
                logger.info("("+ftpContext.getUsername()+") ("+ftpContext.getClientIp()+")> 230 Logged on");
                ftpContext.setLogin(true);
            }else {
                pw.println("530 Login or password incorrect!");
                pw.flush();
                logger.info("(" + ftpContext.getUsername()  + ") (" + ftpContext.getClientIp() + ")> 530 Login or password incorrect!");
                ftpContext.setUsername("not logged in");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
    }
}
