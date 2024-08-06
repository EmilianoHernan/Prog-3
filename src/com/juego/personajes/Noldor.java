package com.juego.personajes;

import com.juego.Dados;
import com.juego.enums.Raza;
import com.juego.interfaces.Personaje;

public class Noldor extends PJ_Base {

    public Noldor(String nombre, int vida, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        super(nombre, vida, Raza.NOLDOR, velocidad, destreza, fuerza, nivel, armadura);
    }

    public Noldor(Noldor noldor) {
        super(noldor);
    }

    @Override
    public void atacar(Personaje enemigo, int numCaras, int staminaCost) {
        super.atacar(enemigo, numCaras, staminaCost);
    }

    @Override
    public PJ_Base copia() {
        return new Noldor(this);
    }
}
