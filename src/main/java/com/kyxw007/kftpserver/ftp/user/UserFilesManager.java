package com.kyxw007.kftpserver.ftp.user;

import com.kyxw007.kftpserver.ftp.controller.FtpContext;
import com.kyxw007.kftpserver.ftp.fileio.FTPFile;
import com.kyxw007.kftpserver.ftp.fileio.FileUtilTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyxw007 on 15/5/2.
 */
public class UserFilesManager {
    public List<FTPFile> listUserFiles(FtpContext ftpContext){
        String dir = ftpContext.getDir();
        String pdir = ftpContext.getPdir();
        String path = dir + pdir;
        System.out.println("dir:::::::"+path);
        return FileUtilTools.listFile(path);
    }
}
