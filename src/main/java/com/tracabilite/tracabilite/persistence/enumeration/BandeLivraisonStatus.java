package com.tracabilite.tracabilite.persistence.enumeration;

public enum BandeLivraisonStatus {
    NEW("NOUVEAU"),
    PROCESS("EN_COUR"),
    TERMINETED("TERMINE");

    private final String frenchTranslation;

    BandeLivraisonStatus(String frenchTranslation) {
        this.frenchTranslation = frenchTranslation;
    }

    public String getFrenchTranslation() {
        return frenchTranslation;
    }
}
