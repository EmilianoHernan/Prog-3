package com.juego;

public enum TipoDado {
    DADO_6_CARAS(6),
    DADO_12_CARAS(12),
    DADO_20_CARAS(20),
    DADO_8_CARAS_CURACION(8),
    DADO_30_CARAS_ESPECIAL(30);

    private final int numCaras;

    TipoDado(int numCaras) {
        this.numCaras = numCaras;
    }

    public int getNumCaras() {
        return numCaras;
    }
}
