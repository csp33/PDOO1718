/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author csp98
 */
public abstract class Casilla {

    protected int numeroCasilla;
    protected int coste;
    protected TipoCasilla tipo;

    // private int numHoteles = 0;
    // private int numCasas = 0;
    // private TituloPropiedad titulo;
    public Casilla(int num, int c, TipoCasilla type) { //Cárcel, salida, etc. No se compran
        this.numeroCasilla = num;
        this.coste = c;
        this.tipo = type;
        // this.titulo = null;
    }

    public int getNumeroCasilla() {
        return numeroCasilla;
    }

    public int getCoste() {
        return coste;
    }

    public TipoCasilla getTipoCasilla() {
        return tipo;
    }

    abstract boolean soyEdificable();

    @Override
    public abstract String toString();

    /*
    public Casilla(int num, int c) { //Calles (se pueden comprar)
        this.numeroCasilla = num;
        this.coste = c;
        this.tipo = TipoCasilla.CALLE;
        this.titulo = null;
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

    @Override
    public String toString() {
        String pro;
        if (this.tipo == TipoCasilla.CALLE) {
            pro = "\nTítulo de propiedad:\n" + this.titulo.toString();
        } else {
            pro = ".\n";
        }

        return "\nNumero de casilla: " + this.numeroCasilla + ".\nCoste de compra: " + this.coste
                + ".\nNúmero de casas construidas: " + this.numCasas
                + ".\nNúmero de hoteles construidos: " + this.numHoteles
                + ".\nTipo de casilla: " + this.tipo + pro;

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

     */
}
