package escoba.control;

import escoba.modelo.Baza;
import escoba.modelo.Carta;
import escoba.modelo.Jugador;
import escoba.modelo.Mesa;
import escoba.modelo.Partida;
import escoba.vista.VistaConsola;

/**
 * Controlador de la partida de escoba.
 * 
 * Coordina el flujo del juego entre el modelo y la vista.
 * 
 * @author Estudiante
 * @version 1.1
 */
public class Controlador {
	
	/** Número de cartas iniciales en la mesa. */
	private static final int CARTAS_INICIALES_MESA = 4;
	
	/** Número de cartas a repartir por jugador. */
	private static final int CARTAS_POR_JUGADOR = 3;
	
	/** Puntuación del siete. */
	private static final int PUNTUACION_SIETE = 7;
	
	/** Partida actual. */
	private Partida partida;
	
	/** Vista de consola. */
	private VistaConsola vista;
	
	/** Contador de rondas. */
	private int ronda;
	
	/** Último jugador que hizo una baza. */
	private Jugador ultimoQueHizoBaza;
	
	/**
	 * Constructor del controlador.
	 * 
	 * @param partida partida a controlar
	 * @param vista vista de consola
	 */
	public Controlador(Partida partida, VistaConsola vista) {
		this.partida = partida;
		this.vista = vista;
		this.ronda = 0;
		this.ultimoQueHizoBaza = null;
	}
	
	/**
	 * Ejecuta la partida completa.
	 * 
	 * Controla el flujo del juego desde el inicio hasta el final,
	 * gestionando el reparto, las jugadas y el resultado final.
	 */
	public void ejecutarPartida() {
		// Barajamos la baraja
		partida.obtenerBaraja().barajar();
		
		// Repartimos las cartas iniciales
		repartirCartasIniciales();
		
		// Comprobamos si hay escoba en el reparto inicial
		comprobarEscobaEnRepartoInicial();
		
		// Bucle principal del juego
		while (!partida.obtenerBaraja().estaVacia() || 
		       !partida.obtenerJugadores()[0].estaSinCartas() || 
		       !partida.obtenerJugadores()[1].estaSinCartas()) {
			
			// Si ambos jugadores están sin cartas y hay cartas en la baraja, repartimos
			if (partida.obtenerJugadores()[0].estaSinCartas() && 
			    partida.obtenerJugadores()[1].estaSinCartas() &&
			    !partida.obtenerBaraja().estaVacia()) {
				repartirCartas();
			}
			
			// Si el jugador actual tiene cartas, juega
			Jugador jugadorActual = partida.obtenerJugadorActual();
			if (!jugadorActual.estaSinCartas()) {
				// Mostramos el estado actual
				vista.mostrarEstado(partida.obtenerMesa(), jugadorActual, 
				                   partida.obtenerBaraja(), ronda);
				
				// Pedimos la carta a jugar
				Carta cartaJugada = vista.pedirCarta(jugadorActual);
				
				// Pedimos las cartas a retirar de la mesa
				Carta[] cartasRetiradas = vista.pedirCartasMesa(partida.obtenerMesa(), cartaJugada);
				
				// Procesamos la jugada
				procesarJugada(jugadorActual, cartaJugada, cartasRetiradas);
				
				// Cambiamos de turno
				partida.cambiarTurno();
				ronda++;
			} else {
				// Si no tiene cartas, cambiamos de turno
				partida.cambiarTurno();
			}
		}
		
		// Al final de la partida, damos las cartas restantes al último que hizo baza
		darCartasRestantes();
		
		// Mostramos el resultado final
		vista.mostrarResultadoFinal(partida);
	}
	
	/**
	 * Reparte las cartas iniciales: 4 a la mesa y 3 a cada jugador.
	 * 
	 * Este método se ejecuta al inicio de la partida.
	 */
	private void repartirCartasIniciales() {
		Mesa mesa = partida.obtenerMesa();
		
		// Ponemos 4 cartas en la mesa
		for (int i = 0; i < CARTAS_INICIALES_MESA; i++) {
			Carta carta = partida.obtenerBaraja().extraerCarta();
			if (carta != null) {
				mesa.ponerCarta(carta);
			}
		}
		
		// Repartimos 3 cartas a cada jugador
		for (int i = 0; i < CARTAS_POR_JUGADOR; i++) {
			for (Jugador jugador : partida.obtenerJugadores()) {
				Carta carta = partida.obtenerBaraja().extraerCarta();
				if (carta != null) {
					jugador.recibirCarta(carta);
				}
			}
		}
	}
	
	/**
	 * Reparte 3 cartas más a cada jugador.
	 * 
	 * Este método se ejecuta cuando ambos jugadores se quedan sin cartas
	 * y todavía hay cartas en la baraja.
	 */
	private void repartirCartas() {
		for (int i = 0; i < CARTAS_POR_JUGADOR; i++) {
			for (Jugador jugador : partida.obtenerJugadores()) {
				Carta carta = partida.obtenerBaraja().extraerCarta();
				if (carta != null) {
					jugador.recibirCarta(carta);
				}
			}
		}
	}
	
