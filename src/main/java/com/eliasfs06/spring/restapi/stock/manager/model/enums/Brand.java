package com.eliasfs06.spring.restapi.stock.manager.model.enums;

public enum Brand {
    COLGATE("Colgate"),
    DOVE("Dove"),
    LISTERINE("Listerine"),
    SAMSUNG("Samsung"),
    APPLE("Apple"),
    HP("HP"),
    BIC("Bic"),
    SHARPIE("Sharpie"),
    LOREAL("L'Oréal"),
    JOHNSON_AND_JOHNSON("Johnson & Johnson"),
    CREST("Crest"),
    NIVEA("Nivea"),
    GILLETTE("Gillette"),
    ORAL_B("Oral-B"),
    TIDE("Tide"),
    LYSOL("Lysol"),
    POST_IT("Post-it"),
    CANON("Canon"),
    SONY("Sony"),
    MICROSOFT("Microsoft");

    private final String name;

    Brand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
