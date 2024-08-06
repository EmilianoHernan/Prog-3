package com.juego.personajes;

import com.juego.Dados;
import com.juego.enums.Raza;
import com.juego.interfaces.Personaje;

public class Enanos extends PJ_Base{
    public Enanos(String nombre, int vida, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        super(nombre, vida, Raza.ENANOS, velocidad, destreza, fuerza, nivel, armadura);
    }

    public Enanos(Enanos enanos) {
        super(enanos);
    }

    @Override
    public void atacar(Personaje enemigo, int numCaras, int staminaCost) {
        super.atacar(enemigo, numCaras, staminaCost);
    }

    @Override
    public PJ_Base copia() {
        return new Enanos(this);
    }
}
