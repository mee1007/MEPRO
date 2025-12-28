package escoba.modelo;

import java.util.Objects;

public class Baza {

    private Carta[] cartas;
    private int numCartas;
    private boolean esEscoba; 

    public Baza() {
        this.cartas = new Carta[40];
        this.numCartas = 0;
        this.esEscoba = false;
    }

    public void agregarCarta(Carta carta) {
        if (numCartas < 40) {
            cartas[numCartas] = carta;
            numCartas++;
        }
    }
    
    public void marcarEscoba() {
        this.esEscoba = true;
    }
    
    public boolean fueEscoba() {
        return esEscoba;
    }

    public int obtenerNumeroCartas() {
        return numCartas;
    }
    
    public Carta[] consultarCartas() {
        Carta[] copia = new Carta[numCartas];
        for(int i=0; i<numCartas; i++){
            copia[i] = cartas[i];
        }
        return copia;
    }

    public int contarOros() {
        int c = 0;
        for (int i = 0; i < numCartas; i++) {
            if (cartas[i].palo() == Palo.OROS) {
                c++;
            }
        }
        return c;
    }

    public int contarSietes() {
        int c = 0;
        for (int i = 0; i < numCartas; i++) {
            if (cartas[i].puntuacion() == 7) {
                c++;
            }
        }
        return c;
    }

    public boolean tieneSieteOros() {
        boolean encontrado = false;
        int i = 0;
        // Búsqueda sin break
        while (i < numCartas && !encontrado) {
            if (cartas[i].puntuacion() == 7 && cartas[i].palo() == Palo.OROS) {
                encontrado = true;
            } else {
                i++;
            }
        }
        return encontrado;
    }
    
    public Baza clonar() {
        Baza nueva = new Baza();
        nueva.esEscoba = this.esEscoba;
        for(int i=0; i<this.numCartas; i++) {
            nueva.agregarCarta(this.cartas[i]);
        }
        return nueva;
    }

    @Override
    public String toString() {
        return "Baza [numCartas=" + numCartas + ", esEscoba=" + esEscoba + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(numCartas, esEscoba);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        Baza other = (Baza) obj;
        return esEscoba == other.esEscoba && numCartas == other.numCartas;
    }
}
