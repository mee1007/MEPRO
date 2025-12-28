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
    private Jugador ultimoJugadorBaza; 
    
    public Controlador(Partida partida, VistaConsola vista) {
        this.partida = partida;
        this.vista = vista;
        this.ultimoJugadorBaza = partida.obtenerJugadores()[0];
    }
    
    public void ejecutarPartida() {
        partida.obtenerBaraja().barajar();
        repartirCartasIniciales();
        comprobarEscobaEnRepartoInicial();
        
        int ronda = 1;
        while (partida.obtenerBaraja().getNumeroCartasDisponibles() > 0) {
            repartirCartas();
            Jugador[] jugadores = partida.obtenerJugadores();
            
            for (int turno = 0; turno < 3; turno++) {
                procesarTurno(jugadores[0], ronda);
                partida.cambiarTurno();
                
                procesarTurno(jugadores[1], ronda);
                partida.cambiarTurno();
                
                ronda++;
            }
        }
        
        darCartasRestantes();
        vista.mostrarResultadoFinal(partida);
    }
    
    private void procesarTurno(Jugador jugador, int ronda) {
        vista.mostrarEstado(partida.obtenerMesa(), jugador, partida.obtenerBaraja(), ronda);
        Carta cartaJugada = vista.pedirCarta(jugador);
        Carta[] cartasMesa = vista.pedirCartasMesa(partida.obtenerMesa(), cartaJugada);
        procesarJugada(jugador, cartaJugada, cartasMesa);
    }

    private void repartirCartasIniciales() {
        Mesa mesa = partida.obtenerMesa();
        for (int i = 0; i < 4; i++) {
            mesa.ponerCarta(partida.obtenerBaraja().robar());
        }
        repartirCartas();
    }

    private void repartirCartas() {
        Jugador[] jugadores = partida.obtenerJugadores();
        for (int i = 0; i < 3; i++) {
            jugadores[0].recibirCarta(partida.obtenerBaraja().robar());
            jugadores[1].recibirCarta(partida.obtenerBaraja().robar());
        }
    }

    private void comprobarEscobaEnRepartoInicial() {
        Mesa mesa = partida.obtenerMesa();
        Carta[] cartasMesa = mesa.consultarCartasEnMesa();
        int suma = 0;
        
        for (int i = 0; i < cartasMesa.length; i++) {
            suma += cartasMesa[i].puntuacion();
        }
        
        if (suma == 15) {
            Jugador j1 = partida.obtenerJugadores()[0];
            Baza baza = new Baza(); 
            for (int i = 0; i < cartasMesa.length; i++) {
                baza.agregarCarta(cartasMesa[i]);
                mesa.quitarCarta(cartasMesa[i]);
            }
            baza.marcarEscoba();
            j1.agregarBaza(baza);
            ultimoJugadorBaza = j1;
            vista.anunciarEscoba(j1.consultarNombre());
        }
    }

    private void procesarJugada(Jugador jugador, Carta carta, Carta[] cartasRetiradas) {
        jugador.jugarCarta(carta); 
        int suma = carta.puntuacion();
        
        if (cartasRetiradas != null) {
            for (int i = 0; i < cartasRetiradas.length; i++) {
                suma += cartasRetiradas[i].puntuacion();
            }
        }
        
        if (suma == 15) {
            Baza baza = new Baza(); 
            baza.agregarCarta(carta);
            
            if (cartasRetiradas != null) {
                for (int i = 0; i < cartasRetiradas.length; i++) {
                    baza.agregarCarta(cartasRetiradas[i]);
                    partida.obtenerMesa().quitarCarta(cartasRetiradas[i]);
                }
            }
            
            if (partida.obtenerMesa().getNumeroCartas() == 0) {
                baza.marcarEscoba();
                vista.anunciarEscoba(jugador.consultarNombre());
            }
            
            jugador.agregarBaza(baza);
            ultimoJugadorBaza = jugador;
        } else {
            partida.obtenerMesa().ponerCarta(carta);
        }
    }

    private void darCartasRestantes() {
        Mesa mesa = partida.obtenerMesa();
        
        if (mesa.getNumeroCartas() > 0) {
            Carta[] sobrantes = mesa.consultarCartasEnMesa();
            Jugador ganador = ultimoJugadorBaza; 
            Baza bazaFinal = new Baza();
            
            for (int i = 0; i < sobrantes.length; i++) {
                if (sobrantes[i].puntuacion() != 7) {
                    bazaFinal.agregarCarta(sobrantes[i]);
                }
                mesa.quitarCarta(sobrantes[i]);
            }
            
            if (bazaFinal.obtenerNumeroCartas() > 0) {
                ganador.agregarBaza(bazaFinal);
            }
        }
    }
}
