package escoba.modelo;

/**
 * Jugador de la escoba.
 * 
 * Representa un jugador con su nombre, mano de cartas y bazas conseguidas.
 * 
 * @author Estudiante
 * @version 1.1
 */
public class Jugador {
	
	/** Tamaño máximo de la mano. */
	private static final int TAMAÑO_MANO = 3;
	
	/** Tamaño máximo de bazas que puede conseguir. */
	private static final int TAMAÑO_MAXIMO_BAZAS = 40;
	
	/** Nombre del jugador. */
	private String nombre;
	
	/** Cartas en la mano del jugador. */
	private Carta[] mano;
	
	/** Número de cartas en la mano. */
	private int numeroCartasEnMano;
	
	/** Bazas conseguidas por el jugador. */
	private Baza[] bazas;
	
	/** Número de bazas conseguidas. */
	private int numeroBazas;
	
	/** Número de escobas conseguidas. */
	private int numeroEscobas;
	
	/**
	 * Constructor con el nombre del jugador.
	 * 
	 * Inicializa el jugador con su nombre, una mano vacía
	 * y sin bazas ni escobas.
	 * 
	 * @param nombre nombre del jugador
	 */
	public Jugador(String nombre) {
		this.nombre = nombre;
		this.mano = new Carta[TAMAÑO_MANO];
		this.numeroCartasEnMano = 0;
		this.bazas = new Baza[TAMAÑO_MAXIMO_BAZAS];
		this.numeroBazas = 0;
		this.numeroEscobas = 0;
	}
	
	/**
	 * Consulta el nombre del jugador.
	 * 
	 * @return nombre del jugador
	 */
	public String consultarNombre() {
		return nombre;
	}
	
	/**
	 * Consulta la mano del jugador.
	 * 
	 * Devuelve una copia del array de cartas en la mano,
	 * no el array original.
	 * 
	 * @return array con las cartas de la mano
	 */
	public Carta[] consultarMano() {
		// Creamos una copia del tamaño exacto
		Carta[] copia = new Carta[numeroCartasEnMano];
		for (int i = 0; i < numeroCartasEnMano; i++) {
			copia[i] = mano[i];
		}
		return copia;
	}
	
	/**
	 * Consulta las bazas del jugador.
	 * 
	 * Devuelve una copia del array de bazas, no el array original.
	 * 
	 * @return array con las bazas del jugador
	 */
	public Baza[] consultarBazas() {
		// Creamos una copia del tamaño exacto
		Baza[] copia = new Baza[numeroBazas];
		for (int i = 0; i < numeroBazas; i++) {
			copia[i] = bazas[i];
		}
		return copia;
	}
	
	/**
	 * Consulta el número de escobas conseguidas.
	 * 
	 * @return número de escobas
	 */
	public int consultarEscobas() {
		return numeroEscobas;
	}
	
	/**
	 * Recibe una carta y la añade a la mano.
	 * 
	 * @param carta carta a recibir
	 */
	public void recibirCarta(Carta carta) {
		if (carta != null && numeroCartasEnMano < TAMAÑO_MANO) {
			mano[numeroCartasEnMano] = carta;
			numeroCartasEnMano++;
		}
	}
	
	/**
	 * Juega una carta de la mano.
	 * 
	 * Retira la carta indicada de la mano del jugador.
	 * 
	 * @param carta carta a jugar
	 */
	public void jugarCarta(Carta carta) {
		if (carta != null) {
			// Buscamos la carta en la mano
			boolean encontrada = false;
			for (int i = 0; i < numeroCartasEnMano && !encontrada; i++) {
				if (mano[i].equals(carta)) {
					// Desplazamos las cartas posteriores
					for (int j = i; j < numeroCartasEnMano - 1; j++) {
						mano[j] = mano[j + 1];
					}
					// Ponemos null en la última posición
					mano[numeroCartasEnMano - 1] = null;
					numeroCartasEnMano--;
					encontrada = true;
				}
			}
		}
	}
	
	/**
	 * Agrega una baza al jugador.
	 * 
	 * Si la baza es una escoba, incrementa el contador de escobas.
	 * 
	 * @param baza baza a agregar
	 */
	public void agregarBaza(Baza baza) {
		if (baza != null && numeroBazas < TAMAÑO_MAXIMO_BAZAS) {
			bazas[numeroBazas] = baza;
			numeroBazas++;
			// Si la baza fue escoba, incrementamos el contador
			if (baza.fueEscoba()) {
				numeroEscobas++;
			}
		}
	}
	
	/**
	 * Comprueba si el jugador está sin cartas en la mano.
	 * 
	 * @return true si no tiene cartas, false en caso contrario
	 */
	public boolean estaSinCartas() {
		return numeroCartasEnMano == 0;
	}
	
	/**
	 * Cuenta el total de cartas en todas las bazas.
	 * 
	 * @return número total de cartas
	 */
	public int contarCartas() {
		int total = 0;
		for (int i = 0; i < numeroBazas; i++) {
			total += bazas[i].consultarCartas().length;
		}
		return total;
	}
	
	/**
	 * Cuenta el total de oros en todas las bazas.
	 * 
	 * @return número de oros
	 */
	public int contarOros() {
		int total = 0;
		for (int i = 0; i < numeroBazas; i++) {
			total += bazas[i].contarOros();
		}
		return total;
	}
	
	/**
	 * Cuenta el total de sietes en todas las bazas.
	 * 
	 * @return número de sietes
	 */
	public int contarSietes() {
		int total = 0;
		for (int i = 0; i < numeroBazas; i++) {
			total += bazas[i].contarSietes();
		}
		return total;
	}
	
	/**
	 * Comprueba si el jugador tiene el siete de oros.
	 * 
	 * @return true si tiene el siete de oros, false en caso contrario
	 */
	public boolean tieneSieteOros() {
		for (int i = 0; i < numeroBazas; i++) {
			if (bazas[i].tieneSieteOros()) {
				return true;
			}
		}
		return false;
	}
}
