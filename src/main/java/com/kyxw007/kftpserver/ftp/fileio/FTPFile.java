package com.kyxw007.kftpserver.ftp.fileio;

import java.security.acl.Group;
import java.util.Date;

/**
 * Created by kyxw007 on 4/29/15.
 */
public class FTPFile {

    private String authority;
    private int subfileNum;
    private String owner;
    private String group;

    private long size;
    private Date date;
    private String name;
    private int mode;
    private String type;
    private String perm;
    private String absPath;
    boolean exist = false;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPerm() {
        return perm;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }

    public String getAbsPath() {
        return absPath;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public int getSubfileNum() {
        return subfileNum;
    }

    public void setSubfileNum(int subfileNum) {
        this.subfileNum = subfileNum;
    }
}
