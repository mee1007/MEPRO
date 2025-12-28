package escoba.control;

import escoba.modelo.Baza;
import escoba.modelo.Carta;
import escoba.modelo.Jugador;
import escoba.modelo.Mesa;
import escoba.modelo.Partida;
import escoba.vista.VistaConsola;

public class Controlador {
    
    private Partida partida;
    private VistaConsola vista;
    
    // TRUCO: Lo iniciamos al primer jugador para no tenerlo nunca a 'null'.
    // Si nadie hace baza en toda la partida, se las lleva el primero por defecto.
    private Jugador ultimoJugadorBaza; 
    
    public Controlador(Partida partida, VistaConsola vista) {
        this.partida = partida;
        this.vista = vista;
        // Inicialización por defecto para evitar nulos
        this.ultimoJugadorBaza = partida.obtenerJugadores()[0];
    }
    
    public void ejecutarPartida() {
        partida.obtenerBaraja().barajar();
        Mesa mesa = partida.obtenerMesa();
        Jugador[] jugadores = partida.obtenerJugadores();
        
        // Repartir 4 cartas a la mesa
        // Como acabamos de empezar, sabemos que hay cartas, no comprobamos null
        for (int i = 0; i < 4; i++) {
            mesa.ponerCarta(partida.obtenerBaraja().robar());
        }
        
        int ronda = 1;
        
        // Controlamos el bucle por el NÚMERO de cartas (el "0" que pedías)
        while (partida.obtenerBaraja().getNumeroCartasDisponibles() > 0) {
            
            // Repartir 3 a cada jugador
            for (int i = 0; i < 3; i++) {
                // Al estar dentro del while, sabemos que robar() devuelve carta válida
                jugadores[0].recibirCarta(partida.obtenerBaraja().robar());
                jugadores[1].recibirCarta(partida.obtenerBaraja().robar());
            }
            
            // Jugar 3 cartas cada uno
            for (int turno = 0; turno < 3; turno++) {
                gestionarTurno(jugadores[0], ronda);
                partida.cambiarTurno();
                
                gestionarTurno(jugadores[1], ronda);
                partida.cambiarTurno();
                
                ronda++;
            }
        }
        
        asignarCartasSobrantes();
        vista.mostrarResultadoFinal(partida);
    }
    
    private void gestionarTurno(Jugador jugador, int ronda) {
        vista.mostrarEstado(partida.obtenerMesa(), jugador, partida.obtenerBaraja(), ronda);
        
        Carta cartaJugada = vista.pedirCarta(jugador);
        
        // Asumimos que la Vista devuelve un array vacío (length 0) si no selecciona nada,
        // en lugar de devolver null.
        Carta[] cartasMesa = vista.pedirCartasMesa(partida.obtenerMesa(), cartaJugada);
        
        jugador.jugarCarta(cartaJugada); // Se quita de la mano
        
        int suma = cartaJugada.puntuacion();
        
        // CAMBIO: Usamos .length en vez de comprobar != null
        // El bucle for ya comprueba implícitamente si length > 0
        for (int i = 0; i < cartasMesa.length; i++) {
            suma += cartasMesa[i].puntuacion();
        }
        
        if (suma == 15) {
            Baza baza = jugador.obtenerBaza();
            baza.agregarCarta(cartaJugada);
            
            for (int i = 0; i < cartasMesa.length; i++) {
                baza.agregarCarta(cartasMesa[i]);
                partida.obtenerMesa().quitarCarta(cartasMesa[i]);
            }
            
            // Usamos el contador "0" para ver si hay escoba
            if (partida.obtenerMesa().getNumeroCartas() == 0) {
                baza.marcarEscoba();
                vista.anunciarEscoba(jugador.consultarNombre());
            }
            
            // Actualizamos quién hizo la última
            ultimoJugadorBaza = jugador;
            
        } else {
            // No suman 15, carta a la mesa
            partida.obtenerMesa().ponerCarta(cartaJugada);
        }
    }
    
    private void asignarCartasSobrantes() {
        Mesa mesa = partida.obtenerMesa();
        
        // Usamos el contador > 0
        if (mesa.getNumeroCartas() > 0) {
            Carta[] sobrantes = mesa.consultarCartasEnMesa();
            
            // Como inicializamos ultimoJugadorBaza en el constructor, 
            // nunca es null, así que lo usamos directamente.
            Jugador ganador = ultimoJugadorBaza;
            
            for (int i = 0; i < sobrantes.length; i++) {
                ganador.obtenerBaza().agregarCarta(sobrantes[i]);
                mesa.quitarCarta(sobrantes[i]);
            }
        }
    }
}
