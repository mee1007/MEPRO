package escoba.modelo;

/**
 * Mesa de juego.
 * Implementación basada en contadores (sin nulls, sin breaks).
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
        if (numCartas < CAPACIDAD) {
            cartasEnMesa[numCartas] = carta;
            numCartas++;
        }
    }

    public void quitarCarta(Carta carta) {
        int posicion = -1;
        int i = 0;
        boolean encontrado = false;
        
        // 1. Buscamos la carta SIN usar break
        while (i < numCartas && !encontrado) {
            if (cartasEnMesa[i].equals(carta)) {
                posicion = i;
                encontrado = true;
            } else {
                i++;
            }
        }

        // 2. Si existe, desplazamos
        if (posicion != -1) {
            for (int j = posicion; j < numCartas - 1; j++) {
                cartasEnMesa[j] = cartasEnMesa[j + 1];
            }
            numCartas--;
        }
    }

    public Carta[] consultarCartasEnMesa() {
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
