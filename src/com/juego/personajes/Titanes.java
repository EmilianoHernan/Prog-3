package com.juego.personajes;

import com.juego.enums.Raza;
import com.juego.interfaces.Personaje;

public class Titanes extends PJ_Base{
    public Titanes(String nombre, int vida, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        super(nombre, vida, Raza.TITANES, velocidad, destreza, fuerza, nivel, armadura);
    }

    public Titanes(Titanes titanes) {
        super(titanes);
    }

    @Override
    public void atacar(Personaje enemigo, int numCaras, int staminaCost) {
        super.atacar(enemigo, numCaras, staminaCost);
    }

    @Override
    public PJ_Base copia() {
        return new Titanes(this);
    }
}

