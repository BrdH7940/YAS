package com.yas.promotion.viewmodel;

import static org.assertj.core.api.Assertions.assertThat;

import com.yas.promotion.model.Promotion;
import com.yas.promotion.model.enumeration.DiscountType;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

class PromotionVmTest {

    @Test
    void fromModel_mapsAllFields() {
        Instant now = Instant.now();
        Instant endDate = now.plus(30, ChronoUnit.DAYS);

        Promotion promotion = Promotion.builder()
            .id(1L)
            .name("Holiday Sale")
            .slug("holiday-sale")
            .couponCode("HOLIDAY10")
            .discountPercentage(10L)
            .discountAmount(0L)
            .isActive(true)
            .startDate(now)
            .endDate(endDate)
            .build();

        PromotionVm result = PromotionVm.fromModel(promotion);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Holiday Sale");
        assertThat(result.slug()).isEqualTo("holiday-sale");
        assertThat(result.couponCode()).isNull();
        assertThat(result.discountPercentage()).isEqualTo(10L);
        assertThat(result.discountAmount()).isEqualTo(0L);
        assertThat(result.isActive()).isTrue();
        assertThat(result.startDate()).isEqualTo(now);
        assertThat(result.endDate()).isEqualTo(endDate);
    }

    @Test
    void fromModel_withNullOptionalFields() {
        Promotion promotion = Promotion.builder()
            .id(2L)
            .name("Basic Promo")
            .slug("basic-promo")
            .isActive(false)
            .build();

        PromotionVm result = PromotionVm.fromModel(promotion);

        assertThat(result.id()).isEqualTo(2L);
        assertThat(result.name()).isEqualTo("Basic Promo");
        assertThat(result.slug()).isEqualTo("basic-promo");
        assertThat(result.discountPercentage()).isNull();
        assertThat(result.discountAmount()).isNull();
        assertThat(result.isActive()).isFalse();
        assertThat(result.startDate()).isNull();
        assertThat(result.endDate()).isNull();
    }
}
