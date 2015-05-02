package com.kyxw007.kftpserver.ftp.fileio;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by kyxw007 on 15/5/2.
 */
public class FileUtilTools {
    private static Logger logger = Logger.getLogger("KFTP:");
    public static List<FTPFile> listFile(String path){
        File fileDir = new File(path);
        if (!fileDir.exists()){
            return null;
        }
        File[] files = fileDir.listFiles();
        ArrayList<FTPFile> ftpFileList = new ArrayList<FTPFile>();
        for (File file:files){
            FTPFile ftpFile = new FTPFile();
            ftpFile.setOwner("kyxw007");
            ftpFile.setGroup("ftp");
            if (file.isDirectory()){
                ftpFile.setAuthority("drwxr-xr-x");
                ftpFile.setSize(0);
                ftpFile.setSubfileNum(file.listFiles().length);
            }else {
                ftpFile.setAuthority("-rw-r-r--1");
                ftpFile.setSize(file.length());
                ftpFile.setSubfileNum(1);
            }

            ftpFile.setDate(new Date(file.lastModified()));
            ftpFile.setName(file.getName());
            ftpFileList.add(ftpFile);
        }
        System.out.println("length:::::"+ftpFileList.size());
        return ftpFileList;
    }

    public static String parseLineForListCommand(FTPFile ftpFile){
        StringBuffer sb = new StringBuffer();
        sb.append(ftpFile.getAuthority());
        sb.append(" ");
        sb.append(ftpFile.getSubfileNum());
        sb.append(" ");
        sb.append(ftpFile.getOwner());
        sb.append(" ");
        sb.append(ftpFile.getGroup());
        sb.append(" ");
        sb.append(ftpFile.getSize());
        sb.append(" ");
        String modifyDate = new SimpleDateFormat("MMM d HH:mm", Locale.ENGLISH).format(ftpFile.getDate());
        sb.append(modifyDate);
        sb.append(" ");
        sb.append(new String(ftpFile.getName().getBytes(), Charset.forName("UTF-8")));
        return sb.toString();

    }

    public static void printFiletoOutputStream(List<FTPFile> filelist, PrintWriter pw) {
        if (filelist==null){
            pw.println("500 No such file or directory./r/n");
            pw.flush();
        }else {
            for (FTPFile ftpFile : filelist) {
                String line = parseLineForListCommand(ftpFile);
                //System.out.println(line);
                pw.println(line);
                //logger.info(parseLineForListCommand(ftpFile));
                pw.flush();
            }
            pw.println("total:" + filelist.size());
        }

    }
}
