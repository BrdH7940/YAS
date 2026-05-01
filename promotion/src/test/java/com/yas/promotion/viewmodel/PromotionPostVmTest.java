package com.yas.promotion.viewmodel;

import static org.assertj.core.api.Assertions.assertThat;

import com.yas.promotion.model.Promotion;
import com.yas.promotion.model.PromotionApply;
import com.yas.promotion.model.enumeration.ApplyTo;
import com.yas.promotion.model.enumeration.DiscountType;
import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.Test;

class PromotionPostVmTest {

    @Test
    void createPromotionApplies_applyToProduct_createsProductApplies() {
        Promotion promotion = Promotion.builder()
            .id(1L)
            .applyTo(ApplyTo.PRODUCT)
            .build();

        PromotionPostVm postVm = PromotionPostVm.builder()
            .name("Test")
            .slug("test")
            .couponCode("CODE1")
            .applyTo(ApplyTo.PRODUCT)
            .productIds(List.of(10L, 20L, 30L))
            .startDate(Date.from(Instant.now()))
            .endDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)))
            .build();

        List<PromotionApply> result = PromotionPostVm.createPromotionApplies(postVm, promotion);

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getProductId()).isEqualTo(10L);
        assertThat(result.get(1).getProductId()).isEqualTo(20L);
        assertThat(result.get(2).getProductId()).isEqualTo(30L);
        assertThat(result.get(0).getPromotion()).isEqualTo(promotion);
    }

    @Test
    void createPromotionApplies_applyToBrand_createsBrandApplies() {
        Promotion promotion = Promotion.builder()
            .id(2L)
            .applyTo(ApplyTo.BRAND)
            .build();

        PromotionPostVm postVm = PromotionPostVm.builder()
            .name("Brand Promo")
            .slug("brand-promo")
            .couponCode("BRAND1")
            .applyTo(ApplyTo.BRAND)
            .brandIds(List.of(100L, 200L))
            .startDate(Date.from(Instant.now()))
            .endDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)))
            .build();

        List<PromotionApply> result = PromotionPostVm.createPromotionApplies(postVm, promotion);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getBrandId()).isEqualTo(100L);
        assertThat(result.get(1).getBrandId()).isEqualTo(200L);
        assertThat(result.get(0).getPromotion()).isEqualTo(promotion);
    }

    @Test
    void createPromotionApplies_applyToCategory_createsCategoryApplies() {
        Promotion promotion = Promotion.builder()
            .id(3L)
            .applyTo(ApplyTo.CATEGORY)
            .build();

        PromotionPostVm postVm = PromotionPostVm.builder()
            .name("Category Promo")
            .slug("category-promo")
            .couponCode("CAT1")
            .applyTo(ApplyTo.CATEGORY)
            .categoryIds(List.of(500L))
            .startDate(Date.from(Instant.now()))
            .endDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)))
            .build();

        List<PromotionApply> result = PromotionPostVm.createPromotionApplies(postVm, promotion);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategoryId()).isEqualTo(500L);
        assertThat(result.get(0).getPromotion()).isEqualTo(promotion);
    }
}
