package com.juego.interfaces;

import com.juego.Dados;
import com.juego.enums.Raza;

public interface Personaje {
    void atacar(Personaje enemigo, int numCaras, int staminaCost);
    void recibirDamage(int damage);
    boolean estaVivo();
    String getNombre();

    int getVidaInicial();

    Raza getRaza();
    int getVida();
    void setVida(int vida);
    int getVelocidad();
    int getDestreza();
    int getFuerza();
    int getNivel();
    int getArmadura();
    void setFuerza(int fuerza);
    void setArmadura(int armadura);

    Dados getDados();

    int getStamina();

    int getStaminaInicial();
    void setStamina(int stamina);

}
