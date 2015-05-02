package com.kyxw007.kftpserver.ftp.command.handlers;

import com.kyxw007.kftpserver.ftp.controller.FtpContext;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by kyxw007 on 15/5/2.
 */
public class CwdCommandHandler implements CommandHandler {
    private FtpContext ftpContext;
    private static Logger logger = Logger.getLogger("KFTP:");

    public CwdCommandHandler(FtpContext ftpContext) {
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
            String str = cmd.substring(3).trim();
            if ("".equals(str)) {
                pw.println("250 Broken client detected, missing argument to CWD. " + ftpContext.getPdir() + " is current directory.");
                pw.flush();
                logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> 250 Broken client detected, missing argument to CWD. " + ftpContext.getPdir() + " is current directory.");
            } else {
                //判断目录是否存在
                String pdir = ftpContext.getPdir().trim();
                String dir = ftpContext.getDir().trim();
//                System.out.println("########str:"+str+"<-");
//                System.out.println("#######pdir:"+pdir+"<-");
//                System.out.println("########dir:"+dir+"<-");
                String tmpDir = null;
                if ("/".equals(pdir)){
                    tmpDir = dir+pdir+str;
                }else {
                    tmpDir = dir + pdir +"/"+ str;
                }
                tmpDir = tmpDir.replace("//","/");
//                System.out.println("###########tempdir:" + tmpDir);
                File file = new File(tmpDir);
                if (file.exists()) {//目录存在
                    if ("..".equals(str)){
                        tmpDir=tmpDir.substring(dir.length());
                        tmpDir=tmpDir.substring(0, tmpDir.lastIndexOf("/"));
                        tmpDir=tmpDir.substring(0, tmpDir.lastIndexOf("/"));
                        if (tmpDir.isEmpty()){
                            tmpDir = "/";
                        }
                        ftpContext.setPdir(tmpDir);
                    }else {
                        ftpContext.setPdir(tmpDir.substring(dir.length()));
                    }


                    pw.println("250 CWD successful. " + ftpContext.getPdir() + " is current directory");
                    pw.flush();
                    logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> 250 CWD successful. " + ftpContext.getPdir() + " is current directory");
                } else {//目录不存在
                    pw.println("550 CWD failed. " + ftpContext.getPdir() + ": directory not found.");
                    pw.flush();
                    logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> 550 CWD failed. " + ftpContext.getPdir() + ": directory not found.");
                }
            }

        } else {
            pw.println(ftpContext.getLOGIN_WARNING());
            pw.flush();
            logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> " + ftpContext.getLOGIN_WARNING());
        }
    }
}
