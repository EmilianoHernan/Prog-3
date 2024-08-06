package com.juego;

import com.juego.enums.Raza;
import com.juego.interfaces.Personaje;
import com.juego.personajes.PJ_Base;
import com.juego.personajes.PersonajeStats;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    private static List<Personaje> personajesRadiantes = new ArrayList<>();
    private static List<Personaje> personajesTitanes = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean esTurnoDelUsuario = true;
    private static Personaje Ylluvatar;
    private static Personaje ODIUM;
    private static List<Personaje> personajesRadiantesOriginales = new ArrayList<>();
    private static List<Personaje> personajesTitanesOriginales = new ArrayList<>();
    private static Personaje YlluvatarOriginal;
    private static Personaje ODIUMOriginal;




    public static void main(String[] args) {
        cargarPersonajes();
        mostrarMenu();
    }

    private static void cargarPersonajes() {
        List<PersonajeStats> personajeStatsList = null;
        try {
            personajeStatsList = LeerPersonajes.leerPersonajesDesdeJSON("src/personajes.json");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        List<Personaje> personajes = GeneradorPersonajes.generarPersonajesDesdeData(personajeStatsList);

        for (Personaje personaje : personajes) {
            if (personaje.getNombre().equals("Ylluvatar")) {
                Ylluvatar = personaje;
                YlluvatarOriginal = ((PJ_Base) personaje).copia();
            } else if (personaje.getNombre().equals("ODIUM")) {
                ODIUM = personaje;
                ODIUMOriginal = ((PJ_Base) personaje).copia();
            } else if (personaje.getRaza() == Raza.DUNEDAIN || personaje.getRaza() == Raza.NOLDOR || personaje.getRaza() == Raza.ENANOS) {
                personajesRadiantes.add(personaje);
                personajesRadiantesOriginales.add(((PJ_Base) personaje).copia());
            } else if (personaje.getRaza() == Raza.ORCO || personaje.getRaza() == Raza.CONDENADOS || personaje.getRaza() == Raza.SELKNAM) {
                personajesTitanes.add(personaje);
                personajesTitanesOriginales.add(((PJ_Base) personaje).copia());
            }
        }
    }

    private static void leerLogs() {
        try (BufferedReader br = new BufferedReader(new FileReader("logs.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de logs: " + e.getMessage());
        }
    }

    private static void borrarLogs() {
        File file = new File("logs.txt");
        if (file.delete()) {
            System.out.println("Archivo de logs borrado exitosamente.");
        } else {
            System.out.println("No se pudo borrar el archivo de logs.");
        }
    }

    private static void registrarLog(String mensaje) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("logs.txt", true))) {
            bw.write(mensaje);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo de logs: " + e.getMessage());
        }
    }

    private static void mostrarMenu() {
        while (true) {
            try {
                System.out.println("Menú Principal:");
                System.out.println("1. Comenzar a jugar");
                System.out.println("2. Crear personajes y jugar");
                System.out.println("3. Leer logs de partidas jugadas");
                System.out.println("4. Borrar archivo de logs");
                System.out.println("5. Salir");

                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        restablecerPersonajes();
                        comenzarAJugar();
                        break;
                    case 2:
                        restablecerPersonajes();
                        crearPersonajes();
                        break;
                    case 3:
                        leerLogs();
                        break;
                    case 4:
                        borrarLogs();
                        break;
                    case 5:
                        System.out.println("Saliendo del juego...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduce un número válido.");
                scanner.next();
            }
        }
    }

    private static void comenzarAJugar() {
        System.out.println("Elige tu bando:");
        System.out.println("1. Radiantes");
        System.out.println("2. Titanes");

        int bandoSeleccionado = scanner.nextInt();
        Raza bando;

        switch (bandoSeleccionado) {
            case 1:
                bando = Raza.RADIANTES;
                break;
            case 2:
                bando = Raza.TITANES;
                break;
            default:
                System.out.println("Opción no válida. Inténtalo de nuevo.");
                comenzarAJugar();
                return;
        }

        List<Personaje> buenos = obtenerPersonajesAleatorios(bando == Raza.RADIANTES ? personajesRadiantes : personajesTitanes, 3);
        List<Personaje> malos = obtenerPersonajesAleatorios(bando == Raza.RADIANTES ? personajesTitanes : personajesRadiantes, 3);

        if (bando == Raza.RADIANTES && Ylluvatar != null) {
            buenos.add(Ylluvatar);
        } else if (bando == Raza.TITANES && ODIUM != null) {
            malos.add(ODIUM);
        }
        registrarLog(
                "Partida jugada el " + new Date());
        iniciarCombate(buenos, malos, bando);
    }

    private static void restablecerPersonajes() {
        personajesRadiantes.clear();
        personajesTitanes.clear();
        for (Personaje original : personajesRadiantesOriginales) {
            personajesRadiantes.add(((PJ_Base) original).copia());
        }
        for (Personaje original : personajesTitanesOriginales) {
            personajesTitanes.add(((PJ_Base) original).copia());
        }
        Ylluvatar = ((PJ_Base) YlluvatarOriginal).copia();
        ODIUM = ((PJ_Base) ODIUMOriginal).copia();
    }

    private static void crearPersonajes() {
        System.out.println("Elige tu bando:");
        System.out.println("1. Radiantes");
        System.out.println("2. Titanes");

        int bandoSeleccionado = scanner.nextInt();
        Raza bando;

        switch (bandoSeleccionado) {
            case 1:
                bando = Raza.RADIANTES;
                break;
            case 2:
                bando = Raza.TITANES;
                break;
            default:
                System.out.println("Opción no válida. Inténtalo de nuevo.");
                return;
        }

        List<Personaje> nuevosPersonajes = new ArrayList<>();
        int personajesCreados = 0;
        final int NUM_PERSONAJES = 3;

        while (personajesCreados < NUM_PERSONAJES) {
            System.out.print("Ingresa el nombre del personaje " + (personajesCreados + 1) + ": ");
            String nombre = scanner.next();
            System.out.print("Ingresa la vida del personaje: ");
            int vida = scanner.nextInt();
            System.out.print("Ingresa la velocidad del personaje: ");
            int velocidad = scanner.nextInt();
            System.out.print("Ingresa la destreza del personaje: ");
            int destreza = scanner.nextInt();
            System.out.print("Ingresa la fuerza del personaje: ");
            int fuerza = scanner.nextInt();
            System.out.print("Ingresa el nivel del personaje: ");
            int nivel = scanner.nextInt();
            System.out.print("Ingresa la armadura del personaje: ");
            int armadura = scanner.nextInt();

            Raza raza = seleccionarRaza(bando);
            Personaje nuevoPersonaje = GeneradorPersonajes.crearPersonaje(nombre, vida, raza, velocidad, destreza, fuerza, nivel, armadura);
            nuevosPersonajes.add(nuevoPersonaje);

            personajesCreados++;
        }

        List<Personaje> personajesBuenos;
        List<Personaje> personajesMalos;

        if (bando == Raza.RADIANTES) {
            personajesBuenos = nuevosPersonajes;
            personajesMalos = personajesTitanes;
        } else {
            personajesBuenos = personajesRadiantes;
            personajesMalos = nuevosPersonajes;
        }

        iniciarCombate(personajesBuenos, personajesMalos, bando);
    }

    private static Set<Raza> razasSeleccionadas = new HashSet<>();
    private static int personajesCreados = 0;
    private static final int NUM_PERSONAJES = 3;

    private static Raza seleccionarRaza(Raza bando) {
        List<Raza> razasDisponibles;
        if (bando == Raza.RADIANTES) {
            razasDisponibles = new ArrayList<>(List.of(Raza.DUNEDAIN, Raza.NOLDOR, Raza.ENANOS));
        } else {
            razasDisponibles = new ArrayList<>(List.of(Raza.ORCO, Raza.CONDENADOS, Raza.SELKNAM));
        }


        razasDisponibles.removeAll(razasSeleccionadas);


        System.out.println("Elige tu raza:");
        int index = 1;
        for (Raza raza : razasDisponibles) {
            System.out.println(index + ". " + raza);
            index++;
        }


        int opcion = scanner.nextInt();
        if (opcion < 1 || opcion > razasDisponibles.size()) {
            System.out.println("Opción no válida. Inténtalo de nuevo.");
            return seleccionarRaza(bando);
        }


        Raza seleccionada = razasDisponibles.get(opcion - 1);
        razasSeleccionadas.add(seleccionada);
        personajesCreados++;
        return seleccionada;
    }
    private static List<Personaje> obtenerPersonajesAleatorios(List<Personaje> lista, int cantidad) {
        List<Personaje> seleccionados = new ArrayList<>();
        Random random = new Random();
        List<Personaje> listaFiltrada = lista.stream()
                .filter(p -> !p.getNombre().equals("Ylluvatar") && !p.getNombre().equals("ODIUM"))
                .toList();
        while (seleccionados.size() < cantidad) {
            Personaje p = listaFiltrada.get(random.nextInt(listaFiltrada.size()));
            if (!seleccionados.contains(p)) {
                seleccionados.add(p);
            }
        }
        return seleccionados;
    }


    private static void iniciarCombate(List<Personaje> buenos, List<Personaje> malos, Raza bando) {
        System.out.println("Iniciando combate...");
        registrarLog("Iniciando combate...");
        System.out.println("Bando elegido: " + bando);
        registrarLog("Bando elegido: " + bando);
        int ronda = 1;
        int contadorUsuario = 0;
        int contadorEnemigo = 0;
        boolean esTurnoBueno = new Random().nextBoolean();
        boolean primerTurnoOriginal = esTurnoBueno;

        List<Raza> razasDisponibles;
        if (bando == Raza.RADIANTES) {
            razasDisponibles = new ArrayList<>(List.of(Raza.DUNEDAIN, Raza.NOLDOR, Raza.ENANOS));
        } else {
            razasDisponibles = new ArrayList<>(List.of(Raza.ORCO, Raza.CONDENADOS, Raza.SELKNAM));
        }

        List<Personaje> buenosCopia = new ArrayList<>(buenos);
        List<Personaje> malosCopia = new ArrayList<>(malos);

        buenosCopia.removeIf(personaje -> personaje.getNombre().equals("ODIUM"));
        malosCopia.removeIf(personaje -> personaje.getNombre().equals("Ylluvatar"));

        while (contadorUsuario < 2 && contadorEnemigo < 2) {
            if (buenosCopia.isEmpty() || malosCopia.isEmpty()) {
                System.out.println("No quedan personajes suficientes para continuar el combate.");
                registrarLog("No quedan personajes suficientes para continuar el combate.");
                break;
            }

            esTurnoBueno = !primerTurnoOriginal;
            primerTurnoOriginal = !primerTurnoOriginal;

            Personaje bueno = buenosCopia.remove(0);
            Personaje malo = malosCopia.remove(0);

            Personaje jugador;
            Personaje enemigo;

            if (razasDisponibles.contains(bueno.getRaza())) {
                jugador = bueno;
                enemigo = malo;
            } else {
                jugador = malo;
                enemigo = bueno;
            }

            System.out.println("Jugador: " + jugador.getNombre() + " (Bando: " + bando + ")");
            registrarLog("Jugador: " + jugador.getNombre() + " (Bando: " + bando + ")");
            System.out.println("Enemigo: " + enemigo.getNombre() + " (Bando: " + (bando == Raza.RADIANTES ? Raza.TITANES : Raza.RADIANTES) + ")");
            registrarLog("Enemigo: " + enemigo.getNombre() + " (Bando: " + (bando == Raza.RADIANTES ? Raza.TITANES : Raza.RADIANTES) + ")");

            System.out.println(jugador.getNombre() + " se encuentra con " + enemigo.getNombre() + ", comienza la batalla!");
            registrarLog(jugador.getNombre() + " se encuentra con " + enemigo.getNombre() + ", comienza la batalla!");

            while (jugador.getVida() > 0 && enemigo.getVida() > 0) {
                System.out.println("------------------------------");
                registrarLog("------------------------------");
                System.out.println("RONDA " + ronda);
                registrarLog("RONDA " + ronda);
                System.out.println("------------------------------");
                registrarLog("------------------------------");

                if (esTurnoBueno) {
                    esTurnoDelUsuario = true;
                    realizarAtaque(jugador, enemigo);
                } else {
                    esTurnoDelUsuario = false;
                    realizarAtaque(enemigo, jugador);
                }
                esTurnoBueno = !esTurnoBueno;

                ronda++;
            }

            if (jugador.getVida() > 0) {
                System.out.println("¡" + jugador.getNombre() + " ha ganado!");
                registrarLog("¡" + jugador.getNombre() + " ha ganado!");
                contadorUsuario++;
                System.out.println("Contador de victorias del usuario: " + contadorUsuario);
                registrarLog("Contador de victorias del usuario: " + contadorUsuario);
            } else {
                System.out.println("¡" + enemigo.getNombre() + " ha ganado!");
                registrarLog("¡" + enemigo.getNombre() + " ha ganado!");
                contadorEnemigo++;
                System.out.println("Contador de victorias del enemigo: " + contadorEnemigo);
                registrarLog("Contador de victorias del enemigo: " + contadorEnemigo);
            }

            ronda = 1;
        }

        if (bando == Raza.RADIANTES) {
            if (contadorUsuario >= 2) {
                System.out.println("¡Los Radiantes han ganado el combate!");
                registrarLog("¡Los Radiantes han ganado el combate!");
                if (ODIUM != null) {
                    System.out.println("------------------------------");
                    registrarLog("------------------------------");
                    System.out.println("¡Te enfrentas a Odium en el combate final!");
                    registrarLog("¡Te enfrentas a Odium en el combate final!");
                    iniciarCombateFinal(ODIUM, malos, bando);
                }
            } else {
                System.out.println("Has perdido el combate.");
                registrarLog("Has perdido el combate.");
            }
        } else if (bando == Raza.TITANES) {
            if (contadorUsuario >= 2) {
                System.out.println("¡Los Titanes han ganado el combate!");
                registrarLog("¡Los Titanes han ganado el combate!");
                if (Ylluvatar != null) {
                    System.out.println("------------------------------");
                    registrarLog("------------------------------");
                    System.out.println("¡Te enfrentas a Ylluvatar en el combate final!");
                    registrarLog("¡Te enfrentas a Ylluvatar en el combate final!");
                    iniciarCombateFinal(Ylluvatar, buenos, bando);
                }
            } else {
                System.out.println("Has perdido el combate.");
                registrarLog("Has perdido el combate.");
            }
        }
    }

    private static void iniciarCombateFinal(Personaje finalBoss, List<Personaje> personajesJugador, Raza bando) {

        Personaje jefeJugador = bando == Raza.RADIANTES ? Ylluvatar : ODIUM;
        Personaje jefeEnemigo = bando == Raza.RADIANTES ? ODIUM : Ylluvatar;

        System.out.println("¡Combate final entre " + jefeJugador.getNombre() + " y " + jefeEnemigo.getNombre() + "!");
        registrarLog("¡Combate final entre " + jefeJugador.getNombre() + " y " + jefeEnemigo.getNombre() + "!");

        int ronda = 1;
        boolean esTurnoDelJugador = true;
        while (jefeJugador.getVida() > 0 && jefeEnemigo.getVida() > 0) {
            System.out.println("------------------------------");
            registrarLog("------------------------------");
            System.out.println("RONDA " + ronda);
            registrarLog("RONDA " + ronda);
            System.out.println("------------------------------");
            registrarLog("------------------------------");

            if (esTurnoDelJugador) {
                System.out.println("Turno de " + jefeJugador.getNombre());
                registrarLog("Turno de " + jefeJugador.getNombre());
                realizarAtaque(jefeJugador, jefeEnemigo);
            } else {
                System.out.println("Turno de " + jefeEnemigo.getNombre());
                registrarLog("Turno de " + jefeEnemigo.getNombre());
                realizarAtaqueAutomatico(jefeEnemigo, jefeJugador);
            }

            esTurnoDelJugador = !esTurnoDelJugador;

            ronda++;
        }

        if (jefeJugador.getVida() > 0) {
            System.out.println("¡" + jefeJugador.getNombre() + " Es el Nuevo Campeon Del Mundo");
            registrarLog("¡" + jefeJugador.getNombre() + " Es el Nuevo Campeon Del Mundo");
        } else {
            System.out.println("¡" + jefeEnemigo.getNombre() + " Es el Nuevo Campeon Del Mundo");
            registrarLog("¡" + jefeEnemigo.getNombre() + " Es el Nuevo Campeon Del Mundo");
        }
    }

    private static void realizarAtaqueAutomatico(Personaje atacante, Personaje defensor) {
        if (atacante instanceof PJ_Base && defensor instanceof PJ_Base) {
            PJ_Base pjBaseAtacante = (PJ_Base) atacante;
            PJ_Base pjBaseDefensor = (PJ_Base) defensor;

            System.out.println("Ataca " + atacante.getNombre());
            registrarLog("Ataca " + atacante.getNombre());
            Random random = new Random();
            int numCaras = 6;
            int staminaCost = 0;

            if (pjBaseAtacante.getStamina() > 9) {
                int dadoSeleccionado;
                do {
                    dadoSeleccionado = random.nextInt(4) + 1;

                    switch (dadoSeleccionado) {
                        case 1:
                            numCaras = 6;
                            staminaCost = 0;
                            break;
                        case 2:
                            numCaras = 12;
                            staminaCost = 10;
                            break;
                        case 3:
                            numCaras = 20;
                            staminaCost = 15;
                            break;
                        case 4:
                            numCaras = 30;
                            staminaCost = 50;
                            break;
                    }
                } while (pjBaseAtacante.getStamina() < staminaCost);
            }

            pjBaseAtacante.atacar(pjBaseDefensor, numCaras, staminaCost);
            System.out.println(atacante.getNombre() + " utilizó el dado de " + numCaras + " caras.");
            registrarLog(atacante.getNombre() + " utilizó el dado de " + numCaras + " caras.");
        }
    }

    private static int sumarResultados(int[] resultados) {
        int suma = 0;
        for (int resultado : resultados) {
            suma += resultado;
        }
        return suma;
    }


    private static void realizarAtaque(Personaje atacante, Personaje defensor) {
        Scanner sc = new Scanner(System.in);
        if (atacante instanceof PJ_Base) {
            PJ_Base pjBaseAtacante = (PJ_Base) atacante;
            PJ_Base pjBaseDefensor = (PJ_Base) defensor;

            if (esTurnoDelUsuario) {

                System.out.println(atacante.getNombre() + " ataca a " + defensor.getNombre());
                registrarLog(atacante.getNombre() + " ataca a " + defensor.getNombre());
                System.out.println("Recuerda que tienes " + pjBaseAtacante.getStamina() + " de Stamina para consumir.");
                registrarLog("Recuerda que tienes " + pjBaseAtacante.getStamina() + " de Stamina para consumir.");

                int opcion = 0;
                int numCaras = 6;
                int staminaCost = 0;
                boolean staminaValida = false;

                while (!staminaValida) {
                    System.out.println("Selecciona un dado para atacar:");
                    System.out.println("1- Dado 6 caras - 0 Stamina");
                    System.out.println("2- Dado 12 caras - 10 Stamina");
                    System.out.println("3- Dado 20 caras - 15 Stamina");
                    System.out.println("4- Dado 30 caras - Ataque Alto (quita mucha Stamina) - 50 Stamina");

                    registrarLog("Selecciona un dado para atacar:");
                    registrarLog("1- Dado 6 caras - 0 Stamina");
                    registrarLog("2- Dado 12 caras - 10 Stamina");
                    registrarLog("3- Dado 20 caras - 15 Stamina");
                    registrarLog("4- Dado 30 caras - Ataque Alto (quita mucha Stamina) - 50 Stamina");


                    boolean opcionesEspecialesDisponibles =
                            pjBaseAtacante.getVida() < pjBaseAtacante.getVidaInicial() * 0.4 ||
                                    pjBaseAtacante.getStamina() < pjBaseAtacante.getStaminaInicial() * 0.4;

                    if (opcionesEspecialesDisponibles) {
                        System.out.println("5- Dado 8 caras - Vida");
                        System.out.println("6- Dado 8 caras - Stamina");
                        registrarLog("5- Dado 8 caras - Vida");
                        registrarLog("6- Dado 8 caras - Stamina");
                    }

                    System.out.println("------------------------------");
                    registrarLog("------------------------------");

                    opcion = sc.nextInt();

                    if (opcion == 1) {
                        numCaras = 6;
                        staminaCost = 0;
                        staminaValida = true;
                    } else if (opcion == 2) {
                        numCaras = 12;
                        staminaCost = 10;
                        staminaValida = pjBaseAtacante.getStamina() >= staminaCost;
                    } else if (opcion == 3) {
                        numCaras = 20;
                        staminaCost = 15;
                        staminaValida = pjBaseAtacante.getStamina() >= staminaCost;
                    } else if (opcion == 4) {
                        numCaras = 30;
                        staminaCost = 50;
                        staminaValida = pjBaseAtacante.getStamina() >= staminaCost;
                    } else if (opcion == 5 && opcionesEspecialesDisponibles && pjBaseAtacante.getVida() < pjBaseAtacante.getVidaInicial()) {

                        Dados dadosVida = new Dados(8);
                        int[] resultadosVida = dadosVida.tirarDados();
                        int valorRecuperado = sumarResultados(resultadosVida);
                        pjBaseAtacante.recuperarVida(valorRecuperado);
                        System.out.println(atacante.getNombre() + " ha recuperado " + valorRecuperado + " puntos de vida.");
                        registrarLog(atacante.getNombre() + " ha recuperado " + valorRecuperado + " puntos de vida.");
                        System.out.println("Tienes ahora " + atacante.getVida() + " puntos de vida.");
                        registrarLog("Tienes ahora " + atacante.getVida() + " puntos de vida.");
                        return;
                    } else if (opcion == 6 && opcionesEspecialesDisponibles && pjBaseAtacante.getStamina() < pjBaseAtacante.getStaminaInicial()) {

                        Dados dadosStamina = new Dados(8);
                        int[] resultadosStamina = dadosStamina.tirarDados();
                        int valorRecuperado = sumarResultados(resultadosStamina);
                        pjBaseAtacante.recuperarStamina(valorRecuperado);
                        System.out.println(atacante.getNombre() + " ha recuperado " + valorRecuperado + " puntos de stamina.");
                        registrarLog(atacante.getNombre() + " ha recuperado " + valorRecuperado + " puntos de stamina.");
                        System.out.println("Tienes ahora " + atacante.getStamina() + " puntos de Stamina.");
                        registrarLog("Tienes ahora " + atacante.getStamina() + " puntos de Stamina.");
                        return;
                    } else {
                        System.out.println("Opción inválida. Vuelve a intentar.");
                        registrarLog("Opción inválida. Vuelve a intentar.");
                    }

                    if (!staminaValida && opcion >= 2 && opcion <= 4) {
                        System.out.println("No tienes suficiente stamina para usar ese dado. Elige otro ataque.");
                        registrarLog("No tienes suficiente stamina para usar ese dado. Elige otro ataque.");
                    }
                }

                pjBaseAtacante.atacar(pjBaseDefensor, numCaras, staminaCost);
                registrarLog(atacante.getNombre() + " atacó a " + defensor.getNombre() + " con un dado de " + numCaras + " caras, costo de stamina: " + staminaCost);

            } else {
                System.out.println("Ataca " + atacante.getNombre());
                registrarLog("Ataca " + atacante.getNombre());
                Random random = new Random();
                int numCaras = 6;
                int staminaCost = 0;

                if (pjBaseAtacante.getStamina() > 9) {
                    int dadoSeleccionado;
                    do {
                        dadoSeleccionado = random.nextInt(4) + 1;

                        switch (dadoSeleccionado) {
                            case 1:
                                numCaras = 6;
                                staminaCost = 0;
                                break;
                            case 2:
                                numCaras = 12;
                                staminaCost = 10;
                                break;
                            case 3:
                                numCaras = 20;
                                staminaCost = 15;
                                break;
                            case 4:
                                numCaras = 30;
                                staminaCost = 50;
                                break;
                        }
                    } while (pjBaseAtacante.getStamina() < staminaCost);
                }

                pjBaseAtacante.atacar(pjBaseDefensor, numCaras, staminaCost);
                System.out.println(atacante.getNombre() + " utilizó el dado de " + numCaras + " caras.");
                registrarLog(atacante.getNombre() + " utilizó el dado de " + numCaras + " caras.");
            }
        }
    }
}