package com.juego.personajes;

import com.juego.enums.Raza;
import com.juego.interfaces.Personaje;

public class Condenados extends PJ_Base{
    public Condenados(String nombre, int vida, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        super(nombre, vida, Raza.CONDENADOS, velocidad, destreza, fuerza, nivel, armadura);
    }

    public Condenados(Condenados condenados) {
        super(condenados);
    }

    @Override
    public void atacar(Personaje enemigo, int numCaras, int staminaCost) {
        super.atacar(enemigo, numCaras, staminaCost);
    }

    @Override
    public PJ_Base copia() {
        return new Condenados(this);
    }
}

