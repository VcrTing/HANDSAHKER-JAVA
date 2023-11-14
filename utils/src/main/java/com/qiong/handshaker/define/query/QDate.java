package com.qiong.handshaker.define.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QDate {
    // 可能為空
    private Date date;
    private boolean isExact;

    //
    static String DEF_FMT = "yyyy-MM-dd";
    static String DEF_FMT_LONG = DEF_FMT + " HH:mm:ss";

    public boolean has() { return date != null; }
    /**
     * 是否是有效的 時間
     * @params
     * @return
     */
    public static Date serDate(Object dateString, boolean isExactDate) {
        if (dateString == null) return null; String src = dateString.toString().trim();
        if (src.isEmpty()) return null;
        try { return new SimpleDateFormat(isExactDate ? DEF_FMT_LONG : DEF_FMT).parse(src); }
        catch (ParseException ignored) { } return null;
    }

    /**
     *
     * @params MAP 是否是精確時間
     * @return
     */
    public static QDate ofMap(HashMap<String, Object> map, String k, boolean isExactDate) {
        return new QDate( serDate(map.get(k), isExactDate), isExactDate );
    }
    public static QDate ofMap(HashMap<String, Object> map, String k) {
        return ofMap(map, k, false);
    }
}
