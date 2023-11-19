package com.qiong.handshaker.moduie.cashier.service;

import com.qiong.handshaker.define.exception.vaiid.QLogicException;
import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.custom.Customer;
import com.qiong.handshaker.moduie.custom.MemberLevel;
import com.qiong.handshaker.moduie.order.Order;
import com.qiong.handshaker.moduie.order.OrderProfit;
import com.qiong.handshaker.moduie.order.inner.OrderDiscount;
import com.qiong.handshaker.moduie.order.inner.OrderProduct;
import com.qiong.handshaker.moduie.order.mapper.OrderMapper;
import com.qiong.handshaker.moduie.order.service.OrderService;
import com.qiong.handshaker.moduie.order.service.ProfitService;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.VariationAndStorehouseAndProduct;
import com.qiong.handshaker.moduie.product.mapper.VariationAndStorehouseAndProductMapper;
import com.qiong.handshaker.moduie.product.service.ProductService;
import com.qiong.handshaker.moduie.product.service.VariationAndStorehouseAndProductService;
import com.qiong.handshaker.moduie.sys.User;
import com.qiong.handshaker.vo.order.VoOrderPostForm;
import com.qiong.handshaker.vo.order.inner.VoInnerOrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutService {

    @Autowired
    VariationAndStorehouseAndProductService variationAndStorehouseAndProductService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProfitService profitService;

    @Autowired
    ProductService productService;

    /**
    * 结算金钱
    * @params
    * @return
    */
    public Order checkout(VoOrderPostForm form, User user, Customer customer, MemberLevel memberLevel, Storehouse storehouse) {

        List<VoInnerOrderProduct> viops = form.getOrdered_product();

        List<VariationAndStorehouseAndProduct> vsps = new ArrayList<>();

        // 查询 库存 是否 足够，放行 则 库存 足够
        for (VoInnerOrderProduct op: viops) {

            // 检查 库存 是否 足够
            VariationAndStorehouseAndProduct vsp = variationAndStorehouseAndProductService.one(op.getProduct(), op.getVariation(), storehouse.getId());
            if (op.mustGetQuantity() > vsp.mustGetQuantity()) throw new QLogicException("产品库存不足，无法结算，仓库名称是：" + storehouse.getName());

            // 放行 + 移除 库存
            vsp.removeQuantity(op.mustGetQuantity());
            vsps.add(vsp);

            // 查询 最新 产品 数据
            op.setProductEntity(productService.getById(op.getProduct()));
        }

        List<OrderProduct> order_products = new ArrayList<>();
        // 生成 OrderProduct
        for (VoInnerOrderProduct op: viops) { order_products.add( OrderProduct.init(op, op.getProductEntity()) ); }

        // 更新 库存 数据
        for (VariationAndStorehouseAndProduct vsp: vsps) { variationAndStorehouseAndProductService.modifyNum(vsp, vsp.getQuantity()); }

        // 储存 订单，正式开单
        Order order = Order.init(form, user, customer, memberLevel, storehouse);
        order.jsonSetOrdered_product(order_products);

        System.out.println();

        // 构建 利率表 记录
        OrderProfit profit = OrderProfit.init(order, order_products, OrderDiscount.init(form.getDiscount()));
        boolean isOk = profitService.save(profit);
        if (!isOk) throw new QLogicException("利率储存 失败");

        // 反馈 回 订单，订单连表
        order.setProfit_sql_id(profit.getId());
        isOk = orderService.save(order);
        if (!isOk) throw new QLogicException("订单储存 失败");

        return order;
    }
}
