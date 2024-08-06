package com.juego;

import java.util.Random;

public class Dados {
    private static final int NUM_DADOS = 2;
    private int numCaras;
    private int[] tiradas;

    // Constructor
    public Dados(int numCaras) {
        this.numCaras = numCaras;
        this.tiradas = new int[NUM_DADOS];
    }


    public Dados() {
        this(6);
    }


    public void setNumCaras(int numCaras) {
        this.numCaras = numCaras;
    }


    public int[] tirarDados() {
        Random random = new Random();
        for (int i = 0; i < tiradas.length; i++) {
            tiradas[i] = random.nextInt(numCaras) + 1;
        }

        for (int resultado : tiradas) {
            System.out.println("[ " + resultado + " ]");
        }
        return tiradas;
    }


    public int getNumCaras() {
        return numCaras;
    }
}

