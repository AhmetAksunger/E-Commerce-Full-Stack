package com.ahmetaksunger.ecommerce.exception;

public enum ExceptionMessages {

    ACCOUNT_DELETION_NOT_ALLOWED("User is not allowed to delete this account."),
    ADDRESS_UPDATE_NOT_ALLOWED("User is not allowed to update this address"),
    ADDRESS_DELETION_NOT_ALLOWED("User is not allowed to delete this address"),
    PAYMENT_CREATION_NOT_ALLOWED("User is not allowed to create a payment detail"),
    PAYMENT_DETAIL_DELETION_NOT_ALLOWED("User is not allowed to delete this payment detail"),
    PRODUCT_UPDATE_NOT_ALLOWED("User is not allowed to update this product"),
    UNAUTHORIZED("Unauthorized"),
    ADDRESS_NOT_FOUND("Address not found"),
    PAYMENT_DETAIL_NOT_FOUND("Payment detail not found"),
    CATEGORY_NOT_FOUND("Category not found"),
    PRODUCT_NOT_FOUND("Product not found");

    private String message;

    public String message(){
        return this.message;
    }
    ExceptionMessages(String message) {
        this.message= message;
    }
}
