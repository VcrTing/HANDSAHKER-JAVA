package com.qiong.handshaker.utils.utils.basic;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class QFileUtil {

    static String FILE_FMT = "yyyyMMddhhmm";

    public static String genName(String suffix, String fileTyped) {
        Date n = new Date();
        String ss = new SimpleDateFormat(FILE_FMT).format(n);
        return suffix + "_" + ss + "." + fileTyped;
    }
}
