package escoba.modelo;

public class Jugador {

    private String nombre;
    private Carta[] mano;
    private int numCartasMano;
    private Baza[] misBazas; 
    private int numBazas;
    
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new Carta[3];
        this.numCartasMano = 0;
        this.misBazas = new Baza[50]; 
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
        int i = 0;
        boolean encontrado = false;

        // Búsqueda SIN break
        while (i < numCartasMano && !encontrado) {
            if (mano[i].equals(cartaAJugar)) {
                pos = i;
                encontrado = true;
            } else {
                i++;
            }
        }

        if (pos != -1) {
            Carta c = mano[pos];
            // Desplazamiento
            for (int j = pos; j < numCartasMano - 1; j++) {
                mano[j] = mano[j + 1];
            }
            numCartasMano--;
            return c;
        }
        return null; 
    }
    
    public void agregarBaza(Baza baza) {
        if (numBazas < misBazas.length) {
            misBazas[numBazas] = baza;
            numBazas++;
        }
    }
    
    public Baza[] consultarBazas() {
        Baza[] copia = new Baza[numBazas];
        for (int i = 0; i < numBazas; i++) {
            copia[i] = misBazas[i]; 
        }
        return copia;
    }

    public int consultarEscobas() {
        int total = 0;
        for(int i=0; i<numBazas; i++) {
            if (misBazas[i].fueEscoba()) {
                total++;
            }
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
        boolean tiene = false;
        int i = 0;
        // Búsqueda sin break
        while(i < numBazas && !tiene) {
            if (misBazas[i].tieneSieteOros()) {
                tiene = true;
            } else {
                i++;
            }
        }
        return tiene;
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
