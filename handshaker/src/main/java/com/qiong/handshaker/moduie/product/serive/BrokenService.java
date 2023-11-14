package com.qiong.handshaker.moduie.product.serive;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.moduie.product.Broken;
import com.qiong.handshaker.moduie.product.mapper.BrokenMapper;
import com.qiong.handshaker.view.product.ViewBrokenResultForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrokenService extends ServiceImpl<BrokenMapper, Broken> {

    @Autowired
    BrokenMapper mapper;

    public List<ViewBrokenResultForm> serListBroken(List<Broken> list) {
        List<ViewBrokenResultForm> res = new ArrayList<>();
        list.forEach(s -> res.add(ViewBrokenResultForm.fromBean(s)));
        return res;
    }

    /**
     * 自定義 深度 分頁
     * @params
     * @return
     */
    public IPage<ViewBrokenResultForm> pageDeep(IPage<Broken> ip, QueryWrapper<Broken> iqw) {

        List<Broken> bs = mapper.pageDeep(ip, iqw);
        IPage<ViewBrokenResultForm> resIp = new Page<>();
        System.out.println("数量 = " + bs.size());

        // 查詢 數量
        resIp.setTotal(
                ip.getTotal()
                // QTypedUtil.serLong(mapper.pageDeepCount(ip, iqw), 0L)
        );
        resIp.setCurrent(ip.getCurrent());
        resIp.setPages(ip.getPages());
        resIp.setSize(ip.getSize());
        // 查詢 紀錄
        resIp.setRecords(serListBroken(bs));

        return resIp;
    }
}
