package escoba.modelo;

import java.util.Random;

public class Baraja {

    private Carta[] cartas;
    private int siguienteCarta; // Índice que actúa como puntero
    private final int NUM_CARTAS = 40;

    public Baraja() {
        cartas = new Carta[NUM_CARTAS];
        siguienteCarta = 0;
        inicializarBaraja();
        barajar();
    }

    private void inicializarBaraja() {
        int pos = 0;
        Palo[] palos = Palo.values();
        for (int i = 0; i < palos.length; i++) {
            for (int j = 1; j <= 10; j++) {
                cartas[pos] = new Carta(j, palos[i]);
                pos++;
            }
        }
    }

    public void barajar() {
        Random rnd = new Random();
        for (int i = 0; i < 200; i++) {
            int pos1 = rnd.nextInt(NUM_CARTAS);
            int pos2 = rnd.nextInt(NUM_CARTAS);
            Carta aux = cartas[pos1];
            cartas[pos1] = cartas[pos2];
            cartas[pos2] = aux;
        }
        siguienteCarta = 0; // Reset del índice
    }

    public Carta robar() {
        // Devolvemos la carta y avanzamos el contador
        // No necesitamos comprobar nulls, confiamos en el índice
        if (siguienteCarta < NUM_CARTAS) {
            Carta c = cartas[siguienteCarta];
            siguienteCarta++;
            return c;
        }
        return null; // Retorno de seguridad, pero el controlador verifica antes
    }
    
    // Alias para tests
    public Carta extraerCarta() {
        return robar();
    }
    
    public int getNumeroCartasDisponibles() {
        return NUM_CARTAS - siguienteCarta;
    }
}
