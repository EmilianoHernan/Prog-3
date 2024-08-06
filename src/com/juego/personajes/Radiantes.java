package com.juego.personajes;

import com.juego.enums.Raza;
import com.juego.interfaces.Personaje;

import java.util.Random;
import java.util.Scanner;

public class Radiantes extends PJ_Base {
    private Random random = new Random();

    public Radiantes(String nombre, int vida, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        super(nombre, vida, Raza.RADIANTES, velocidad, destreza, fuerza, nivel, armadura);
    }

    public Radiantes(Radiantes radiantes) {
        super(radiantes);
    }

    @Override
    public void atacar(Personaje enemigo, int numCaras, int staminaCost) {
        super.atacar(enemigo, numCaras, staminaCost);
    }

    @Override
    public PJ_Base copia() {
        return new Radiantes(this);
    }
}


