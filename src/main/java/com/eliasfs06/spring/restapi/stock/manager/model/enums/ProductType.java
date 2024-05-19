package com.eliasfs06.spring.restapi.stock.manager.model.enums;

public enum ProductType {
    HYGIENE("Hygiene"),
    OFFICE_SUPPLIES("Office Supplies"),
    ELECTRONICS("Electronics"),
    BEAUTY("Beauty"),
    HOUSEHOLD("Household"),
    CLOTHING("Clothing"),
    SPORTS("Sports"),
    FOOD("Food"),
    TOYS("Toys"),
    BOOKS("Books"),
    FURNITURE("Furniture"),
    JEWELRY("Jewelry"),
    AUTOMOTIVE("Automotive"),
    PET_SUPPLIES("Pet Supplies"),
    HEALTH("Health"),
    BABY("Baby"),
    MUSIC("Music"),
    ART("Art"),
    GARDEN("Garden"),
    CRAFTS("Crafts");

    private final String description;

    ProductType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

