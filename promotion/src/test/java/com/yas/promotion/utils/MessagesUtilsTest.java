package com.yas.promotion.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MessagesUtilsTest {

    @Test
    void getMessage_whenKeyExists_returnsFormattedMessage() {
        String result = MessagesUtils.getMessage("PROMOTION_NOT_FOUND", "test-promo");
        assertThat(result).isEqualTo("Promotion test-promo is not found");
    }

    @Test
    void getMessage_whenKeyExists_withMultipleArgs() {
        // PROMOTION_IN_USE has one placeholder
        String result = MessagesUtils.getMessage("PROMOTION_IN_USE", 123L);
        assertThat(result).isEqualTo("Can't delete promotion 123 because it is in use");
    }

    @Test
    void getMessage_whenKeyDoesNotExist_returnsKeyAsMessage() {
        String result = MessagesUtils.getMessage("NON_EXISTENT_KEY");
        assertThat(result).isEqualTo("NON_EXISTENT_KEY");
    }

    @Test
    void getMessage_whenKeyDoesNotExist_withArgs_returnsKeyWithFormatting() {
        String result = MessagesUtils.getMessage("UNKNOWN_ERROR_CODE", "arg1");
        assertThat(result).isEqualTo("UNKNOWN_ERROR_CODE");
    }

    @Test
    void getMessage_couponCodeAlreadyExisted_returnsCorrectMessage() {
        String result = MessagesUtils.getMessage("COUPON_CODE_ALREADY_EXISTED", "CODE123");
        assertThat(result).isEqualTo("The coupon code CODE123 is already existed");
    }

    @Test
    void getMessage_exhaustedUsageQuantity_returnsCorrectMessage() {
        String result = MessagesUtils.getMessage("EXHAUSTED_USAGE_QUANTITY");
        assertThat(result).isEqualTo("Exhausted usage quantity");
    }

    @Test
    void getMessage_invalidMinimumOrderPurchaseAmount_returnsCorrectMessage() {
        String result = MessagesUtils.getMessage("INVALID_MINIMUM_ORDER_PURCHASE_AMOUNT");
        assertThat(result).isEqualTo("Invalid minimum order purchase amount");
    }

    @Test
    void getMessage_productNotFoundToApplyPromotion_returnsCorrectMessage() {
        String result = MessagesUtils.getMessage("PRODUCT_NOT_FOUND_TO_APPLY_PROMOTION");
        assertThat(result).isEqualTo("Not found product to apply promotion");
    }

    @Test
    void getMessage_accessDenied_returnsCorrectMessage() {
        String result = MessagesUtils.getMessage("ACCESS_DENIED");
        assertThat(result).isEqualTo("Access denied");
    }
}
