package escoba.modelo;

/**
 * Mesa de juego.
 * Implementación basada en contadores (sin uso de nulls).
 */
public class Mesa {

    private Carta[] cartasEnMesa;
    private int numCartas;
    private final int CAPACIDAD = 40;

    public Mesa() {
        this.cartasEnMesa = new Carta[CAPACIDAD];
        this.numCartas = 0;
    }

    public void ponerCarta(Carta carta) {
        // Usamos el contador para saber dónde escribir
        // Evitamos escribir si estamos llenos (seguridad básica)
        if (numCartas < CAPACIDAD) {
            cartasEnMesa[numCartas] = carta;
            numCartas++;
        }
    }

    public void quitarCarta(Carta carta) {
        int posicion = -1;
        
        // 1. Buscamos la carta usando el contador como límite
        for (int i = 0; i < numCartas; i++) {
            if (cartasEnMesa[i].equals(carta)) {
                posicion = i;
                break;
            }
        }

        // 2. Si existe, desplazamos los elementos para tapar el hueco
        if (posicion != -1) {
            for (int i = posicion; i < numCartas - 1; i++) {
                cartasEnMesa[i] = cartasEnMesa[i + 1];
            }
            // Solo bajamos el contador.
            // NO hacemos cartasEnMesa[numCartas-1] = null; <- PROHIBIDO
            // La referencia vieja se queda ahí pero el contador la hace inaccesible.
            numCartas--;
        }
    }

    public Carta[] consultarCartasEnMesa() {
        // Creamos un array nuevo del tamaño exacto del contador
        Carta[] resultado = new Carta[numCartas];
        for (int i = 0; i < numCartas; i++) {
            resultado[i] = cartasEnMesa[i];
        }
        return resultado;
    }

    public int getNumeroCartas() {
        return numCartas;
    }
}
