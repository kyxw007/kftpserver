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
public class UserCommandHandler implements CommandHandler {
    private FtpContext ftpContext;
    private static Logger logger = Logger.getLogger("KFTP:");

    public UserCommandHandler(FtpContext ftpContext) {
        this.ftpContext = ftpContext;
    }

    @Override
    public void execute(String cmd) {
        try {
            OutputStream os = ftpContext.getSocketClient().getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            String username = cmd.substring(4).trim();
            FtpUserAuthorize fua = new FtpUserAuthorize();
            if(fua.userExists(username)){

                pw.println("331 Password required for kyxw007");
                logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> 331 Password required for kyxw007");
                ftpContext.setTempUsername(username);
                pw.flush();
            }else {
                pw.println("501 Syntax error");
                pw.flush();
                logger.info("(not logged in) ("+ftpContext.getClientIp()+")> 501 Syntax error");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
    }
}
