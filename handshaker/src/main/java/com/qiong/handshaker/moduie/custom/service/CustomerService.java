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

    public Customer oneDeep(Long id) { return mapper.oneDeep(id); }

    /**
    * 新增客户
    * @params
    * @return
    */
    public QResponse<Object> posCustom(Customer entity) {
        Long memberID = DataConfigHandshaker.initMemberID;

        // 查询最后一个 用户
        Customer last = mapper.lastEntity();

        // 重组 member id
        if (last != null) {
            Long mid = QTypedUtil.serLong(last.getMember_id());
            if (mid != null) memberID = mid + 1;
        }
        entity.setMember_id(memberID.toString());

        int isOk = mapper.insert(entity);
        return QResponseTool.restfull(isOk > 0, entity);
    }


    /**
     * 自定義 深度 分頁
     * @params
     * @return
     */
    public IPage<Customer> pageDeep(
            IPage<Customer> ip,
            QueryWrapper<Customer> iqw) {
        // 查詢 紀錄
        ip.setRecords(mapper.pageDeep(ip, iqw));
        // 查詢 數量
        ip.setTotal(QTypedUtil.serLong(mapper.pageDeepCount(ip, iqw), 0L));
        return ip;
    }
}
