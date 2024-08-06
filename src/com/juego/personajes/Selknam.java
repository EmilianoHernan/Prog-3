package com.juego.personajes;

import com.juego.Dados;
import com.juego.enums.Raza;
import com.juego.interfaces.Personaje;

public class Selknam extends PJ_Base{
    public Selknam(String nombre, int vida, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        super(nombre, vida, Raza.SELKNAM, velocidad, destreza, fuerza, nivel, armadura);
    }

    public Selknam(Selknam selknam) {
        super(selknam);
    }

    @Override
    public void atacar(Personaje enemigo, int numCaras, int staminaCost) {
        super.atacar(enemigo, numCaras, staminaCost);
    }

    @Override
    public PJ_Base copia() {
        return new Selknam(this);
    }
}

