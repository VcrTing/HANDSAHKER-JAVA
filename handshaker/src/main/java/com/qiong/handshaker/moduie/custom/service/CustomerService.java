package com.qiong.handshaker.moduie.custom.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiong.handshaker.data.config.DataConfigHandshaker;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.custom.Customer;
import com.qiong.handshaker.moduie.custom.MemberLevel;
import com.qiong.handshaker.moduie.custom.mapper.CustomerMapper;
import com.qiong.handshaker.moduie.custom.mapper.MemberLevelMapper;
import com.qiong.handshaker.moduie.sys.User;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.utils.basic.QTypedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends ServiceImpl<CustomerMapper, Customer> {

    @Autowired
    CustomerMapper mapper;

    /**
    * 新增客户，新增前，需要自定义增长的 customer id
    * @params
    * @return
    */
    public QResponse<Object> posCustom(Customer entity) {
        long memberID = DataConfigHandshaker.initMemberID;

        // 查询最后一个 用户
        Customer last = mapper.lastEntity();

        // 重组 member id
        if (last != null) {
            Long mid = QTypedUtil.serLong(last.getMember_id());
            if (mid != null) memberID = mid + 1;
        }
        entity.setMember_id(Long.toString(memberID));

        // 返回
        return QResponseTool.restfull(mapper.insert(entity) > 0, entity);
    }


    /**
     * 自定義 深度 分頁
     * @params
     * @return
     */
    public IPage<Customer> pageDeep( IPage<Customer> ip, QueryWrapper<Customer> iqw) {
        // 查詢 紀錄
        ip.setRecords(mapper.pageDeep(ip, iqw));
        // 查詢 數量
        // ip.setTotal(QTypedUtil.serLong(mapper.pageDeepCount(ip, iqw), 0L));
        return ip;
    }

    /**
    * 自定义 深度 查询 单个
    * @params
    * @return
    */
    public Customer oneDeep(Long id) { return mapper.oneDeep(id); }
}
