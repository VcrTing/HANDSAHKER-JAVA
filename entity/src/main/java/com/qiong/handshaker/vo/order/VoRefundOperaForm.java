package com.qiong.handshaker.vo.order;

import com.qiong.handshaker.vo.order.refund.VoInnerRefundedInfo;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoRefundOperaForm {

    private List<VoInnerRefundedInfo> refunded_info;

    private String refunded_remarks;

    private Long storehouse;
}
