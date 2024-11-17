package com.tracabilite.tracabilite.persistence.enumeration;

public enum BandeCommandeStatus {
    NEW("NOUVEAU"),
    PROCESS("EN_COUR"),
    TERMINETED("TERMINE");

    private final String frenchTranslation;

    BandeCommandeStatus(String frenchTranslation) {
        this.frenchTranslation = frenchTranslation;
    }

    public String getFrenchTranslation() {
        return frenchTranslation;
    }
}
