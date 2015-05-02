package com.kyxw007.kftpserver.ftp.command;

import com.kyxw007.kftpserver.ftp.command.handlers.*;
import com.kyxw007.kftpserver.ftp.controller.FtpContext;
import org.apache.log4j.Logger;

/**
 * Created by kyxw007 on 15/5/2.
 */
public class CommandHandlerFactory {
    private static Logger logger = Logger.getLogger("KFTP:");
    public static CommandHandler createCommandHandler(String cmd,FtpContext ftpContext){
        String code = null;
        int index = cmd.indexOf(" ");
        if (index!=-1){
            code = cmd.substring(0, index).toUpperCase();
        }else {
            code = cmd.toUpperCase();
        }
       // System.out.println(code);

        CommandHandler handler = null;
        switch (code){
            case "AUTH":
                handler = new AuthCommandHandler(ftpContext);
                break;
            case "USER":
                handler = new UserCommandHandler(ftpContext);
                break;
            case "PASS":
                handler = new PassCommandHandler(ftpContext);
                break;
            case "SYST":
                handler = new SYSTCommandHandler(ftpContext);
                break;
            case "PWD":
                handler = new PwdCommandHandler(ftpContext);
                break;
            case "TYPE":
                handler = new TypeCommandHandler(ftpContext);
                break;
            case "PASV":
                handler = new PasvCommandHandler(ftpContext);
                break;
            case "LIST":
                handler = new ListCommandHandler(ftpContext);
                break;
            case "CWD":
                handler = new CwdCommandHandler(ftpContext);
                break;
            case "RETR":
                handler = new RetrCommandHandler(ftpContext);
                break;
            case "STOR":
                handler = new StoreCommandHandler(ftpContext);
                break;
            default:
                handler = new DefautCommandHandler(ftpContext);
        }

        return handler;
    }
}
