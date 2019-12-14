package com.gialinh.shop.web.controller;

import com.gialinh.shop.domain.Category;
import com.gialinh.shop.domain.Collection;
import com.gialinh.shop.domain.Product;
import com.gialinh.shop.service.custom.CategoryServiceCustom;
import com.gialinh.shop.service.custom.CollectionServiceCustom;
import com.gialinh.shop.service.custom.ProductServiceCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/seeder")
public class SeederController {
    @Autowired
    CategoryServiceCustom categoryService;
    @Autowired
    CollectionServiceCustom collectionService;
    @Autowired
    ProductServiceCustom productService;

    private static Logger LOGGER = LoggerFactory.getLogger(SeederController.class.getSimpleName());

    @GetMapping
    public String seeder() {
        List<Category> existCategory = categoryService.findAllNoStatus();
        List<Category> categories = new ArrayList<>(
            Arrays.asList(
                new Category("Dụng cụ Nails", "Dùng để làm Nail", "http://viendaotaothammy.com/wp-content/uploads/2016/10/do-nghe-lam-nail-can-thiet-de-mo-tiem-nail-1.jpg"),
                new Category("Nails", "Dịch cụ chăm sóc Nail", "https://photo-2-baomoi.zadn.vn/w1000_r1/2019_01_09_322_29290888/1ec026dc9d9d74c32d8c.jpg"),
                new Category("Makeup", "Dịch cụ Trang điểm", "https://media.ex-cdn.com/EXP/media.phunutoday.vn/files/content/2019/04/21/ve-dep-sieu-thuc-cua-nu-hoang-makeup-han-quoc-gay-sot-toan-the-gioi-6-1555484784-605-width800height790-0859.jpg"),
                new Category("Mỹ phẩm", "Mỹ phẩm xách tay", "https://media.healthplus.vn/thumb_x650x382/Images/Uploaded/Share/2018/02/08/10-bi-mat-ma-nganh-cong-nghiep-my-pham-my-khong-muon-tiet-lo-voi-ban11518078340.jpg")
            )
        );
        if (existCategory != null && existCategory.size() > 0) {
            LOGGER.info("Category is has data!");
        } else {
            for (Category category : categories) {
                categoryService.save(category);
            }
        }

        List<Collection> existCollection = collectionService.findAllNoPage();
        List<Collection> collections = new ArrayList<>(
            Arrays.asList(
                new Collection("Mùa Xuân", "Mùa xuân", "http://trainghiemvanhoa.vn/images/anh_1.png"),
                new Collection("Mùa Hè", "Mùa hè", "https://photo-2-baomoi.zadn.vn/w1000_r1/2019_01_09_322_29290888/1ec026dc9d9d74c32d8c.jpg"),
                new Collection("Mùa Thu", "Mùa thu", "https://media.ex-cdn.com/EXP/media.phunutoday.vn/files/content/2019/04/21/ve-dep-sieu-thuc-cua-nu-hoang-makeup-han-quoc-gay-sot-toan-the-gioi-6-1555484784-605-width800height790-0859.jpg"),
                new Collection("Mùa Đông", "Mùa đông", "https://media.healthplus.vn/thumb_x650x382/Images/Uploaded/Share/2018/02/08/10-bi-mat-ma-nganh-cong-nghiep-my-pham-my-khong-muon-tiet-lo-voi-ban11518078340.jpg")
            )
        );
        if (existCollection != null && existCollection.size() > 0) {
            LOGGER.info("Collection is has data!");
        } else {
            for (Collection collection : collections) {
                collectionService.save(collection);
            }
        }

        List<Product> existProduct = productService.findAllNoPage();
        List<Product> products = new ArrayList<>(
            Arrays.asList(
                new Product("SET SƠN ĐỎ CHANEL", 10000.0, "Set sơn đỏ hàng hiệu.",
                    "https://limnail.com/img/up-anh/anh-6354-72533971_2373721362841077_524600010046701568_n.jpg, https://limnail.com/img/up-anh//anh-7214-73084143_417501735632891_4799026569119006720_n.jpg, https://limnail.com/img/up-anh/anh-8355-72994085_694997197687353_2050994617830080512_n.jpg, https://limnail.com/img/up-anh/anh-3283-72942664_752826408522303_1045832306171838464_n.jpg",
                    false, 0, "set sơn đỏ sml", categoryService.findAllNoStatus().get(0), collectionService.findAllNoPage().get(0)),
                new Product("MÁY HƠ GEL TÍCH ĐIỆN", 9000.0, "MÁY HƠ GEL TÍCH ĐIỆN",
                    "https://anatran.vn/image/cache/imgdata/dung-cu-nail/may-lam-nail/lamp_ans_02/may_ho_tich_dien_1-800x390.png, https://anatran.vn/image/cache/imgdata/dung-cu-nail/may-lam-nail/lamp_ans_02/8691888480_250008574.jpg, https://anatran.vn/image/cache/imgdata/dung-cu-nail/may-lam-nail/lamp_ans_02/8691897207_250008574.jpg, https://anatran.vn/image/cache/imgdata/dung-cu-nail/may-lam-nail/lamp_ans_02/8691906034_250008574.jpg",
                    true, 10, "MÁY HƠ GEL TÍCH ĐIỆN", categoryService.findAllNoStatus().get(1), collectionService.findAllNoPage().get(1)),
                new Product("MÁY HÚT BỤI CÓ VÒI", 800000.0, "MÁY HÚT BỤI CÓ VÒI",
                    "https://anatran.vn/image/cache/imgdata/dung-cu-nail/may-lam-nail/may_hut/may_hut_co_voi_1-800x390.png, https://anatran.vn/image/cache/imgdata/dung-cu-nail/may-lam-nail/may_hut/9382734480_443469210.jpg, https://anatran.vn/image/cache/imgdata/dung-cu-nail/may-lam-nail/may_hut/9407494413_443469210.jpg, https://anatran.vn/image/cache/imgdata/dung-cu-nail/may-lam-nail/may_hut/9427394974_443469210.jpg",
                    false, 0, "MÁY HÚT BỤI CÓ VÒI", categoryService.findAllNoStatus().get(2), collectionService.findAllNoPage().get(2)),
                new Product("BỘ LIÊN KẾT SƠN GEL ANS", 800000.0, "BỘ LIÊN KẾT SƠN GEL ANS",
                    "https://anatran.vn/image/cache/imgdata/combo/bo_lien_ket_2-800x390.png, https://anatran.vn/image/cache/imgdata/combo/bo_lien_ket.png",
                    true, 20, "BỘ LIÊN KẾT SƠN GEL ANS", categoryService.findAllNoStatus().get(3), collectionService.findAllNoPage().get(3))
                )
        );
        if (existProduct != null && existProduct.size() > 0) {
            LOGGER.info("Product is has data!");
        } else {
            for (Product product : products) {
                productService.save(product);
            }
        }

        return "index";
    }
}