	/**
	 * Procesa la jugada de un jugador.
	 * 
	 * Gestiona la carta jugada y las cartas retiradas de la mesa,
	 * creando una baza si corresponde y verificando si es escoba.
	 * 
	 * @param jugador jugador que realiza la jugada
	 * @param cartaJugada carta jugada por el jugador
	 * @param cartasRetiradas cartas retiradas de la mesa
	 */
	private void procesarJugada(Jugador jugador, Carta cartaJugada, Carta[] cartasRetiradas) {
		// El jugador juega la carta
		jugador.jugarCarta(cartaJugada);
		
		// Creamos una nueva baza
		Baza baza = new Baza();
		
		// Añadimos la carta jugada a la baza
		baza.agregarCarta(cartaJugada);
		
		// Si se retiran cartas de la mesa
		if (cartasRetiradas.length > 0) {
			// Añadimos las cartas retiradas a la baza
			for (int i = 0; i < cartasRetiradas.length; i++) {
				baza.agregarCarta(cartasRetiradas[i]);
				partida.obtenerMesa().quitarCarta(cartasRetiradas[i]);
			}
			
			// Comprobamos si es escoba (la mesa queda vacía)
			if (partida.obtenerMesa().estaVacia()) {
				baza.marcarEscoba();
				vista.anunciarEscoba(jugador.consultarNombre());
			}
			
			// Agregamos la baza al jugador
			jugador.agregarBaza(baza);
			
			// Actualizamos el último que hizo baza
			ultimoQueHizoBaza = jugador;
		} else {
			// Si no se retiran cartas, la carta jugada se queda en la mesa
			partida.obtenerMesa().ponerCarta(cartaJugada);
		}
	}
	
	/**
	 * Da las cartas restantes de la mesa al último jugador que hizo baza.
	 * 
	 * IMPORTANTE: Los sietes NO se incluyen en esta baza final y se descartan.
	 * Si ninguno de los jugadores ganó una baza, se asigna al primer jugador.
	 */
	private void darCartasRestantes() {
		Carta[] cartasRestantes = partida.obtenerMesa().consultarCartasEnMesa();
		
		// Si hay cartas restantes en la mesa
		if (cartasRestantes.length > 0) {
			// Creamos una baza con las cartas restantes (sin sietes)
			Baza bazaFinal = new Baza();
			
			// Agregamos solo las cartas que NO son sietes
			for (int i = 0; i < cartasRestantes.length; i++) {
				if (cartasRestantes[i].puntuacion() != PUNTUACION_SIETE) {
					bazaFinal.agregarCarta(cartasRestantes[i]);
				}
				// Quitamos todas las cartas de la mesa (incluidos los sietes)
				partida.obtenerMesa().quitarCarta(cartasRestantes[i]);
			}
			
			// Determinamos a quién asignar la baza
			Jugador jugadorQueRecibeBaza;
			if (ultimoQueHizoBaza != null) {
				// Si alguien ganó una baza, se la damos
				jugadorQueRecibeBaza = ultimoQueHizoBaza;
			} else {
				// Si nadie ganó baza, se la damos al primer jugador
				jugadorQueRecibeBaza = partida.obtenerJugadores()[0];
			}
			
			// Solo agregamos la baza si tiene cartas (puede estar vacía si solo había sietes)
			if (bazaFinal.consultarCartas().length > 0) {
				jugadorQueRecibeBaza.agregarBaza(bazaFinal);
			}
		}
	}
	
	/**
	 * Comprueba si hay escoba en el reparto inicial.
	 * 
	 * Si las 4 cartas iniciales de la mesa suman 15, el primer jugador
	 * consigue una escoba automáticamente y se retiran todas las cartas.
	 */
	private void comprobarEscobaEnRepartoInicial() {
		Carta[] cartasMesa = partida.obtenerMesa().consultarCartasEnMesa();
		
		// Calculamos la suma de las cartas
		int suma = 0;
		for (int i = 0; i < cartasMesa.length; i++) {
			suma += cartasMesa[i].puntuacion();
		}
		
		// Si suman 15, el primer jugador consigue una escoba
		if (suma == Mesa.SUMA_PUNTOS_A_ALCANZAR) {
			Jugador primerJugador = partida.obtenerJugadores()[0];
			
			// Creamos una baza con todas las cartas de la mesa
			Baza baza = new Baza();
			for (int i = 0; i < cartasMesa.length; i++) {
				baza.agregarCarta(cartasMesa[i]);
				partida.obtenerMesa().quitarCarta(cartasMesa[i]);
			}
			
			// Marcamos como escoba
			baza.marcarEscoba();
			
			// Añadimos la baza al primer jugador
			primerJugador.agregarBaza(baza);
			
			// Actualizamos el último que hizo baza
			ultimoQueHizoBaza = primerJugador;
			
			// Anunciamos la escoba
			vista.anunciarEscoba(primerJugador.consultarNombre());
		}
	}
}
