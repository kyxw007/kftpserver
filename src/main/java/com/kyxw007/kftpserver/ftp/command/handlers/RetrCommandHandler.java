package com.kyxw007.kftpserver.ftp.command.handlers;

import com.kyxw007.kftpserver.ftp.controller.FtpContext;
import com.kyxw007.kftpserver.util.UtilTools;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by kyxw007 on 15/5/3.
 */
public class RetrCommandHandler implements CommandHandler {
    private FtpContext ftpContext;
    private static Logger logger = Logger.getLogger("KFTP:");

    public RetrCommandHandler(FtpContext ftpContext) {
        this.ftpContext = ftpContext;
    }

    @Override
    public void execute(String cmd) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(ftpContext.getSocketClient().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ftpContext.isLogin()) {

            String path = ftpContext.getDir() + ftpContext.getPdir() + "/" + cmd.substring(4).trim();
            path = path.replace("//", "/").trim();
            File file = new File(path);
            if (file.exists()) {
                pw.println("150 Opening data channel for file transfer.");
                pw.flush();
                logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> 150 Opening data channel for file transfer.");
                try {
                    OutputStream os = ftpContext.getTempsocket().getOutputStream();
                    byte[] tempByte = new byte[1024];
                    FileInputStream fis = new FileInputStream(file);
                    int length;
                    while ((length=fis.read(tempByte))!=-1){
                        os.write(tempByte,0,length);
                    }
                    UtilTools.close(fis);
                    UtilTools.close(os);
                    UtilTools.close(ftpContext.getTempsocket());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pw.println("226 Transfer OK");
                pw.flush();
                logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> 226 Transfer OK");
            } else {
                pw.println("550 " + ftpContext.getPdir() + " : file not found.");
                pw.flush();
                logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> 550 " + ftpContext.getPdir() + " : file not found.");
            }
        } else {
            pw.println(ftpContext.getLOGIN_WARNING());
            pw.flush();
            logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> " + ftpContext.getLOGIN_WARNING());

        }
    }
}
