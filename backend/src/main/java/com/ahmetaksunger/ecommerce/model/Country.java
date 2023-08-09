package com.ahmetaksunger.ecommerce.model;

public enum Country {

    TURKEY("Turkey"),
    UNITED_STATES("United States"),
    UNITED_KINGDOM("United Kingdom"),
    GERMANY("Germany"),
    FRANCE("France"),
    SPAIN("Spain"),
    ITALY("Italy"),
    JAPAN("Japan"),
    CHINA("China"),
    INDIA("India"),
    BRAZIL("Brazil"),
    CANADA("Canada"),
    AUSTRALIA("Australia");
    private String value;

    Country(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
