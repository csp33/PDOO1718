//     ___            _            _            _   
//    / _ \   _   _  | |_    ___  | |_    ___  | |_ 
//   | | | | | | | | | __|  / _ \ | __|  / _ \ | __|
//   | |_| | | |_| | | |_  |  __/ | |_  |  __/ | |_ 
//    \__\_\  \__, |  \__|  \___|  \__|  \___|  \__|
//            |___/                                 
package modeloqytetet;

/**
 *
 * @author csp98
 */
public class Especulador extends Jugador {

    public Especulador(String nombre) {
        super(nombre);
    }
    static final int factorEspeculador = 2;
    private int fianza;

    public Especulador(Jugador jugador, int fianza) {
        super(jugador.getNombre());
        this.propiedades = jugador.propiedades;
        this.cartaLibertad = jugador.cartaLibertad;
        this.casillaActual = jugador.casillaActual;
        this.encarcelado = jugador.encarcelado;
        this.saldo = jugador.saldo;
        this.fianza = fianza;

    }

    @Override
    public int getFactorEspeculador() {
        return 2;
    }

    private boolean pagarFianza(int cantidad) {  //Devuelve true si se libra de la cárcel
        boolean tengoSaldo = super.tengoSaldo(cantidad);
        if (tengoSaldo) {
            modificarSaldo(-cantidad);
        }
        return tengoSaldo;
    }

    public int getFianza() {
        return fianza;
    }

    @Override
    protected Especulador convertirme(int fianza) {
        return this;
    }

    @Override
    protected void pagarImpuestos(int cantidad) {
        int mitad = cantidad / 2;
        modificarSaldo(-mitad);
    }

    @Override
    protected void irACarcel(Casilla casilla) {
        boolean puedoPagar = pagarFianza(fianza);
        if (!puedoPagar) {
            super.irACarcel(casilla);
        }
    }

    @Override
    public String toString() {
        String carcel;
        if (encarcelado) {
            carcel = "Sí";
        } else {
            carcel = "No";
        }
        return "\t\tEspeculador " + nombre + ":\n¿Está encarcelado?: " + carcel + "\nSaldo: "
                + saldo + "€.\nPropiedades: "
                + propiedades.toString() + "\n";
    }

}
