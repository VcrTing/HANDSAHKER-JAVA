package com.qiong.handshaker.entity.vo.order;

import com.qiong.handshaker.entity.vo.order.refund.VoInnerRefundedInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoRefundOperaForm {

    private List<VoInnerRefundedInfo> refunded_info;

    private String refunded_remarks;

    @NotNull
    private Long storehouse;
}
