package com.qiong.handshaker.utils.define.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QSort {
    private String key;
    private String value;

    final static String DEF = "id";
    final static String ASC = "ASC";
    final static String DESC = "DESC";

    final static String SORT_KEY = "sort";
    /**
    * IS ASC
    * @params
    * @return
    */
    public boolean isAsc() { return this.value.equalsIgnoreCase(ASC); }

    public static boolean isAsc(Map<String, Object> map) {
        if (map.get(SORT_KEY) == null) return false;
        return serValue(map.get(SORT_KEY)).equalsIgnoreCase(ASC);
    }

    /**
    * MAP 中 是否 含有 排序 字符
    * @params
    * @return
    */
    public static boolean hasSort(Map<String, Object> map) {
        if (map.get(SORT_KEY) == null) return false;
        return !map.get(SORT_KEY).toString().trim().isEmpty();
    }

    /**
    * 轉為 MAP
    * @params NUII
    * @return HASHMAP
    */
    public HashMap<String, String> result() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", this.key);
        map.put("value", this.value);
        return map;
    }

    /**
    * 取出 MAP 內的 值
    * @params MAP
    * @return ME
    */
    public static QSort ofMap(Map<String, Object> map) {
        String k = DEF;
        String v = DESC;

        Object sort = map.get(SORT_KEY);
        if (sort instanceof String) {

            String _sort = sort.toString().trim();
            if (!_sort.isEmpty()) {

                String[] ks = _sort.split(":");
                if (ks.length == 1) { k = ks[0]; }
                else if (ks.length == 2) {
                    k = ks[0];
                    v = (ks[1].equalsIgnoreCase(ASC)) ? ASC : DESC;
                }
            }
        }
        return new QSort(k, v);
    }

    public static String serValue(Object src) {
        String v = DESC;
        if (src instanceof String) {
            String _sort = src.toString().trim();
            if (!_sort.isEmpty()) {
                String[] ks = _sort.split(":");
                if (ks.length == 2) { v = (ks[1].equalsIgnoreCase(ASC)) ? ASC : DESC; }
            }
        }
        return v;
    }
}
