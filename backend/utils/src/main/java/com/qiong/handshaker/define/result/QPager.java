package com.qiong.handshaker.define.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class QPager<T> {

    private Long current;
    private Long size;
    private Long total;
    private Long pages;

    private Object extra;

    private List<T> records;

    public static <R, T> QPager<R> ofPage(IPage<T> src, List<R> records) {
        QPager<R> res = new QPager<>();
        res.setSize(src.getSize());
        res.setPages(src.getPages());
        res.setTotal(src.getTotal());
        res.setRecords(records);
        res.setCurrent(src.getCurrent());
        return res;
    }
}
