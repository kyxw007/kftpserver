package com.kyxw007.kftpserver.ftp.command.handlers;

import com.kyxw007.kftpserver.ftp.controller.FtpContext;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by kyxw007 on 15/5/2.
 */
public class PwdCommandHandler implements CommandHandler {
    private FtpContext ftpContext;
    private static Logger logger = Logger.getLogger("KFTP:");

    public PwdCommandHandler(FtpContext ftpContext) {
        this.ftpContext = ftpContext;
    }

    @Override
    public void execute(String cmd) {
        try {
            OutputStream os = ftpContext.getSocketClient().getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.println("257 \""+ftpContext.getPdir()+"\" is current directory.");
            logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> 257 \"" + ftpContext.getPdir() + "\" is current directory.");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
