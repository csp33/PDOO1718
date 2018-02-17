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
public class Calle extends Casilla {

    protected int numCasas = 0;
    protected int numHoteles = 0;
    protected TituloPropiedad titulo = null;

    public Calle(int num, int c) {
        super(num, c, TipoCasilla.CALLE);
    }

    @Override
    boolean soyEdificable() {
        return true;
    }

    @Override
    public String toString() {
        String casas, hoteles;
        if (numCasas > 0) {
            casas = "\nNúmero de casas construidas: " + numCasas;
        } else {
            casas = "";
        }
        if (numHoteles > 0) {
            hoteles = "\nNúmero de hoteles construidos: " + numHoteles;
        } else {
            hoteles = "";
        }
        return "Numero de casilla: " + this.numeroCasilla + ".\nCoste de compra: " + this.coste
                + casas + hoteles + "\nTítulo de propiedad:" + this.titulo.toString();
    }

    int getNumeroHoteles() {
        return numHoteles;
    }

    int getNumeroCasas() {
        return numCasas;
    }

    public TituloPropiedad getTituloPropiedad() {
        return titulo;
    }

    void setNumHoteles(int n) {
        numHoteles = n;
    }

    void setNumCasas(int n) {
        numCasas = n;
    }

    void setTitulo(TituloPropiedad titulo) {
        this.titulo = titulo;
    }

    TituloPropiedad asignarPropietario(Jugador jugador) {
        titulo.setPropietario(jugador);
        return titulo;
    }

    int calcularValorHipoteca() {
        int hipotecaBase;
        hipotecaBase = titulo.getHipotecaBase();
        int cantidadRecibida = hipotecaBase + (int) (numCasas * 0.55 * hipotecaBase + numHoteles * hipotecaBase);
        return cantidadRecibida;
    }

    int cancelarHipoteca() {
        int aPagar;
        titulo.setHipotecada(false);
        aPagar = getCosteHipoteca();
        return aPagar;
    }

    int edificarCasa() {
        int costeEdificarCasa;
        setNumCasas(numCasas + 1);
        costeEdificarCasa = titulo.getPrecioEdificar();
        return costeEdificarCasa;

    }

    int edificarHotel() {
        int costeEdificarHotel;
        setNumHoteles(numHoteles + 1);
        costeEdificarHotel = titulo.getPrecioEdificar();
        return costeEdificarHotel;
    }

    public int cobrarAlquiler() {
        int costeAlquiler;
        int costeAlquilerBase = titulo.getAlquilerBase();
        costeAlquiler = costeAlquilerBase + (int) (numCasas * 0.5 + numHoteles * 2);
        titulo.cobrarAlquiler(costeAlquiler);
        return costeAlquiler;
    }

    boolean estaHipotecada() {
        return titulo.getHipotecada();
    }

    int getCosteHipoteca() {
        int coste;
        coste = (int) (calcularValorHipoteca() * 1.10);  //Es un 10% más
        return coste;
    }

    int hipotecar() {
        int cantidadRecibida;
        titulo.setHipotecada(true);
        cantidadRecibida = calcularValorHipoteca();
        return cantidadRecibida;
    }

    boolean propietarioEncarcelado() {
        return titulo.propietarioEncarcelado();
    }

    public boolean sePuedeEdificarCasa(int factorEspeculador) {
        return numCasas < 4 * factorEspeculador;
    }

    public boolean sePuedeEdificarHotel(int factorEspeculador) {
        return numHoteles < 4 * factorEspeculador && numCasas == 4 * factorEspeculador;
    }

    boolean tengoPropietario() {
        return titulo.tengoPropietario();
    }

    int venderTitulo() {
        int precioVenta;
        int precioCompra;
        precioCompra = coste + (numCasas + numHoteles) * titulo.getPrecioEdificar();
        precioVenta = (int) (precioCompra + titulo.getFactorRevalorizacion() * precioCompra);
        titulo.setPropietario(null);
        setNumHoteles(0);
        setNumCasas(0);
        return precioVenta;
    }

}
