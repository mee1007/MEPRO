package escoba.modelo;

/**
 * Baza conseguida por un jugador.
 * 
 * Contiene las cartas recogidas en una jugada y si fue escoba o no.
 * 
 * @author Estudiante
 * @version 1.1
 */
public class Baza {
	
	/** Tamaño máximo del array de cartas. */
	private static final int TAMAÑO_MAXIMO = 40;
	
	/** Cartas que componen la baza. */
	private Carta[] cartas;
	
	/** Número de cartas en la baza. */
	private int numeroCartas;
	
	/** Indica si la baza fue una escoba. */
	private boolean esEscoba;
	
	/**
	 * Constructor sin argumentos.
	 * 
	 * Crea una baza vacía.
	 */
	public Baza() {
		cartas = new Carta[TAMAÑO_MAXIMO];
		numeroCartas = 0;
		esEscoba = false;
	}
	
	/**
	 * Agrega una carta a la baza.
	 * 
	 * @param carta carta a agregar
	 */
	public void agregarCarta(Carta carta) {
		if (carta != null && numeroCartas < TAMAÑO_MAXIMO) {
			cartas[numeroCartas] = carta;
			numeroCartas++;
		}
	}
	
	/**
	 * Consulta las cartas de la baza.
	 * 
	 * Devuelve una copia del array de cartas, no el array original.
	 * 
	 * @return array con las cartas de la baza
	 */
	public Carta[] consultarCartas() {
		// Creamos una copia del tamaño exacto
		Carta[] copia = new Carta[numeroCartas];
		for (int i = 0; i < numeroCartas; i++) {
			copia[i] = cartas[i];
		}
		return copia;
	}
	
	/**
	 * Cuenta el número de oros en la baza.
	 * 
	 * @return número de oros
	 */
	public int contarOros() {
		int contador = 0;
		for (int i = 0; i < numeroCartas; i++) {
			if (cartas[i].palo() == Palo.OROS) {
				contador++;
			}
		}
		return contador;
	}
	
	/**
	 * Cuenta el número de sietes en la baza.
	 * 
	 * @return número de sietes
	 */
	public int contarSietes() {
		int contador = 0;
		for (int i = 0; i < numeroCartas; i++) {
			if (cartas[i].puntuacion() == 7) {
				contador++;
			}
		}
		return contador;
	}
	
	/**
	 * Comprueba si la baza tiene el siete de oros.
	 * 
	 * @return true si tiene el siete de oros, false en caso contrario
	 */
	public boolean tieneSieteOros() {
		for (int i = 0; i < numeroCartas; i++) {
			if (cartas[i].palo() == Palo.OROS && cartas[i].puntuacion() == 7) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Marca la baza como escoba.
	 */
	public void marcarEscoba() {
		esEscoba = true;
	}
	
	/**
	 * Comprueba si la baza fue una escoba.
	 * 
	 * @return true si fue escoba, false en caso contrario
	 */
	public boolean fueEscoba() {
		return esEscoba;
	}
	
	/**
	 * Clona la baza actual.
	 * 
	 * Genera un clon profundo de la baza, copiando todas las cartas
	 * y el estado de escoba.
	 * 
	 * @return clon de la baza
	 */
	public Baza clonar() {
		Baza clon = new Baza();
		// Copiamos todas las cartas
		for (int i = 0; i < numeroCartas; i++) {
			clon.agregarCarta(cartas[i]);
		}
		// Copiamos el estado de escoba
		if (this.esEscoba) {
			clon.marcarEscoba();
		}
		return clon;
	}
	
	/**
	 * Genera un código hash para la baza.
	 * 
	 * @return código hash
	 */
	@Override
	public int hashCode() {
		int resultado = 17;
		// Incluimos el número de cartas
		resultado = 31 * resultado + numeroCartas;
		// Incluimos si es escoba
		resultado = 31 * resultado + (esEscoba ? 1 : 0);
		// Incluimos las cartas
		for (int i = 0; i < numeroCartas; i++) {
			resultado = 31 * resultado + (cartas[i] != null ? cartas[i].hashCode() : 0);
		}
		return resultado;
	}
	
	/**
	 * Compara esta baza con otro objeto.
	 * 
	 * Dos bazas son iguales si tienen el mismo número de cartas,
	 * el mismo estado de escoba y las mismas cartas en el mismo orden.
	 * 
	 * @param objeto objeto a comparar
	 * @return true si son iguales, false en caso contrario
	 */
	@Override
	public boolean equals(Object objeto) {
		if (this == objeto) {
			return true;
		}
		if (objeto == null || getClass() != objeto.getClass()) {
			return false;
		}
		Baza otraBaza = (Baza) objeto;
		
		// Comprobamos que tengan el mismo número de cartas
		if (this.numeroCartas != otraBaza.numeroCartas) {
			return false;
		}
		
		// Comprobamos que tengan el mismo estado de escoba
		if (this.esEscoba != otraBaza.esEscoba) {
			return false;
		}
		
		// Comprobamos que tengan las mismas cartas
		for (int i = 0; i < numeroCartas; i++) {
			if (!this.cartas[i].equals(otraBaza.cartas[i])) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Convierte la baza a texto.
	 * 
	 * @return representación en texto de la baza
	 */
	@Override
	public String toString() {
		StringBuilder texto = new StringBuilder();
		texto.append("Baza{");
		texto.append("cartas=").append(numeroCartas);
		texto.append(", escoba=").append(esEscoba);
		texto.append("}");
		return texto.toString();
	}
}
