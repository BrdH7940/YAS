package com.yas.promotion.viewmodel;

import static org.assertj.core.api.Assertions.assertThat;

import com.yas.promotion.model.Promotion;
import com.yas.promotion.model.PromotionApply;
import com.yas.promotion.model.enumeration.ApplyTo;
import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.Test;

class PromotionPutVmTest {

    @Test
    void createPromotionApplies_applyToProduct_createsProductApplies() {
        Promotion promotion = Promotion.builder()
            .id(1L)
            .applyTo(ApplyTo.PRODUCT)
            .build();

        PromotionPutVm putVm = new PromotionPutVm();
        putVm.setApplyTo(ApplyTo.PRODUCT);
        putVm.setProductIds(List.of(10L, 20L));

        List<PromotionApply> result = PromotionPutVm.createPromotionApplies(putVm, promotion);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getProductId()).isEqualTo(10L);
        assertThat(result.get(1).getProductId()).isEqualTo(20L);
        assertThat(result.get(0).getPromotion()).isEqualTo(promotion);
    }

    @Test
    void createPromotionApplies_applyToBrand_createsBrandApplies() {
        Promotion promotion = Promotion.builder()
            .id(2L)
            .applyTo(ApplyTo.BRAND)
            .build();

        PromotionPutVm putVm = new PromotionPutVm();
        putVm.setApplyTo(ApplyTo.BRAND);
        putVm.setBrandIds(List.of(100L, 200L, 300L));

        List<PromotionApply> result = PromotionPutVm.createPromotionApplies(putVm, promotion);

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getBrandId()).isEqualTo(100L);
        assertThat(result.get(1).getBrandId()).isEqualTo(200L);
        assertThat(result.get(2).getBrandId()).isEqualTo(300L);
    }

    @Test
    void createPromotionApplies_applyToCategory_createsCategoryApplies() {
        Promotion promotion = Promotion.builder()
            .id(3L)
            .applyTo(ApplyTo.CATEGORY)
            .build();

        PromotionPutVm putVm = new PromotionPutVm();
        putVm.setApplyTo(ApplyTo.CATEGORY);
        putVm.setCategoryIds(List.of(500L, 600L));

        List<PromotionApply> result = PromotionPutVm.createPromotionApplies(putVm, promotion);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCategoryId()).isEqualTo(500L);
        assertThat(result.get(1).getCategoryId()).isEqualTo(600L);
    }
}
