package com.qiong.handshaker.moduie.product.controller;

import com.qiong.handshaker.anno.result.QResponseAdvice;
import com.qiong.handshaker.data.properties.PropertiesMy;
import com.qiong.handshaker.data.router.DataRouterProduct;
import com.qiong.handshaker.data.security.DataSecurityRoleConf;
import com.qiong.handshaker.define.result.QResponse;
import com.qiong.handshaker.moduie.base.service.SupplierService;
import com.qiong.handshaker.moduie.product.Label;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.inner.LabelsJson;
import com.qiong.handshaker.moduie.product.service.LabelService;
import com.qiong.handshaker.moduie.product.service.ProductService;
import com.qiong.handshaker.tool.result.QResponseTool;
import com.qiong.handshaker.tool.staticset.QStaticTool;
import com.qiong.handshaker.utils.basic.QFileUtil;
import com.qiong.handshaker.view.product.ViewProductResultForm;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.util.*;

@RestController
@QResponseAdvice
public class ProductAndLabelController {

    @Autowired
    SupplierService supplierService;

    @Autowired
    ProductService productService;

    @Autowired
    LabelService labelService;

    // 具体 新加 删除 标签 的 操作
    public QResponse<Product> operaProductLabel(Long pid, Long lid, boolean isAdd) {
        Product product = productService.getById(pid);

        if (product != null) {
            LabelsJson lj = LabelsJson.init(product);
            // 删除 或 新增
            if (isAdd) { lj.add(lid); } else { lj.remove(lid); }
            product.setLabels(lj.getJsonStr());
            productService.updateById(product);
        }
        return QResponseTool.restfull((product != null), product);
    }

    /**
    * 产品 新增 标签
    * @params
    * @return
    */
    @PatchMapping("/products_add_label/{pid}/{lid}")
    public QResponse<Product> addLabel(@PathVariable Long pid, @PathVariable Long lid) {
        return operaProductLabel(pid, lid, true);
    }

    /**
    * 产品 移除 标签
    * @params
    * @return
    */
    @PatchMapping("/products_delete_label/{pid}/{lid}")
    public QResponse<Product> deiLabel(@PathVariable Long pid, @PathVariable Long lid) {
        return operaProductLabel(pid, lid, false);
    }

    /**
    * 将 所有产品 导出为一张 excel
    * @params
    * @return
    */
    /*
    // @Value("${spring.web.resources.static-locations}")
    @Value("${my.document-folder}")
    private String documentFolder;

    @Value("${my.interview-header}")
    private String interviewHeader;
    */
    @Autowired
    QStaticTool staticTool;

    // 自定义 数据
    @Autowired
    PropertiesMy propertiesMy;

    /**
    * 导出 产品 EXCAEL
    * @params
    * @return
    */
    @PreAuthorize(DataSecurityRoleConf.AUTH_ADMIN_ONLY)
    @GetMapping(DataRouterProduct.EXPORT)
    public QResponse<String> exportExcel() {

        // 全部 产品
        List<Product> products = productService.list();
        // 全部 标签
        List<Label> labels = labelService.list();
        // 制作 ViewProductResultForm
        List<ViewProductResultForm> res = new ArrayList<>();
        for (Product pro: products) { res.add(ViewProductResultForm.init(pro, labels)); }

        // 制作 SHEET
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("產品統計表");
        // 插入 EXCEL 数据
        ViewProductResultForm.genExcel(sheet, res);

        String name = "";
        // 写入 文件
        try {
            name = QFileUtil.genName("產品統計表", "xlsx");
            OutputStream out = staticTool.genResourceOutputStream(propertiesMy.getDocumentFolder(), name);
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception ignored) { }

        return QResponseTool.restfull(!name.isEmpty(), name); // propertiesMy.getInterviewHeader() + "/" + name
    }
}
