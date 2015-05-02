package com.kyxw007.kftpserver.ftp.command.handlers;

import com.kyxw007.kftpserver.ftp.controller.FtpContext;
import com.kyxw007.kftpserver.util.UtilTools;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Date;

/**
 * Created by kyxw007 on 15/5/3.
 */
public class StoreCommandHandler implements CommandHandler {
    private FtpContext ftpContext;
    private static Logger logger = Logger.getLogger("KFTP:");

    public StoreCommandHandler(FtpContext ftpContext) {
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

            pw.println("150 Opening data channel for file transfer.");
            pw.flush();
            logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> 150 Opening data channel for file transfer.");

            String path = ftpContext.getDir() + ftpContext.getPdir() + "/" + cmd.substring(4).trim();
            path = path.replace("//", "/").trim();
            File file = new File(path);

            if (file.exists()){
                String tmpName = "tmp"+new Date().getTime();
                String oldFilename = file.getName();
                file.renameTo(new File(tmpName));
                File file1 = new File(path);
                try {
                    writeFile(file1);
                } catch (IOException e) {
                    e.printStackTrace();
                    file.renameTo(new File(oldFilename));
                }
                file.delete();
            }else {
                try {
                    writeFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            pw.println("226 Transfer OK");
            pw.flush();
            logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> 226 Transfer OK");

        } else {
            pw.println(ftpContext.getLOGIN_WARNING());
            pw.flush();
            logger.info("(" + ftpContext.getUsername() + ") (" + ftpContext.getClientIp() + ")> " + ftpContext.getLOGIN_WARNING());

        }
    }

    private void writeFile(File file) throws IOException {
        InputStream is = ftpContext.getTempsocket().getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length=is.read(buffer))!=-1){
            fos.write(buffer,0,length);
        }
        UtilTools.close(fos);
        UtilTools.close(is);
    }
}
