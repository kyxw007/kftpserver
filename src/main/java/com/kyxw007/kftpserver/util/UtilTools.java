package com.kyxw007.kftpserver.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * Created by kyxw007 on 15/5/1.
 */
public class UtilTools {
    public static void main(String[] args) throws IOException {
        SimpleDateFormat sdf=new SimpleDateFormat("MMMM d HH:mm", Locale.ENGLISH);
        String a = sdf.format(new Date());
        System.out.println(a);

        OutputStream o = new FileOutputStream(new File("a"));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(o));
        pw.println("123123io12");
        pw.flush();
        pw.close();
        o.flush();
        o.close();
        InputStream in = new FileInputStream(new File("a"));
        System.out.println(in.available());
        int count = in.available();
        byte[] tempByteArray = new byte[count];
        in.read(tempByteArray,0,count);
        System.out.println(new String(tempByteArray));

    }
    public static void close(Closeable s){
        if(s!=null){
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
