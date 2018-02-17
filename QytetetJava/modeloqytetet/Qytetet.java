/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import QytetetGUI.Dado;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author csp98
 */
public class Qytetet {

    private Sorpresa cartaActual;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Sorpresa> mazo;
    private Jugador jugadorActual;
    private Tablero tablero;
    private Dado dado;

    public static final int MAX_JUGADORES = 4;
    protected static final int MAX_CARTAS = 10;
    protected static final int MAX_CASILLAS = 20;
    public static final int PRECIO_LIBERTAD = 200;
    public static final int SALDO_SALIDA = 1000;

    private static Qytetet INSTANCE;

    public static Qytetet getInstance(ArrayList<String> jugadores) {
        if (INSTANCE == null) {
            INSTANCE = new Qytetet(jugadores);
        }
        return INSTANCE;
    }

    private Qytetet(ArrayList<String> nombreJugadores) {
        inicializarJuego(nombreJugadores);
    }

    private void inicializarCartasSorpresa() {
        mazo = new ArrayList();
        mazo.add(new Sorpresa("Te conviertes en especulador.\nTu fianza es de 5000 €", 5000, TipoSorpresa.CONVERTIRSE));
        mazo.add(new Sorpresa("Te conviertes en especulador.\nTu fianza es de 3000 €", 3000, TipoSorpresa.CONVERTIRSE));
        mazo.add(new Sorpresa("Se te vienen encima los exámenes finales e intentas salvar\n"
                + "el curso con una academia. Paga 100€ para clases", -100, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Sacas matrícula de honor en una asignatura.\nRecibes 100€", 100, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Suspendes una asignatura y no te guardan las prácticas.\nVuelve al punto de salida", 0, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Pierdes las llaves de casa y pasas una noche en la casilla 8.", 8, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Te pillan copiando una práctica. Vas a la cárcel", 10, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Los inquilinos de tus propiedades pagan su estancia.\nRecibe 100€ por casa y 200 por hotel", +100, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Das una macrofiesta en tus propiedades pero la cosa se desmadra.\n"
                + "Paga 75€ por cada casa que tengas y 150€ por hotel.", -75, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Cada jugador te hace un regalo de 75€ por tu cumpleaños.", +75, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("No te cuentan las prácticas en la convocatoria extraordinaria y tu nota\n"
                + "se queda en 4,89. Lo siento, pero pagas segunda matrícula (160 )", -160, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("Tu mejor amigo paga la fianza.\n"
                + "Eres libre de nuevo.", 0, TipoSorpresa.SALIRCARCEL));
        barajarCartas();
    }

    private void barajarCartas() {
        int veces = (int) (Math.random() * 10 + 1); //Entre 1 y 10
        for (int i = 0; i < veces; i++) {
            int j = (int) (Math.random() * mazo.size() / 2);  //Entre 0 y la mitad
            int k = (int) (Math.random() * mazo.size() + mazo.size() / 2); //Entre la mitad y el final
            Sorpresa temp = new Sorpresa(mazo.get(i));
            mazo.set(i, mazo.get(j));
            mazo.set(j, temp);
        }
    }

    private void inicializarJugadores(ArrayList<String> nombres) {
        jugadores = new ArrayList();
        for (int i = 0; i < nombres.size(); i++) {
            jugadores.add(new Jugador(nombres.get(i)));
            jugadores.get(i).setCasillaActual(tablero.obtenerCasillaNumero(0)); //Establecemos la casilla inicial de salida.
        }
    }

    private void inicializarTablero() {
        tablero = new Tablero();
    }

    public Sorpresa getCartaActual() {
        return cartaActual;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public void siguienteJugador() {
        int posicionActual, posicionNueva;
        posicionActual = jugadores.lastIndexOf(jugadorActual);
        posicionNueva = (posicionActual + 1) % jugadores.size();
        jugadorActual = jugadores.get(posicionNueva);
    }

    public boolean aplicarSorpresa() {
        boolean tienePropietario = false;
        Casilla nuevaCasilla;
        boolean esCarcel;
        if (cartaActual.getTipo() == TipoSorpresa.PAGARCOBRAR) {
            jugadorActual.modificarSaldo(cartaActual.getValor());
        } else if (cartaActual.getTipo() == TipoSorpresa.IRACASILLA) {
            esCarcel = tablero.esCasillaCarcel(cartaActual.getValor());
            if (esCarcel) {
                encarcelarJugador();
            } else {
                nuevaCasilla = tablero.obtenerCasillaNumero(cartaActual.getValor());
                tienePropietario = jugadorActual.actualizarPosicion(nuevaCasilla);
            }
        } else if (cartaActual.getTipo() == TipoSorpresa.PORCASAHOTEL) {
            jugadorActual.pagarCobrarPorCasaYHotel(cartaActual.getValor());
        } else if (cartaActual.getTipo() == TipoSorpresa.PORJUGADOR) {
            for (int i = 0; i < jugadores.size(); i++) {
                if (jugadores.get(i) != jugadorActual) {
                    jugadores.get(i).modificarSaldo(cartaActual.getValor());
                    jugadorActual.modificarSaldo(-cartaActual.getValor());
                }
            }
        } else if (cartaActual.getTipo() == TipoSorpresa.CONVERTIRSE) {
            int fianza = cartaActual.getValor();
            Especulador nuevo = jugadorActual.convertirme(fianza);
            int indice = jugadores.indexOf(jugadorActual);
            jugadores.remove(jugadorActual);
            jugadores.add(indice, nuevo);
            jugadorActual = nuevo;
            for (TituloPropiedad p : jugadorActual.propiedades) {
                p.setPropietario(jugadorActual);
            }
        }
        mazo.remove(cartaActual);
        if (cartaActual.getTipo() == TipoSorpresa.SALIRCARCEL) {
            jugadorActual.setCartaLibertad(cartaActual);
        } else {
            mazo.add(cartaActual);
        }
        return tienePropietario;
    }

    public boolean cancelarHipoteca(Casilla casilla) {
        boolean puedoDeshipotecar = false;
        boolean estaHipotecada;
        int aPagar;
        estaHipotecada = ((Calle) casilla).estaHipotecada();
        if (estaHipotecada) {
            puedoDeshipotecar = jugadorActual.puedoPagarHipoteca(casilla);
            if (puedoDeshipotecar) {
                aPagar = ((Calle) casilla).cancelarHipoteca();
                jugadorActual.modificarSaldo(-aPagar);
            }
        }
        return puedoDeshipotecar;
    }

    public boolean comprarTituloPropiedad() {
        return jugadorActual.comprarTitulo();
    }

    public boolean edificarCasa(Casilla casilla) {
        boolean puedoEdificar = false;
        if (casilla.soyEdificable()) {
            boolean sePuedeEdificar;
            sePuedeEdificar = ((Calle) casilla).sePuedeEdificarCasa(jugadorActual.getFactorEspeculador());
            if (sePuedeEdificar) {
                puedoEdificar = jugadorActual.puedoEdificarCasa(casilla);
                if (puedoEdificar) {
                    int costeEdificarCasa;
                    costeEdificarCasa = ((Calle) casilla).edificarCasa();
                    jugadorActual.modificarSaldo(-costeEdificarCasa);
                }
            }
        }
        return puedoEdificar;
    }

    public boolean edificarHotel(Casilla casilla) {
        boolean puedoEdificar = false;
        if (casilla.soyEdificable()) {
            boolean sePuedeEdificar;
            sePuedeEdificar = ((Calle) casilla).sePuedeEdificarHotel(jugadorActual.getFactorEspeculador());
            if (sePuedeEdificar) {
                puedoEdificar = jugadorActual.puedoEdificarHotel(casilla);
            }
            if (puedoEdificar) {
                int costeEdificarHotel;
                costeEdificarHotel = ((Calle) casilla).edificarHotel();
                jugadorActual.modificarSaldo(-costeEdificarHotel);
            }
        }
        return puedoEdificar;
    }

    public boolean hipotecarPropiedad(Casilla casilla) {
        boolean puedoHipotecarPropiedad = false;
        if (casilla.soyEdificable()) {
            boolean sePuedeHipotecar;
            sePuedeHipotecar = !((Calle) casilla).estaHipotecada();
            if (sePuedeHipotecar) {
                puedoHipotecarPropiedad = jugadorActual.puedoHipotecar(casilla);
                if (puedoHipotecarPropiedad) {
                    int cantidadRecibida;
                    cantidadRecibida = ((Calle) casilla).hipotecar();
                    jugadorActual.modificarSaldo(cantidadRecibida);
                }
            }
        }
        return puedoHipotecarPropiedad;
    }

    public void inicializarJuego(ArrayList<String> jugadores) {
        inicializarTablero();
        inicializarCartasSorpresa();
        inicializarJugadores(jugadores);
        salidaJugadores();
    }

    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo) {
        boolean libre = false;
        boolean tengoSaldo;
        int valorDado;
        dado = Dado.getInstance();
        if (metodo == MetodoSalirCarcel.TIRANDODADO) {
            valorDado = dado.nextNumber();
            libre = valorDado > 5;
        } else if (metodo == MetodoSalirCarcel.PAGANDOLIBERTAD) {
            tengoSaldo = jugadorActual.pagarLibertad(PRECIO_LIBERTAD);
            libre = tengoSaldo;
            if (tengoSaldo) {
                jugadorActual.modificarSaldo(-PRECIO_LIBERTAD);
            }
        }
        if (libre) {
            jugadorActual.setEncarcelado(false);
        }
        return libre;
    }

    public boolean jugar() {
        int valorDado;
        Casilla casillaPosicion;
        Casilla nuevaCasilla;
        boolean tienePropietario;
        dado = Dado.getInstance();
        valorDado = dado.nextNumber();
        casillaPosicion = jugadorActual.getCasillaActual();
        nuevaCasilla = tablero.obtenerNuevaCasilla(casillaPosicion, valorDado);
        tienePropietario = jugadorActual.actualizarPosicion(nuevaCasilla);
        if (!nuevaCasilla.soyEdificable()) {
            if (nuevaCasilla.getTipoCasilla() == TipoCasilla.JUEZ) {
                encarcelarJugador();
            } else if (nuevaCasilla.getTipoCasilla() == TipoCasilla.SORPRESA) {
                cartaActual = mazo.get(0);
            }
        }
        return tienePropietario;
    }

    public Map<String, Integer> obtenerRanking() {
        Map<String, Integer> ranking = new HashMap();
        int capital;
        String nombre;
        for (int i = 0; i < jugadores.size(); i++) {
            capital = jugadores.get(i).obtenerCapital();
            nombre = jugadores.get(i).getNombre();
            ranking.put(nombre, capital);
        }
        return ranking;
    }

    public ArrayList<Casilla> propiedadesHipotecadasJugador(boolean hipotecadas) {
        ArrayList<TituloPropiedad> titulos = jugadorActual.obtenerPropiedadesHipotecadas(hipotecadas);
        ArrayList<Casilla> aDevolver = new ArrayList();
        for (int i = 0; i < titulos.size(); i++) {
            aDevolver.add(titulos.get(i).getCasilla());
        }
        return aDevolver;
    }

    public boolean venderPropiedad(Casilla casilla) {
        boolean puedoVender = false;
        if (casilla.soyEdificable()) {
            puedoVender = jugadorActual.puedoVenderPropiedad(casilla);
            if (puedoVender) {
                jugadorActual.venderPropiedad(casilla);
            }
        }
        return puedoVender;
    }

    private void encarcelarJugador() {
        Casilla casillaCarcel;
        Sorpresa carta;
        if (!jugadorActual.tengoCartaLibertad()) {
            casillaCarcel = tablero.getCarcel();
            jugadorActual.irACarcel(casillaCarcel);
        } else {
            carta = jugadorActual.devolverCartaLibertad();
            mazo.add(carta);
        }
    }

    private void salidaJugadores() {
        for (int i = 0; i < jugadores.size(); i++) {
            jugadores.get(i).setCasillaActual(tablero.obtenerCasillaNumero(0));
        }
        int player = ThreadLocalRandom.current().nextInt(0, jugadores.size() - 1);
        jugadorActual = jugadores.get(player);
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public ArrayList<Sorpresa> getSorpresas() {
        return mazo;
    }
    public int getSaldoSalida(){
        return SALDO_SALIDA;
    }
}
