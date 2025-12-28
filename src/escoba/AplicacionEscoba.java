package escoba;

import escoba.control.Controlador;
import escoba.modelo.Baraja;
import escoba.modelo.Jugador;
import escoba.modelo.Mesa;
import escoba.modelo.Partida;
import escoba.vista.VistaConsola;

/**
 * Clase raíz de la aplicación del juego de la escoba.
 * 
 * Inicializa los componentes necesarios (jugadores, mesa, baraja, partida)
 * y lanza la ejecución del controlador para gestionar el juego.
 * 
 * @author Estudiante
 * @version 1.0
 * @since JDK 24.0.2
 */
public class AplicacionEscoba {
	
	/**
	 * Constructor privado para impedir instanciaciones.
	 */
	private AplicacionEscoba() {		
	}
	
	/**
	 * Método principal.
	 * 
	 * Punto de entrada de la aplicación. Crea la partida,
	 * inicializa la vista y el controlador, y ejecuta la partida.
	 * 
	 * @param args argumentos en línea de comandos (sin uso)
	 */
	public static void main(String[] args) {
		// Creamos los jugadores
		Jugador jugador1 = new Jugador("Juan");
		Jugador jugador2 = new Jugador("María");
		
		// Creamos la mesa y la baraja
		Mesa mesa = new Mesa();
		Baraja baraja = new Baraja();
		
		// Creamos la partida
		Partida partida = new Partida(jugador1, jugador2, mesa, baraja);
		
		// Creamos la vista y el controlador
		VistaConsola vista = new VistaConsola();
		Controlador controlador = new Controlador(partida, vista);
		
		// Ejecutamos la partida
		controlador.ejecutarPartida();
	}
}
