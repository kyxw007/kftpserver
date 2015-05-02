package com.kyxw007.kftpserver.ftp.command.handlers;

import com.kyxw007.kftpserver.ftp.controller.FtpContext;
import com.kyxw007.kftpserver.ftp.fileio.FTPFile;
import com.kyxw007.kftpserver.ftp.fileio.FileUtilTools;
import com.kyxw007.kftpserver.ftp.user.UserFilesManager;
import com.kyxw007.kftpserver.util.UtilTools;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by kyxw007 on 15/5/2.
 */
public class ListCommandHandler implements CommandHandler {

    private FtpContext ftpContext;
    private static Logger logger = Logger.getLogger("KFTP:");

    public ListCommandHandler(FtpContext ftpContext) {
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
        if (ftpContext.isLogin()){
            pw.println("150 Opening data channel for directory list.");
            pw.flush();
            logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> 150 Opening data channel for directory list.");
            UserFilesManager ufm = new UserFilesManager();
            List<FTPFile> filelist = ufm.listUserFiles(ftpContext);
            PrintWriter pwr = null;
            try {
                pwr = new PrintWriter(ftpContext.getTempsocket().getOutputStream(),true);
                FileUtilTools.printFiletoOutputStream(filelist,pwr);

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                UtilTools.close(ftpContext.getTempsocket());
                UtilTools.close(pwr);
            }
            pw.println("226 Transfer OK");
            pw.flush();
            logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> 226 Transfer OK");

        }else {
            pw.println(ftpContext.getLOGIN_WARNING());
            pw.flush();
            logger.info("("+ftpContext.getUsername() +") ("+ftpContext.getClientIp()+")> "+ftpContext.getLOGIN_WARNING());
        }
    }
}
