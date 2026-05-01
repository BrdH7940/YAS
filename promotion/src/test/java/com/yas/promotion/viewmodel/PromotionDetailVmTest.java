package com.yas.promotion.viewmodel;

import static org.assertj.core.api.Assertions.assertThat;

import com.yas.promotion.model.Promotion;
import com.yas.promotion.model.enumeration.ApplyTo;
import com.yas.promotion.model.enumeration.DiscountType;
import com.yas.promotion.model.enumeration.UsageType;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.Test;

class PromotionDetailVmTest {

    @Test
    void fromModel_singleArg_mapsAllFields() {
        Instant now = Instant.now();
        Instant endDate = now.plus(30, ChronoUnit.DAYS);

        Promotion promotion = Promotion.builder()
            .id(1L)
            .name("Summer Sale")
            .slug("summer-sale")
            .description("Summer promotion")
            .couponCode("SUMMER50")
            .usageLimit(100)
            .usageCount(25)
            .discountType(DiscountType.PERCENTAGE)
            .applyTo(ApplyTo.PRODUCT)
            .usageType(UsageType.LIMITED)
            .discountPercentage(50L)
            .discountAmount(0L)
            .minimumOrderPurchaseAmount(200L)
            .isActive(true)
            .startDate(now)
            .endDate(endDate)
            .build();

        PromotionDetailVm result = PromotionDetailVm.fromModel(promotion);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Summer Sale");
        assertThat(result.slug()).isEqualTo("summer-sale");
        assertThat(result.description()).isEqualTo("Summer promotion");
        assertThat(result.couponCode()).isEqualTo("SUMMER50");
        assertThat(result.usageLimit()).isEqualTo(100);
        assertThat(result.usageCount()).isEqualTo(25);
        assertThat(result.discountType()).isEqualTo(DiscountType.PERCENTAGE);
        assertThat(result.applyTo()).isEqualTo(ApplyTo.PRODUCT);
        assertThat(result.usageType()).isEqualTo(UsageType.LIMITED);
        assertThat(result.discountPercentage()).isEqualTo(50L);
        assertThat(result.discountAmount()).isEqualTo(0L);
        assertThat(result.minimumOrderPurchaseAmount()).isEqualTo(200L);
        assertThat(result.isActive()).isTrue();
        assertThat(result.startDate()).isEqualTo(now);
        assertThat(result.endDate()).isEqualTo(endDate);
        assertThat(result.brands()).isNull();
        assertThat(result.categories()).isNull();
        assertThat(result.products()).isNull();
    }

    @Test
    void fromModel_withBrandsAndCategoriesAndProducts_mapsAllFields() {
        Instant now = Instant.now();
        Instant endDate = now.plus(30, ChronoUnit.DAYS);

        Promotion promotion = Promotion.builder()
            .id(2L)
            .name("Winter Sale")
            .slug("winter-sale")
            .description("Winter promotion")
            .couponCode("WINTER30")
            .usageLimit(200)
            .usageCount(50)
            .discountType(DiscountType.FIXED)
            .applyTo(ApplyTo.BRAND)
            .usageType(UsageType.UNLIMITED)
            .discountPercentage(0L)
            .discountAmount(300L)
            .minimumOrderPurchaseAmount(500L)
            .isActive(false)
            .startDate(now)
            .endDate(endDate)
            .build();

        List<BrandVm> brands = List.of(new BrandVm(1L, "Nike", "nike", true));
        List<CategoryGetVm> categories = List.of(new CategoryGetVm(1L, "Shoes", "shoes", 0L));
        List<ProductVm> products = List.of();

        PromotionDetailVm result = PromotionDetailVm.fromModel(promotion, brands, categories, products);

        assertThat(result.id()).isEqualTo(2L);
        assertThat(result.name()).isEqualTo("Winter Sale");
        assertThat(result.slug()).isEqualTo("winter-sale");
        assertThat(result.description()).isEqualTo("Winter promotion");
        assertThat(result.couponCode()).isEqualTo("WINTER30");
        assertThat(result.usageLimit()).isEqualTo(200);
        assertThat(result.usageCount()).isEqualTo(50);
        assertThat(result.discountType()).isEqualTo(DiscountType.FIXED);
        assertThat(result.applyTo()).isEqualTo(ApplyTo.BRAND);
        assertThat(result.usageType()).isEqualTo(UsageType.UNLIMITED);
        assertThat(result.discountPercentage()).isEqualTo(0L);
        assertThat(result.discountAmount()).isEqualTo(300L);
        assertThat(result.minimumOrderPurchaseAmount()).isEqualTo(500L);
        assertThat(result.isActive()).isFalse();
        assertThat(result.brands()).hasSize(1);
        assertThat(result.brands().getFirst().name()).isEqualTo("Nike");
        assertThat(result.categories()).hasSize(1);
        assertThat(result.categories().getFirst().name()).isEqualTo("Shoes");
        assertThat(result.products()).isEmpty();
    }

    @Test
    void fromModel_withNullCollections_keepsNullCollections() {
        Promotion promotion = Promotion.builder()
            .id(3L)
            .name("Flash Sale")
            .slug("flash-sale")
            .discountType(DiscountType.PERCENTAGE)
            .applyTo(ApplyTo.CATEGORY)
            .build();

        PromotionDetailVm result = PromotionDetailVm.fromModel(promotion, null, null, null);

        assertThat(result.id()).isEqualTo(3L);
        assertThat(result.brands()).isNull();
        assertThat(result.categories()).isNull();
        assertThat(result.products()).isNull();
    }

    @Test
    void builder_toBuilder_createsModifiedCopy() {
        PromotionDetailVm original = PromotionDetailVm.builder()
            .id(1L)
            .name("Original")
            .slug("original")
            .isActive(true)
            .build();

        PromotionDetailVm modified = original.toBuilder()
            .name("Modified")
            .isActive(false)
            .build();

        assertThat(modified.id()).isEqualTo(1L);
        assertThat(modified.name()).isEqualTo("Modified");
        assertThat(modified.isActive()).isFalse();
        // original unchanged
        assertThat(original.name()).isEqualTo("Original");
        assertThat(original.isActive()).isTrue();
    }
}
