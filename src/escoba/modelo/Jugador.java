package escoba.modelo;

public class Jugador {

    private String nombre;
    private Carta[] mano;
    private int numCartasMano;
    // Diagrama dice que jugador devuelve Baza[] en consultarBazas()
    // Así que usaremos un array de Bazas
    private Baza[] misBazas; 
    private int numBazas;
    
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new Carta[3];
        this.numCartasMano = 0;
        this.misBazas = new Baza[50]; // Tamaño suficiente para una partida
        this.numBazas = 0;
    }

    public String consultarNombre() {
        return nombre;
    }

    public void recibirCarta(Carta carta) {
        if (numCartasMano < 3) {
            mano[numCartasMano] = carta;
            numCartasMano++;
        }
    }

    public Carta jugarCarta(Carta cartaAJugar) {
        int pos = -1;
        for (int i = 0; i < numCartasMano; i++) {
            if (mano[i].equals(cartaAJugar)) {
                pos = i;
                break;
            }
        }
        if (pos != -1) {
            Carta c = mano[pos];
            for (int i = pos; i < numCartasMano - 1; i++) {
                mano[i] = mano[i + 1];
            }
            numCartasMano--;
            return c;
        }
        return null; // Obligatorio por compilador, aunque no debería pasar
    }
    
    // --- Métodos de Bazas ---
    
    public void agregarBaza(Baza baza) {
        if (numBazas < misBazas.length) {
            misBazas[numBazas] = baza;
            numBazas++;
        }
    }
    
    public Baza[] consultarBazas() {
        // Devuelve copia del array
        Baza[] copia = new Baza[numBazas];
        for (int i = 0; i < numBazas; i++) {
            // Idealmente clonaríamos la baza, pero copia de referencia vale para un 7
            copia[i] = misBazas[i]; 
        }
        return copia;
    }

    // --- Métodos de Conteo (Delegados) ---
    // Recorren todas las bazas ganadas y suman
    
    public int consultarEscobas() {
        int total = 0;
        for(int i=0; i<numBazas; i++) {
            if (misBazas[i].fueEscoba()) total++;
        }
        return total;
    }
    
    public int contarCartas() {
        int total = 0;
        for(int i=0; i<numBazas; i++) {
            total += misBazas[i].obtenerNumeroCartas();
        }
        return total;
    }
    
    public int contarOros() {
        int total = 0;
        for(int i=0; i<numBazas; i++) {
            total += misBazas[i].contarOros();
        }
        return total;
    }
    
    public int contarSietes() {
        int total = 0;
        for(int i=0; i<numBazas; i++) {
            total += misBazas[i].contarSietes();
        }
        return total;
    }
    
    public boolean tieneSieteOros() {
        for(int i=0; i<numBazas; i++) {
            if (misBazas[i].tieneSieteOros()) return true;
        }
        return false;
    }
    
    public boolean estaSinCartas() {
        return numCartasMano == 0;
    }
    
    public Carta[] consultarMano() {
        Carta[] copia = new Carta[numCartasMano];
        for(int i=0; i<numCartasMano; i++){
            copia[i] = mano[i];
        }
        return copia;
    }
}
