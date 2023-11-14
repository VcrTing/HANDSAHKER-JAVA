package com.qiong.handshaker.moduie.product.inner;

import cn.hutool.json.JSONUtil;
import com.qiong.handshaker.define.dataset.EntityDefineDataset;
import com.qiong.handshaker.moduie.product.Label;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.view.product.ViewProductResultForm;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class LabelsJson {
    private List<Long> ids;
    private List<String> stringIds;
    private String jsonStr;

    /**
    * 操作 ID
    * @params
    * @return
    */
    public static String idToString(Long id) {
        return EntityDefineDataset.ID_JSON_PREFIX + id;
    }

    /**
    * 去重复
    * @params
    * @return
    */
    public void killSame() {
        ids = new ArrayList<>(new HashSet<>(ids));
        stringIds = new ArrayList<>(new HashSet<>(stringIds));
    }

    /**
    * 操作方法
    * @params
    * @return
    */
    public void remove(Long id) {
        List<Long> res = new ArrayList<>();
        List<String> reStr = new ArrayList<>();

        for (Long i: ids) {
            if (!Objects.equals(i, id)) {
                res.add(i);
                reStr.add(EntityDefineDataset.ID_JSON_PREFIX + i);
            }
        }
        ids = res;
        stringIds = reStr;
        killSame();
        jsonStr = JSONUtil.toJsonStr(stringIds);
    }

    public void add(Long id) {
        ids.add(id);
        stringIds.add(EntityDefineDataset.ID_JSON_PREFIX + id);
        killSame();
        jsonStr = JSONUtil.toJsonStr(stringIds);
    }

    /**
    * 与实体类 交互
    * @params
    * @return
    */
    public List<Label> idsToEntity(List<Label> src) {
        List<Label> res = new ArrayList<>();
        for (Long id: ids) {
            for (Label l: src) {
                if (Objects.equals(l.getId(), id)) res.add(l);
            }
        }
        return res;
    }

    /**
    * 生成 法
    * @params
    * @return
    */
    public static LabelsJson init(Product product) {

        LabelsJson res = new LabelsJson();

        res.ids = new ArrayList<>();

        res.jsonStr = product.getLabels();
        res.stringIds = JSONUtil.toList(res.jsonStr, String.class);

        if (res.stringIds != null) {
            for(String s: res.stringIds) {
                Long id = Long.parseLong(s.substring(EntityDefineDataset.ID_JSON_PREFIX.length()));
                res.ids.add(id);
            }
        }

        return res;
    }

    public static LabelsJson init(List<Long> ids) {

        LabelsJson res = new LabelsJson();

        res.stringIds = new ArrayList<>();

        res.ids = ids;

        for (Long id: ids) {
            res.stringIds.add(EntityDefineDataset.ID_JSON_PREFIX + id);
        }

        res.jsonStr = JSONUtil.toJsonStr(res.stringIds);

        return res;
    }
}
