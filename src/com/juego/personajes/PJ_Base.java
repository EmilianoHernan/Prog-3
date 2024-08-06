package com.juego.personajes;

import com.juego.Dados;
import com.juego.enums.Raza;
import com.juego.interfaces.Personaje;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public abstract class PJ_Base implements Personaje {
    private String nombre;
    private int vida;
    private int vidaInicial;
    private int stamina;
    private int staminaInicial;
    private Raza raza;
    private int velocidad;
    private int destreza;
    private int fuerza;
    private int nivel;
    private int armadura;
    private int dañoReciente;
    private Dados dados;
    private int ultimoAtaque;

    public PJ_Base(String nombre, int vida, Raza raza, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        this.nombre = nombre;
        this.vida = 125;
        this.vidaInicial = 125;
        this.stamina = 100;
        this.staminaInicial = 100;
        this.raza = raza;
        this.velocidad = velocidad;
        this.destreza = destreza;
        this.fuerza = fuerza;
        this.nivel = nivel;
        this.armadura = armadura;
        this.dados = new Dados();
        this.ultimoAtaque = 0;
    }


    public PJ_Base(PJ_Base original) {
        this.nombre = original.nombre;
        this.vida = original.vida;
        this.vidaInicial = original.vidaInicial;
        this.stamina = original.stamina;
        this.staminaInicial = original.staminaInicial;
        this.raza = original.raza;
        this.velocidad = original.velocidad;
        this.destreza = original.destreza;
        this.fuerza = original.fuerza;
        this.nivel = original.nivel;
        this.armadura = original.armadura;
        this.dañoReciente = original.dañoReciente;
        this.ultimoAtaque = original.ultimoAtaque;
    }


    public abstract PJ_Base copia();

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public int getVida() {
        return vida;
    }

    @Override
    public void setVida(int vida) {
        this.vida = vida;
    }

    @Override
    public int getVidaInicial() {
        return vidaInicial;
    }

    public void recuperarVida(int cantidad) {
        this.vida += cantidad;
        if (this.vida > this.vidaInicial) this.vida = this.vidaInicial;
    }
    public void recuperarStamina(int cantidad) {
        this.stamina += cantidad;
        if (this.stamina > this.staminaInicial) this.stamina = this.staminaInicial;
    }
    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStaminaInicial() {
        return staminaInicial;
    }

    public int getUltimoAtaque() {
        return ultimoAtaque;
    }

    public void setUltimoAtaque(int valor) {
        this.ultimoAtaque = valor;
    }

    @Override
    public Raza getRaza() {
        return raza;
    }
    public int getDañoReciente() {
        return dañoReciente;
    }

    @Override
    public int getVelocidad() {
        return velocidad;
    }

    @Override
    public int getDestreza() {
        return destreza;
    }

    @Override
    public int getFuerza() {
        return fuerza;
    }

    @Override
    public int getNivel() {
        return nivel;
    }

    @Override
    public int getArmadura() {
        return armadura;
    }

    public Dados getDados() {
        return dados;
    }

    @Override
    public boolean estaVivo() {
        return vida > 0;
    }

    @Override
    public void recibirDamage(int damage) {
        vida = Math.max(vida - damage, 0);
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public void setArmadura(int armadura) {
        this.armadura = armadura;
    }
    public void setDañoReciente(int dañoReciente) {
        this.dañoReciente = dañoReciente;
    }


    @Override
    public void atacar(Personaje enemigo, int numCaras, int staminaCost) {
        if (stamina >= staminaCost) {
            Dados dados = new Dados(numCaras);
            int[] tiradas = dados.tirarDados();

            int suma = 0;
            for (int tirada : tiradas) {
                suma += tirada;
            }

            if (dados.getNumCaras() == 8) {
                int curacion = suma;
                vida = Math.min(vidaInicial, vida + curacion);
                System.out.println(getNombre() + " se ha curado " + curacion + " puntos de vida.");
            } else {
                int dañoProvocado = suma + calcularDañoAdicional(enemigo);
                int vidaRestante = enemigo.getVida() - dañoProvocado;
                enemigo.setVida(Math.max(0, vidaRestante));
                setDañoReciente(dañoProvocado);

                System.out.println(getNombre() + " ha causado " + dañoProvocado + " puntos de daño a " + enemigo.getNombre() + ".");
                if (vidaRestante <= 0) {
                    System.out.println("¡El personaje " + enemigo.getNombre() + " ha sido derrotado!");
                } else {
                    System.out.println("Vida restante de " + enemigo.getNombre() + ": " + vidaRestante);
                }
            }


            stamina -= staminaCost;
        } else {
            System.out.println(getNombre() + " no tiene suficiente stamina para usar ese dado.");
        }
    }

    private int calcularDañoAdicional(Personaje enemigo) {
        int dañoAdicional = 0;
        if (enemigo instanceof PJ_Base) {
            PJ_Base enemigoBase = (PJ_Base) enemigo;
            if (getFuerza() > enemigoBase.getFuerza()) {
                dañoAdicional++;
            }
            if (getDestreza() > enemigoBase.getDestreza()) {
                dañoAdicional++;
            }
            if (getArmadura() > enemigoBase.getArmadura()) {
                dañoAdicional++;
            }
            if (getVelocidad() > enemigoBase.getVelocidad()) {
                dañoAdicional++;
            }
            if (getNivel() > enemigoBase.getNivel()) {
                dañoAdicional++;
            }
        }
        return dañoAdicional;
    }
}
