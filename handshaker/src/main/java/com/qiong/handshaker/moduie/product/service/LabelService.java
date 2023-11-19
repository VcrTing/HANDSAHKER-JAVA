package com.qiong.handshaker.moduie.product.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.moduie.product.Label;
import com.qiong.handshaker.moduie.product.mapper.LabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LabelService extends ServiceImpl<LabelMapper, Label> {

    @Autowired
    LabelMapper mapper;

    // 根据 IDS 获取 LABELS
    public List<Label> manyByIds(List<Long> ids, List<Label> labels) {
        // 取出 全部，为了 提高 效率
        // List<Label> src = mapper.selectList(null);

        List<Label> res = new ArrayList<>();

        // 匹配结果
        for (Label l: labels) {
            for (Long id: ids) {
                if (Objects.equals(id, l.getId())) res.add(l);
            }
        }

        return res;
    }
}
