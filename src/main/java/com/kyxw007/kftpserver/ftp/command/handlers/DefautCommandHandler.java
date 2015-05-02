package com.kyxw007.kftpserver.ftp.command.handlers;

import com.kyxw007.kftpserver.ftp.controller.FtpContext;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by kyxw007 on 15/5/2.
 */
public class DefautCommandHandler implements CommandHandler{

    private FtpContext ftpContext;
    private static Logger logger = Logger.getLogger("KFTP:");

    public DefautCommandHandler(FtpContext ftpContext){
        this.ftpContext = ftpContext;
    }

    @Override
    public void execute(String cmd) {
        try {
            OutputStream os = ftpContext.getSocketClient().getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.println("502 Unknow Command : " + cmd);
            logger.info("(" + ftpContext.getUsername()  + ") (" + ftpContext.getClientIp() + ")> 502 Unknow Command : "+cmd);
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
    }
}
