package com.kyxw007.kftpserver.ftp.command.handlers;

import com.kyxw007.kftpserver.ftp.controller.FtpContext;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by kyxw007 on 15/5/2.
 */
public class TypeCommandHandler implements CommandHandler {
    private FtpContext ftpContext;
    private static Logger logger = Logger.getLogger("KFTP:");

    public TypeCommandHandler(FtpContext ftpContext) {
        this.ftpContext = ftpContext;
    }

    @Override
    public void execute(String cmd) {
        try {
            OutputStream os = ftpContext.getSocketClient().getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            String type = cmd.substring(4).trim();
            switch (type){
                case "I":
                    ftpContext.setType(FtpContext.Type.type.I);
                    break;
                case "A":
                    ftpContext.setType(FtpContext.Type.type.A);
                    break;
                case "E":
                    ftpContext.setType(FtpContext.Type.E);
                    break;
                case "L":
                    ftpContext.setType(FtpContext.Type.L);
                    break;

            }
            pw.println("200 Type set to "+type);
            logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> 200 Type set to " + type);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }

    }
}
