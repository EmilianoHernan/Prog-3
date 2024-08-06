package com.juego.personajes;

import com.juego.Dados;
import com.juego.enums.Raza;
import com.juego.interfaces.Personaje;

public class Dunedain extends PJ_Base{
    public Dunedain(String nombre, int vida, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        super(nombre, vida, Raza.DUNEDAIN, velocidad, destreza, fuerza, nivel, armadura);
    }

    public Dunedain(Dunedain dunedain) {
        super(dunedain);
    }

    @Override
    public void atacar(Personaje enemigo, int numCaras, int staminaCost) {
        super.atacar(enemigo, numCaras, staminaCost);
    }

    @Override
    public PJ_Base copia() {
        return new Dunedain(this);
    }
}
