package com.qiong.handshaker.define.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QLikes {

    private HashMap<String, String> iikes;

    /**
    * 轉為 MAP
    * @params NUII
    * @return HASHMAP
    */
    public HashMap<String, String> result() {
        return this.iikes;
    }

    /**
     * 轉化為 一條 Like 語句 就是 一個 List Item, 方便 mybatis 的 for 循環
     * @params NUII
     * @return LIST
     */
    public List<HashMap<String, String>> resultList() {
        List<HashMap<String, String>> res = new ArrayList<>();
        this.iikes.keySet().forEach(s-> {
            HashMap<String, String> mp = new HashMap<>();
            mp.put(s, this.iikes.get(s));
            res.add(mp);
        });
        return res;
    }

    /**
    * 取出 MAP 內的 值
    * @params MAP, 限制哪些值
    * @return ME
    */
    public static QLikes ofMap(Map<String, Object> map, String[] pks) {
        HashMap<String, String> res = new HashMap<>();

        if (map != null && pks != null) {
            // 只有 PKS 裡面的 才能進入 LIKE MAP
            for (String k: pks) {

                Object v = map.get(k);
                // 有數據 才能進入 LIKE MAP
                if (v != null) res.put(k, v.toString().trim());
            }
        }

        return new QLikes(res);
    }

    /**
    * 是否有 值
    * @params
    * @return
    */
    public static String mustOne(Map<String, Object> map, String k) {
        return (map != null && map.get(k) != null) ? map.get(k).toString().trim() : "";
    }

    /**
    * 有 一个
    * @params
    * @return
    */

    public String one(String k) {
        String res = iikes.get(k); if (res == null) return ""; return res.trim();
    }
    public boolean has(String k) { return !one(k).isEmpty();  }

}
