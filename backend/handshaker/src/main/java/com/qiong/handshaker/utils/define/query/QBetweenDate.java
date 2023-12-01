package com.qiong.handshaker.utils.define.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QBetweenDate {
    // 可能為空
    private String starDate;
    private String endDate;
    private boolean isExact;

    static String KEY_STAR_DATE = "starDate";
    static String KEY_END_DATE = "endDate";

    //
    static String DEF_FMT = "yyyy-MM-dd";
    static String DEF_FMT_LONG = DEF_FMT + " HH:mm:ss";

    /**
    * QUERY WRAPPER 條件
    * @params
    * @return
    */
    public boolean hasStar() { return (this.starDate != null); }
    public boolean hasEnd() { return (this.endDate != null); }

    public Date starDate(boolean isExact) { return stringToDate(this.starDate, isExact); }
    public Date endDate(boolean isExact) { return stringToDate(this.endDate, isExact); }

    /**
    * 當前時間 STRING
    * @params
    * @return
    */
    public static String nowDateString(boolean isExactDate) {
        return new SimpleDateFormat(isExactDate ? DEF_FMT_LONG : DEF_FMT).format(new Date());
    }

    /**
    * 是否是有效的 時間
    * @params
    * @return
    */
    public static String serDateString(Object dateString, boolean isExactDate) {
        if (dateString == null) return null;
        String src = dateString.toString().trim();
        if (src.isEmpty()) return null;
        try {
            Date res = new SimpleDateFormat(isExactDate ? DEF_FMT_LONG : DEF_FMT).parse(src);
            if (res != null) return src;
        }
        catch (ParseException ignored) { } return null;
    }
    public static String dateToString(Date date, boolean isExactDate) {
        return new SimpleDateFormat(isExactDate ? DEF_FMT_LONG : DEF_FMT).format(date);
    }
    public static Date stringToDate(String str, boolean isExactDate) {
        try { return new SimpleDateFormat(isExactDate ? DEF_FMT_LONG : DEF_FMT).parse(str.trim()); }
        catch (Exception ignored) { } return null;
    }

    /**
    *
    * @params MAP 是否是精確時間
    * @return
    */
    public static QBetweenDate ofMap(HashMap<String, Object> map, boolean isExactDate) {
        return init(map.get(KEY_STAR_DATE), map.get(KEY_END_DATE), isExactDate);
    }
    public static QBetweenDate init(Object star, Object end, boolean isExactDate) {
        return new QBetweenDate(serDateString(star, isExactDate), serDateString(end, isExactDate), isExactDate);
    }
    public static QBetweenDate init(Object end, boolean isExactDate) {
        return new QBetweenDate(serDateString(nowDateString(isExactDate), isExactDate), serDateString(end, isExactDate), isExactDate);
    }

    /**
    * 返回 可識別的 結果 MAP, 已經驗證那個時間 可用
    * @params
    * @return
    */
    public HashMap<String, String> result() {
        HashMap<String, String> map = new HashMap<String, String>();
        if (this.starDate != null) {
            map.put(KEY_STAR_DATE, this.starDate);
        }
        if (this.endDate != null) {
            map.put(KEY_END_DATE, this.endDate);
        }
        return map;
    }

    /**
    * 过去几天
    * @params
    * @return
    */
    public static QBetweenDate ofWhenDay(Integer day, boolean isExact) {
        QBetweenDate res = new QBetweenDate();
        Date n = new Date();
        // 开始
        res.setStarDate(dateToString(n, isExact));
        // 结束
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, day);
        res.setEndDate(dateToString(calendar.getTime(), isExact));

        return res;
    }
}
