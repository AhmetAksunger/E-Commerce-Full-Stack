package com.ahmetaksunger.ecommerce.exception;

public enum ExceptionMessages {

    ACCOUNT_DELETION_NOT_ALLOWED("User is not allowed to delete this account."),
    ADDRESS_UPDATE_NOT_ALLOWED("User is not allowed to update this address"),
    ADDRESS_DELETION_NOT_ALLOWED("User is not allowed to delete this address"),
    PAYMENT_CREATION_NOT_ALLOWED("User is not allowed to create a payment detail"),
    PAYMENT_DETAIL_DELETION_NOT_ALLOWED("User is not allowed to delete this payment detail"),
    PRODUCT_UPDATE_NOT_ALLOWED("User is not allowed to update this product"),
    PRODUCT_DELETION_NOT_ALLOWED("User is not allowed to delete this product"),
    CART_DELETION_NOT_ALLOWED("User is not allowed to delete this cart"),
    UNAUTHORIZED("Unauthorized"),
    ADDRESS_NOT_FOUND("Address not found"),
    PAYMENT_DETAIL_NOT_FOUND("Payment detail not found"),
    CATEGORY_NOT_FOUND("Category not found"),
    PRODUCT_NOT_FOUND("Product not found"),
    CART_NOT_FOUND("Cart not found"),
    CART_ITEM_NOT_FOUND("Cart item not found"),
    USER_ALREADY_HAS_CART("User already has cart"),
    INSUFF_PRODUCT_QUANTITY("Insufficient product quantity"),
    INSUFF_REVENUE("Seller doesn't have enough revenue to withdraw."),
    INVALID_WITHDRAW_AMOUNT("Invalid withdraw amount"),
    INVALID_CART("Invalid cart"),
    CART_IS_EMPTY("Cart is empty"),
    CART_UPDATE_NOT_ALLOWED("User is not allowed to update this cart"),
    INVALID_PRODUCT("Invalid product");

    private String message;
    public String message(){
        return this.message;
    }
    ExceptionMessages(String message) {
        this.message= message;
    }
}
