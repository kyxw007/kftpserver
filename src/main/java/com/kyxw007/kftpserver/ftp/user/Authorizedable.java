package com.kyxw007.kftpserver.ftp.user;

/**
 * Created by kyxw007 on 15/5/1.
 */
public interface Authorizedable {
    public boolean authorize(String user,String passw);
    public boolean userExists(String user);
}
