/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;

public class Jugador {

    static final int factorEspeculador = 1;
    protected Sorpresa cartaLibertad = null;
    protected Casilla casillaActual;
    protected boolean encarcelado = false;
    protected String nombre;
    protected int saldo = 7500;
    ArrayList<TituloPropiedad> propiedades = new ArrayList();

    public int getFactorEspeculador() {
        return factorEspeculador;
    }

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }

    public int getSaldo() {
        return saldo;
    }

    public Casilla getCasillaActual() {
        return casillaActual;
    }

    public boolean getEncarcelado() {
        return encarcelado;
    }

    public boolean tengoPropiedades() {
        return !propiedades.isEmpty();
    }

    boolean actualizarPosicion(Casilla casilla) {
        int costeAlquiler, coste;
        if (casilla.getNumeroCasilla() < casillaActual.getNumeroCasilla()) {
            modificarSaldo(Qytetet.SALDO_SALIDA);
        }
        casillaActual = casilla;
        boolean tienePropietario = false;
        if (casilla.soyEdificable()) {
            tienePropietario = ((Calle) casilla).tengoPropietario();
            if (tienePropietario) {
                boolean enCarcel;
                enCarcel = ((Calle) casilla).getTituloPropiedad().propietarioEncarcelado();
                if (!enCarcel && this != ((Calle) casilla).getTituloPropiedad().getPropietario()) {
                    costeAlquiler = ((Calle) casilla).cobrarAlquiler();
                    modificarSaldo(-costeAlquiler);
                }
            }
        } else if (casilla.getTipoCasilla() == TipoCasilla.IMPUESTO) {
            coste = casilla.getCoste();
            pagarImpuestos(coste);
        }
        return tienePropietario;

    }

    boolean comprarTitulo() {
        boolean puedoComprar = false;
        if (casillaActual.soyEdificable()) {
            boolean tengoPropietario;
            tengoPropietario = ((Calle) casillaActual).tengoPropietario();
            if (!tengoPropietario) {
                int costeCompra;
                costeCompra = casillaActual.getCoste();
                if (costeCompra <= saldo) {
                    TituloPropiedad titulo;
                    titulo = ((Calle) casillaActual).asignarPropietario(this);
                    propiedades.add(titulo);
                    modificarSaldo(-costeCompra);
                    puedoComprar = true;
                }
            }
        }
        return puedoComprar;
    }

    Sorpresa devolverCartaLibertad() {
        Sorpresa aux = cartaLibertad;
        cartaLibertad = null;
        return aux;
    }

    void irACarcel(Casilla casilla) {
        setCasillaActual(casilla);
        setEncarcelado(true);
    }

    void modificarSaldo(int cantidad) {
        saldo += cantidad;
    }

    int obtenerCapital() {
        int capital = saldo;
        int valorPropiedad;
        for (int i = 0; i < propiedades.size(); i++) {
            valorPropiedad = propiedades.get(i).getCasilla().getCoste();
            if (((Calle) propiedades.get(i).getCasilla()).estaHipotecada()) {
                valorPropiedad -= propiedades.get(i).getHipotecaBase();
            } else {
                valorPropiedad += (((Calle) propiedades.get(i).getCasilla()).getNumeroCasas()
                        + ((Calle) propiedades.get(i).getCasilla()).getNumeroHoteles())
                        * propiedades.get(i).getPrecioEdificar();
            }
            capital += valorPropiedad;
        }
        return capital;
    }

    ArrayList<TituloPropiedad> obtenerPropiedadesHipotecadas(boolean hipotecada) {
        ArrayList<TituloPropiedad> aDevolver = new ArrayList();
        for (int i = 0; i < propiedades.size(); i++) {
            if (propiedades.get(i).getHipotecada() == hipotecada) {
                aDevolver.add(propiedades.get(i));
            }
        }
        return aDevolver;
    }

    void pagarCobrarPorCasaYHotel(int cantidad) {
        int numeroTotal;
        numeroTotal = cuantasCasasHotelesTengo();
        modificarSaldo(numeroTotal * cantidad);
    }

    boolean pagarLibertad(int cantidad) {
        boolean tengoSaldo;
        tengoSaldo = tengoSaldo(cantidad);
        if (tengoSaldo) {
            modificarSaldo(-cantidad);
        }
        return tengoSaldo;
    }

    boolean puedoEdificarCasa(Casilla casilla) {
        boolean esMia;
        boolean tengoSaldo = false;
        int costeEdificarCasa;
        esMia = esDeMiPropiedad(casilla);
        if (esMia) {
            costeEdificarCasa = ((Calle) casilla).getTituloPropiedad().getPrecioEdificar();
            tengoSaldo = tengoSaldo(costeEdificarCasa);
        }
        return esMia && tengoSaldo;
    }

    public String getNombre() {
        return nombre;
    }

    boolean puedoEdificarHotel(Casilla casilla) {
        boolean esMia;
        boolean tengoSaldo = false;
        int costeEdificarHotel;
        esMia = esDeMiPropiedad(casilla);
        if (esMia) {
            costeEdificarHotel = ((Calle) casilla).getTituloPropiedad().getPrecioEdificar();
            tengoSaldo = tengoSaldo(costeEdificarHotel);
        }
        return esMia && tengoSaldo;
    }

    boolean puedoHipotecar(Casilla casilla) {
        boolean esMia = esDeMiPropiedad(casilla);
        return esMia;
    }

    boolean puedoPagarHipoteca(Casilla casilla) {
        boolean esMia;
        boolean tengoSaldo;
        esMia = esDeMiPropiedad(casilla);
        tengoSaldo = tengoSaldo((int) (((Calle) casilla).getCosteHipoteca()));
        return esMia && tengoSaldo;
    }

    private int getPosicion(Casilla casilla) {
        int pos = -1;
        for (int i = 0; i < propiedades.size() && pos == -1; i++) {
            if (propiedades.get(i).getCasilla() == casilla) {
                pos = i;
            }
        }
        return pos;
    }

    public boolean puedoVenderPropiedad(Casilla casilla) {
        int pos = getPosicion(casilla);
        return esDeMiPropiedad(casilla) && !propiedades.get(pos).getHipotecada();
    }

    void setCartaLibertad(Sorpresa carta) {
        cartaLibertad = carta;
    }

    void setCasillaActual(Casilla casilla) {
        casillaActual = casilla;
    }

    void setEncarcelado(boolean encarcelado) {
        this.encarcelado = encarcelado;
    }

    public boolean tengoCartaLibertad() {
        return cartaLibertad != null;
    }

    void venderPropiedad(Casilla casilla) {
        int precioVenta;
        precioVenta = ((Calle) casilla).venderTitulo();
        modificarSaldo(precioVenta);
        eliminarDeMisPropiedades(casilla);
    }

    private int cuantasCasasHotelesTengo() {
        int num = 0;
        for (int i = 0; i < propiedades.size(); i++) {
            num += ((Calle) propiedades.get(i).getCasilla()).getNumeroCasas()
                    + ((Calle) propiedades.get(i).getCasilla()).getNumeroHoteles();
        }
        return num;
    }

    private void eliminarDeMisPropiedades(Casilla casilla) {
        propiedades.remove(((Calle) casilla).getTituloPropiedad());

    }

    private boolean esDeMiPropiedad(Casilla casilla) {
        return propiedades.contains(((Calle) casilla).getTituloPropiedad());
    }

    protected boolean tengoSaldo(int cantidad) {
        return saldo >= cantidad;
    }

    protected Especulador convertirme(int fianza) {
        Especulador copia = new Especulador(this, fianza);
        return copia;
    }

    protected void pagarImpuestos(int cantidad) {
        modificarSaldo(-cantidad);
    }

    @Override
    public String toString() {
        String carcel;
        if (encarcelado) {
            carcel = "Sí";
        } else {
            carcel = "No";
        }
        return "\t\tJugador " + nombre + ":\n¿Está encarcelado?: " + carcel + "\nSaldo: "
                + saldo + "€.\nPropiedades: "
                + propiedades.toString() + "\n";
    }

}
