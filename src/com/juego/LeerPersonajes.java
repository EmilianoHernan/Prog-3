package com.juego;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.juego.personajes.PersonajeStats;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class LeerPersonajes {
    public static List<PersonajeStats> leerPersonajesDesdeJSON(String archivo) throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(archivo);
        Type tipoLista = new TypeToken<List<PersonajeStats>>() {}.getType();
        List<PersonajeStats> personajes = gson.fromJson(reader, tipoLista);
        reader.close();
        return personajes;
    }
}
