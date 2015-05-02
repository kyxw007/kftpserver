package com.kyxw007.kftpserver.ftp.user;

/**
 * Created by kyxw007 on 15/5/1.
 */
public class FtpUserAuthorize implements Authorizedable {
    public boolean authorize(String user, String passw) {
        if ("kyxw007".equals(user)&&"123".equals(passw)){
            return true;
        }

        return false;
    }

    public boolean userExists(String user) {
        if ("kyxw007".equals(user))
            return true;
        else
            return false;
    }

}
