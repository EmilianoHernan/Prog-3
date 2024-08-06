package com.juego;

import com.juego.enums.Raza;
import com.juego.interfaces.Personaje;
import com.juego.personajes.*;

import java.util.ArrayList;
import java.util.List;

public class GeneradorPersonajes {

    public static List<Personaje> generarPersonajesDesdeData(List<PersonajeStats> personajeDataList) {
        List<Personaje> personajes = new ArrayList<>();
        for (PersonajeStats data : personajeDataList) {
            String nombre = data.getNombre();
            int vida = data.getSalud();
            int velocidad = data.getVelocidad();
            int destreza = data.getDestreza();
            int fuerza = data.getFuerza();
            int nivel = data.getNivel();
            int armadura = data.getArmadura();
            Raza raza = Raza.valueOf(data.getRaza().toUpperCase());

            switch (raza) {
                case DUNEDAIN:
                    personajes.add(new Dunedain(nombre, vida, velocidad, destreza, fuerza, nivel, armadura));
                    break;
                case NOLDOR:
                    personajes.add(new Noldor(nombre, vida, velocidad, destreza, fuerza, nivel, armadura));
                    break;
                case ORCO:
                    personajes.add(new Orco(nombre, vida, velocidad, destreza, fuerza, nivel, armadura));
                    break;
                case CONDENADOS:
                    personajes.add(new Condenados(nombre, vida, velocidad, destreza, fuerza, nivel, armadura));
                    break;
                case ENANOS:
                    personajes.add(new Enanos(nombre, vida, velocidad, destreza, fuerza, nivel, armadura));
                    break;
                case SELKNAM:
                    personajes.add(new Selknam(nombre, vida, velocidad, destreza, fuerza, nivel, armadura));
                    break;
                case TITANES:
                    personajes.add(new Titanes(nombre, vida, velocidad, destreza, fuerza, nivel, armadura));
                    break;
                case RADIANTES:
                    personajes.add(new Radiantes(nombre, vida, velocidad, destreza, fuerza, nivel, armadura));
                    break;
            }
        }
        return personajes;
    }

    public static PJ_Base crearPersonaje(String nombre, int vida, Raza raza, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        switch (raza) {
            case DUNEDAIN:
                return new Dunedain(nombre, vida, velocidad, destreza, fuerza, nivel, armadura);
            case NOLDOR:
                return new Noldor(nombre, vida, velocidad, destreza, fuerza, nivel, armadura);
            case ENANOS:
                return new Enanos(nombre, vida, velocidad, destreza, fuerza, nivel, armadura);
            case ORCO:
                return new Orco(nombre, vida, velocidad, destreza, fuerza, nivel, armadura);
            case CONDENADOS:
                return new Condenados(nombre, vida, velocidad, destreza, fuerza, nivel, armadura);
            case SELKNAM:
                return new Selknam(nombre, vida, velocidad, destreza, fuerza, nivel, armadura);
            default:
                throw new IllegalArgumentException("Raza no reconocida: " + raza);
        }
    }
}