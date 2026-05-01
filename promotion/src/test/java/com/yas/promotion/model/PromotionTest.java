package com.yas.promotion.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.yas.promotion.model.enumeration.ApplyTo;
import com.yas.promotion.model.enumeration.DiscountType;
import com.yas.promotion.model.enumeration.UsageType;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PromotionTest {

    @Test
    void equals_sameInstance_returnsTrue() {
        Promotion promotion = createPromotion(1L);
        assertThat(promotion.equals(promotion)).isTrue();
    }

    @Test
    void equals_sameId_returnsTrue() {
        Promotion promotion1 = createPromotion(1L);
        Promotion promotion2 = createPromotion(1L);
        assertThat(promotion1.equals(promotion2)).isTrue();
    }

    @Test
    void equals_differentId_returnsFalse() {
        Promotion promotion1 = createPromotion(1L);
        Promotion promotion2 = createPromotion(2L);
        assertThat(promotion1.equals(promotion2)).isFalse();
    }

    @Test
    void equals_nullId_returnsFalse() {
        Promotion promotion1 = createPromotion(null);
        Promotion promotion2 = createPromotion(1L);
        assertThat(promotion1.equals(promotion2)).isFalse();
    }

    @Test
    void equals_bothNullId_returnsFalse() {
        Promotion promotion1 = createPromotion(null);
        Promotion promotion2 = createPromotion(null);
        assertThat(promotion1.equals(promotion2)).isFalse();
    }

    @Test
    void equals_notInstanceOfPromotion_returnsFalse() {
        Promotion promotion = createPromotion(1L);
        assertThat(promotion.equals("not a promotion")).isFalse();
    }

    @Test
    void equals_null_returnsFalse() {
        Promotion promotion = createPromotion(1L);
        assertThat(promotion.equals(null)).isFalse();
    }

    @Test
    void hashCode_alwaysReturnsClassHashCode() {
        Promotion promotion1 = createPromotion(1L);
        Promotion promotion2 = createPromotion(2L);
        assertThat(promotion1.hashCode()).isEqualTo(promotion2.hashCode());
        assertThat(promotion1.hashCode()).isEqualTo(Promotion.class.hashCode());
    }

    @Test
    void setPromotionApplies_whenListIsNull_initializesAndAddsItems() {
        Promotion promotion = Promotion.builder().id(1L).build();
        // promotionApplies is null initially in this builder path
        PromotionApply apply1 = PromotionApply.builder().productId(10L).promotion(promotion).build();
        PromotionApply apply2 = PromotionApply.builder().productId(20L).promotion(promotion).build();

        promotion.setPromotionApplies(List.of(apply1, apply2));

        assertThat(promotion.getPromotionApplies()).hasSize(2);
    }

    @Test
    void setPromotionApplies_whenListAlreadyExists_clearsAndAddsNewItems() {
        Promotion promotion = Promotion.builder().id(1L).build();
        PromotionApply apply1 = PromotionApply.builder().productId(10L).promotion(promotion).build();
        promotion.setPromotionApplies(List.of(apply1));

        assertThat(promotion.getPromotionApplies()).hasSize(1);

        PromotionApply apply2 = PromotionApply.builder().productId(20L).promotion(promotion).build();
        PromotionApply apply3 = PromotionApply.builder().productId(30L).promotion(promotion).build();
        promotion.setPromotionApplies(List.of(apply2, apply3));

        assertThat(promotion.getPromotionApplies()).hasSize(2);
        assertThat(promotion.getPromotionApplies()).containsExactly(apply2, apply3);
    }

    @Test
    void allArgsConstructor_setsAllFields() {
        Promotion promotion = Promotion.builder()
            .id(1L)
            .name("Test Promo")
            .slug("test-promo")
            .description("A test promotion")
            .couponCode("CODE123")
            .discountType(DiscountType.PERCENTAGE)
            .usageType(UsageType.LIMITED)
            .applyTo(ApplyTo.PRODUCT)
            .usageLimit(100)
            .usageCount(50)
            .discountPercentage(25L)
            .discountAmount(0L)
            .minimumOrderPurchaseAmount(500L)
            .isActive(true)
            .startDate(Instant.now())
            .endDate(Instant.now().plus(30, ChronoUnit.DAYS))
            .build();

        assertThat(promotion.getId()).isEqualTo(1L);
        assertThat(promotion.getName()).isEqualTo("Test Promo");
        assertThat(promotion.getSlug()).isEqualTo("test-promo");
        assertThat(promotion.getDescription()).isEqualTo("A test promotion");
        assertThat(promotion.getCouponCode()).isEqualTo("CODE123");
        assertThat(promotion.getDiscountType()).isEqualTo(DiscountType.PERCENTAGE);
        assertThat(promotion.getUsageType()).isEqualTo(UsageType.LIMITED);
        assertThat(promotion.getApplyTo()).isEqualTo(ApplyTo.PRODUCT);
        assertThat(promotion.getUsageLimit()).isEqualTo(100);
        assertThat(promotion.getUsageCount()).isEqualTo(50);
        assertThat(promotion.getDiscountPercentage()).isEqualTo(25L);
        assertThat(promotion.getDiscountAmount()).isEqualTo(0L);
        assertThat(promotion.getMinimumOrderPurchaseAmount()).isEqualTo(500L);
        assertThat(promotion.getIsActive()).isTrue();
        assertThat(promotion.getStartDate()).isNotNull();
        assertThat(promotion.getEndDate()).isNotNull();
    }

    private Promotion createPromotion(Long id) {
        Promotion promotion = new Promotion();
        promotion.setId(id);
        promotion.setName("Test Promotion");
        promotion.setSlug("test-promotion");
        return promotion;
    }
}
