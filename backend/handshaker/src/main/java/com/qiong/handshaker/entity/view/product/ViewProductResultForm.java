package com.qiong.handshaker.entity.view.product;


import cn.hutool.json.JSONUtil;
import com.qiong.handshaker.entity.moduie.product.Label;
import com.qiong.handshaker.entity.moduie.product.Product;
import com.qiong.handshaker.entity.moduie.product.VariationAndStorehouseAndProduct;
import com.qiong.handshaker.entity.moduie.product.inner.LabelsJson;
import com.qiong.handshaker.entity.moduie.product.inner.ProductRemark;
import com.qiong.handshaker.entity.view.product.inner.ViewInnerBrokenProduct;
import com.qiong.handshaker.entity.view.product.inner.ViewInnerRestock;
import com.qiong.handshaker.entity.view.product.inner.ViewInnerVariation;
import com.qiong.handshaker.entity.view.product.inner.ViewInnerStorehouseInfo;
import com.qiong.handshaker.utils.utils.basic.QTypedUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewProductResultForm implements Serializable {

    private Long id;

    private String product_id;
    private Date create_date;
    private String name;

    private Date new_restock_date;

    private BigDecimal new_restock_price;
    private BigDecimal new_selling_price;
    private BigDecimal new_lowest_price;

    // 入货价 平均 价
    private BigDecimal average_restock_price;
    // 总库存
    private Integer total_stock;

    private String new_supplier;

    private List<Label> labels;

    private List<ViewInnerVariation> variations;

    private List<ProductRemark> remarks;

    // 本月坏货
    private BigDecimal total_broken_products;

    // 坏货详情
    private List<ViewInnerBrokenProduct> broken_products;

    // 库存详情
    private List<ViewInnerStorehouseInfo> storehouse_info;

    // 入貨紀錄
    private List<ViewInnerRestock> restock;
    private List<ViewInnerRestock> restocks;

    // 更新 最新 入货 记录
    public void asyncNewRestock() {
        List<ViewInnerRestock> irs = restocks;
        if (irs != null) {
            irs.sort((s1, s2) -> (int)(s1.getId() - s2.getId()));
            if (!irs.isEmpty()) {
                restock = new ArrayList<>();
                restock.add(irs.get(0));
            }
        }
    }

    /**
    * 组装 ViewInnerVariation ，以及计算 总 库存
    * @params VariationAndStorehouseAndProduct 全部数据 或 与 该 产品 有关的 数据
    * @return
    */
    public ViewProductResultForm groupVariations(List<VariationAndStorehouseAndProduct> vsps) {

        if (vsps == null) return this;
        HashMap<Long, ViewInnerVariation> nums = new HashMap<>();
        Integer total = 0;

        for (VariationAndStorehouseAndProduct vsp: vsps) {
            if (Objects.equals(vsp.getProduct_sql_id(), this.id)) {
                Long __id = vsp.getVariation_sql_id();
                if (nums.get(__id) == null) {
                    nums.put(__id, ViewInnerVariation.init(vsp, false));
                } else {
                    ViewInnerVariation one = nums.get(__id);
                    one.setQuantity( one.getQuantity() + vsp.mustGetQuantity());
                    nums.put(__id, one);
                }
                total += vsp.mustGetQuantity();
            }
        }

        this.setVariations(new ArrayList<>(nums.values()));
        this.setTotal_stock(total);
        return this;
    }

    /**
    * 组装结果
    * @params
    * @return
    */
    public static ViewProductResultForm init(Product product, List<Label> allLabels) {

        ViewProductResultForm res = new ViewProductResultForm();
        if (product == null) return res;

        res.setId(product.getId());
        res.setName(product.getName());
        res.setProduct_id(product.getProduct_id());
        res.setCreate_date(product.getCreate_date());

        res.setNew_supplier(product.getNew_supplier());
        res.setNew_restock_date(product.getNew_restock_date());

        res.setNew_lowest_price(product.getNew_lowest_price());
        res.setNew_restock_price(product.getNew_restock_price());
        res.setNew_selling_price(product.getNew_selling_price());

        // 序列化 备注
        if (product.getRemarks() != null) res.setRemarks(JSONUtil.toList(product.getRemarks(), ProductRemark.class));

        // 序列化 标签
        if (allLabels != null) res.setLabels(LabelsJson.init(product).idsToEntity(allLabels));

        // 序列化 库存 数据

        return res;
    }

    /**
    * 生成 EXCEL
    * @params
    * @return
    */
    public static void genExcel(Sheet sheet, List<ViewProductResultForm> src) {

        for (int i = 0; i < src.size(); i++) {
            Row row = sheet.createRow(i);
            ViewProductResultForm one = src.get(i);
            // 基础 信息
            row.createCell(0).setCellValue( one.getProduct_id() );
            row.createCell(1).setCellValue( one.getName() );
            row.createCell(2).setCellValue(QTypedUtil.serStr(one.getCreate_date(), false));

            // 标签
            StringBuilder sbLabel = new StringBuilder();
            one.getLabels().forEach(s -> sbLabel.append( s.getName() ).append( "，") );
            row.createCell(3).setCellValue( sbLabel.toString() );

            // 入货
            row.createCell(4).setCellValue( QTypedUtil.serStr(one.getTotal_stock(), 0) );
            row.createCell(5).setCellValue( QTypedUtil.serStr(one.getNew_restock_date(), false) );
            row.createCell(6).setCellValue( one.getNew_supplier() );

            // 价格
            row.createCell(7).setCellValue( QTypedUtil.serStr(one.getNew_lowest_price()) );
            row.createCell(8).setCellValue( QTypedUtil.serStr(one.getNew_restock_price()) );
            row.createCell(9).setCellValue( QTypedUtil.serStr(one.getNew_selling_price()) );
            row.createCell(10).setCellValue( QTypedUtil.serStr(one.getAverage_restock_price()) );

            // 备注
            StringBuilder sbRmk = new StringBuilder();
            one.getRemarks().forEach(s -> sbRmk.append( s.getContent() ).append( "，") );
            row.createCell(11).setCellValue( sbRmk.toString() );

        }
    }
}
