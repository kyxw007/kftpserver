package com.kyxw007.kftpserver.ftp.controller;

import java.net.Socket;
import java.util.Random;

/**
 * Created by kyxw007 on 15/5/2.
 */
public class FtpContext {
    private final String LOGIN_WARNING = "530 Please log in with USER and PASS first.";
    private Socket socketClient;//客户端socket
    private String dir;//绝对路径
    private String pdir = "/";//相对路径
    private Random generator = new Random();//随机数
    private Type type;
    private String tempUsername ;
    private String username ="not logged in";
    private String clientIp;
    private boolean login;
    private String pass;

    private int port_high;
    private int port_low;
    private Socket tempsocket;//被动模式下的socket

    /**
     * 数据传输类型
     * A＝ASCII E＝EBCDIC I＝binary
     */
    public enum Type {
        A, E, I, type, L
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Socket getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(Socket socketClient) {
        this.socketClient = socketClient;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getPdir() {
        return pdir;
    }

    public void setPdir(String pdir) {
        this.pdir = pdir;
    }

    public Random getGenerator() {
        return generator;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getPort_low() {
        return port_low;
    }

    public void setPort_low() {
        this.port_low = 100 + generator.nextInt(1000);
    }

    public int getPort_high() {

        return port_high;
    }

    public void setPort_high() {
        this.port_high = 1 + generator.nextInt(20);
    }

    public void setGenerator(Random generator) {
        this.generator = generator;
    }

    public Socket getTempsocket() {
        return tempsocket;
    }

    public void setTempsocket(Socket tempsocket) {
        this.tempsocket = tempsocket;
    }

    public String getLOGIN_WARNING() {
        return LOGIN_WARNING;
    }

    public String getTempUsername() {
        return tempUsername;
    }

    public void setTempUsername(String tempUsername) {
        this.tempUsername = tempUsername;
    }
}
