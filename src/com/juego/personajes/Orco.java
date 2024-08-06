package com.juego.personajes;

import com.juego.Dados;
import com.juego.enums.Raza;
import com.juego.interfaces.Personaje;

public class Orco extends PJ_Base{
    public Orco(String nombre, int vida, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        super(nombre, vida, Raza.ORCO, velocidad, destreza, fuerza, nivel, armadura);
    }

    public Orco(Orco orco) {
        super(orco);
    }

    @Override
    public void atacar(Personaje enemigo, int numCaras, int staminaCost) {
        super.atacar(enemigo, numCaras, staminaCost);
    }

    @Override
    public PJ_Base copia() {
        return new Orco(this);
    }
}

