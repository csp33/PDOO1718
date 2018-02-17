//     ___            _            _            _   
//    / _ \   _   _  | |_    ___  | |_    ___  | |_ 
//   | | | | | | | | | __|  / _ \ | __|  / _ \ | __|
//   | |_| | | |_| | | |_  |  __/ | |_  |  __/ | |_ 
//    \__\_\  \__, |  \__|  \___|  \__|  \___|  \__|
//            |___/                                 
package InterfazTextualQytetet;

import java.util.ArrayList;
import java.util.Map;
import modeloqytetet.Calle;
import modeloqytetet.Qytetet;
import modeloqytetet.Jugador;
import modeloqytetet.Casilla;
import modeloqytetet.Especulador;
import modeloqytetet.MetodoSalirCarcel;
import modeloqytetet.Sorpresa;
import modeloqytetet.TipoCasilla;
import modeloqytetet.TipoSorpresa;
import modeloqytetet.TituloPropiedad;

/**
 *
 * @author csp98
 */
public class ControladorQytetet {

    private static void esperar(double segundos) {
        try {
            Thread.sleep((int) (segundos * 1000));
        } catch (InterruptedException e) {
            System.out.println("Error");
        }
    }

    private Qytetet juego;
    private Jugador jugador;
    private Casilla casilla;
    private VistaTextualQytetet vista;

    public ControladorQytetet() {

    }

    public void inicializacionJuego() {
        vista = new VistaTextualQytetet();
        ArrayList<String> jugadores = vista.obtenerNombreJugadores();
        juego = Qytetet.getInstance(jugadores);
        jugador = juego.getJugadorActual();
        casilla = juego.getJugadorActual().getCasillaActual();
        vista.mostrar("Mostrando tablero de juego:\n");
        for (int i = 0; i < juego.getTablero().getTamanio(); i++) {
            vista.mostrar(juego.getTablero().obtenerCasillaNumero(i).toString());
            //   esperar(1);
        }
        esperar(1);
        vista.mostrar("Mostrando cartas sorpresa:\n");
        esperar(0.5);
        for (Sorpresa s : juego.getSorpresas()) {
            vista.mostrar(s.toString());
            esperar(0.5);
        }
        esperar(1);
        vista.mostrar("El jugador que comienza es: " + jugador.getNombre() + "\n");
        esperar(1);
    }

    public void desarrolloJuego() {
        boolean bancarrota;
        do {
            //Parte 1: Movimiento
            vista.mostrar("Comienza el turno del jugador " + jugador.getNombre() + "\n");
            vista.mostrarSaldo(jugador);
            esperar(1.5);
            if (jugador.getEncarcelado()) { //Intenta salir de la cárcel.
                vista.mostrar("El jugador se encuentra en la cárcel\n");
                esperar(0.5);
                boolean libre;
                MetodoSalirCarcel metodo;
                metodo = vista.menuSalirCarcel();
                libre = juego.intentarSalirCarcel(metodo);
                if (libre) {
                    if (metodo == MetodoSalirCarcel.TIRANDODADO) {
                        vista.mostrar("El jugador tira el dado y sale un 6");
                    } else {
                        vista.mostrar("El jugador paga su libertad (" + Qytetet.PRECIO_LIBERTAD + " €).");
                    }
                    esperar(1);
                    vista.mostrar("El jugador consiguió salir de la cárcel.\n");
                } //Si no lo consigue,pasamos turno.
                else if (metodo == MetodoSalirCarcel.TIRANDODADO) {
                    vista.mostrar("Mala suerte! No salió un 6");
                } else {
                    vista.mostrar("No tienes saldo para pagar tu libertad.");
                }
                esperar(1.5);
            }
            if (!jugador.getEncarcelado()) {//No estaba en la cárcel o ha podido salir.
                boolean pasaPorSalida = false;
                boolean tienePropietario;
                boolean caeEnJuez = false;
                casilla = juego.getJugadorActual().getCasillaActual();
                vista.mostrar("El jugador " + jugador.getNombre() + " se encuentra en la casilla número " + casilla.getNumeroCasilla());
                esperar(1);
                int valorDado = casilla.getNumeroCasilla();
                vista.mostrar("El jugador " + jugador.getNombre() + " tira el dado");
                tienePropietario = juego.jugar();
                casilla = juego.getJugadorActual().getCasillaActual();
                if (valorDado > casilla.getNumeroCasilla()) {
                    pasaPorSalida = true;
                }
                if (jugador.getEncarcelado() && casilla.getTipoCasilla() == TipoCasilla.CARCEL) { //Si el juez ha movido al jugador
                    caeEnJuez = true;
                    if (valorDado == 19) {
                        valorDado = 6;
                    } else {
                        for (int i = 4; i >= 0; i--) {
                            if (valorDado == i) {
                                valorDado = Math.abs(i - 5);
                            }
                        }
                    }
                } else {
                    valorDado -= casilla.getNumeroCasilla();
                    valorDado = -valorDado;
                    if (valorDado < 0) {
                        valorDado += 20;
                    }
                }
                esperar(0.7);
                vista.mostrar("Sale un " + valorDado);
                esperar(1);
                if (caeEnJuez) {
                    vista.mostrar("El juez manda al jugador a la cárcel.");
                    if (jugador.tengoCartaLibertad()) {
                        vista.mostrar("Pero el jugador tenía la carta de libertad, así que la usa y se libra del calabozo.");
                    }
                } else if (casilla.getTipoCasilla() == TipoCasilla.JUEZ) //Ha pagado la fianza
                {
                    int fianza = ((Especulador) (juego.getJugadorActual())).getFianza();
                    vista.mostrar("El juez intenta mandar al jugador a la cárcel, pero éste paga la fianza (" + fianza + " €.");
                }
                if (pasaPorSalida) {
                    vista.mostrar("El jugador pasa por la salida, por lo que cobra 1000€.");
                }
                vista.mostrar("El jugador " + jugador.getNombre() + " llega a la casilla " + casilla.getNumeroCasilla());
                esperar(2);
                vista.mostrar(casilla.toString());
                esperar(1.5);
                if (casilla.getTipoCasilla() == TipoCasilla.CALLE) {
                    if (tienePropietario) {
                        vista.mostrar("El propietario de la casilla es " + ((Calle) casilla).getTituloPropiedad().getPropietario().getNombre() + ". Por"
                                + " lo tanto, has de pagarle el alquiler.");
                        esperar(1);
                        ((Calle) casilla).cobrarAlquiler();
                    } else {
                        vista.mostrar("La casilla no tiene propietario.\n");
                        esperar(1);
                        boolean quiero = vista.elegirQuieroComprar();
                        if (quiero) {
                            boolean puedo;
                            puedo = juego.comprarTituloPropiedad();
                            if (puedo) {
                                vista.mostrar("Enhorabuena! Propiedad comprada!");
                            } else {
                                vista.mostrar("El jugador no tiene suficiente saldo.\n");
                            }
                            esperar(1);
                        }
                    }
                } else if (casilla.getTipoCasilla() == TipoCasilla.IMPUESTO) {
                    vista.mostrar("El jugador paga un impuesto de " + casilla.getCoste() + " euros.");
                } else if (casilla.getTipoCasilla() == TipoCasilla.PARKING) {
                    vista.mostrar("El jugador llega al parking.");
                } else if (casilla.getTipoCasilla() == TipoCasilla.SORPRESA) {
                    vista.mostrar("El jugador cae en una casilla sorpresa y saca una carta del mazo.");
                    esperar(0.7);
                    vista.mostrar("La carta es: ");
                    vista.mostrar(juego.getCartaActual().toString());
                    tienePropietario = juego.aplicarSorpresa();
                    if (juego.getCartaActual().getTipo() == TipoSorpresa.IRACASILLA) {
                        casilla = juego.getJugadorActual().getCasillaActual();
                        if (juego.getTablero().obtenerCasillaNumero(juego.getCartaActual().getValor()) == juego.getTablero().getCarcel()) {
                            vista.mostrar("La carta manda al jugador a la cárcel");
                            esperar(1);
                        } else {
                            vista.mostrar("La carta mueve al jugador a la casilla " + juego.getCartaActual().getValor() + ".");
                            if (casilla.getTipoCasilla() == TipoCasilla.CALLE) {
                                if (tienePropietario) {
                                    vista.mostrar("El propietario de la casilla es " + ((Calle) casilla).getTituloPropiedad().getPropietario().getNombre() + ". Por lo"
                                            + "tanto, has de pagarle el alquiler.");
                                    esperar(1);
                                    ((Calle) casilla).cobrarAlquiler();
                                } else {
                                    vista.mostrar("La casilla no tiene propietario.\n");
                                    esperar(1);
                                    boolean quiero = vista.elegirQuieroComprar();
                                    if (quiero) {
                                        boolean puedo;
                                        puedo = juego.comprarTituloPropiedad();
                                        if (puedo) {
                                            vista.mostrar("Enhorabuena! Propiedad comprada!");
                                        } else {
                                            vista.mostrar("El jugador no tiene suficiente saldo.\n");
                                        }
                                        esperar(1);
                                    }
                                }
                            }
                        }
                    } else if (juego.getCartaActual().getTipo() == TipoSorpresa.CONVERTIRSE) {
                        int fianza = ((Especulador) (juego.getJugadorActual())).getFianza();
                        vista.mostrar("El jugador se convierte en especulador con una fianza de " + fianza + " €.");
                    }
                }
            }
            esperar(1);
            vista.mostrarSaldo(jugador);
            esperar(1);
            jugador = juego.getJugadorActual();
            bancarrota = jugador.getSaldo() <= 0;
            //Parte 2. Gestión inmobiliaria
            boolean puedoGestionar;
            puedoGestionar = !bancarrota && !jugador.getEncarcelado() && jugador.tengoPropiedades();
            if (puedoGestionar) {
                boolean salir;
                int opcion;
                opcion = vista.menuGestionInmobiliaria();
                salir = opcion == 0;
                while (!salir && jugador.tengoPropiedades()) {
                    if (opcion != 0) {     //Si no pasamos turno
                        boolean puedo;
                        int elegida;
                        ArrayList<String> nombres;
                        Casilla seleccionada;
                        if (opcion == 1) {       //Edificar casa
                            nombres = vista.crearArrayPuedoConstruirCasa(jugador.getPropiedades(), jugador.getFactorEspeculador());
                            elegida = vista.menuElegirPropiedad(nombres);
                            if (elegida != -1) {
                                seleccionada = jugador.getPropiedades().get(elegida).getCasilla();
                                puedo = juego.edificarCasa(seleccionada);
                                if (puedo) {
                                    vista.mostrar("Casa construida.");
                                } else {
                                    vista.mostrar("Saldo insuficiente o no caben más casas.");
                                }
                            } else {
                                vista.mostrar("No hay propiedades disponibles para edificar casas.");
                            }
                        } else if (opcion == 2) {       //Edificar hotel
                            nombres = vista.crearArrayPuedoConstruirHotel(jugador.getPropiedades(), jugador.getFactorEspeculador());
                            elegida = vista.menuElegirPropiedad(nombres);
                            if (elegida != -1) {
                                seleccionada = jugador.getPropiedades().get(elegida).getCasilla();
                                puedo = juego.edificarHotel(seleccionada);
                                if (puedo) {
                                    vista.mostrar("Hotel construido.");
                                } else {
                                    vista.mostrar("Saldo o número de casas insuficientes");
                                }
                            } else {
                                vista.mostrar("No hay propiedades disponibles para edificar hoteles.");
                            }
                        } else if (opcion == 3) { //Vender propiedad
                            nombres = vista.crearArraySinHipoteca(jugador.getPropiedades());
                            elegida = vista.menuElegirPropiedad(nombres);
                            if (elegida != -1) {
                                seleccionada = jugador.getPropiedades().get(elegida).getCasilla();
                                puedo = juego.venderPropiedad(seleccionada);
                                if (puedo) {
                                    vista.mostrar("Propiedad vendida.");
                                } else {
                                    vista.mostrar("La propiedad está hipotecada.");
                                }
                            } else {
                                vista.mostrar("No hay propiedades disponibles para la venta.");
                            }
                        } else if (opcion == 4) { //Hipotecar propiedad
                            nombres = vista.crearArraySinHipoteca(jugador.getPropiedades());
                            elegida = vista.menuElegirPropiedad(nombres);
                            if (elegida != -1) {
                                seleccionada = jugador.getPropiedades().get(elegida).getCasilla();
                                puedo = juego.hipotecarPropiedad(seleccionada);
                                if (puedo) {
                                    vista.mostrar("Casilla hipotecada.");
                                } else {
                                    vista.mostrar("No se puede hipotecar.");
                                }
                            } else {
                                vista.mostrar("No hay propiedades disponibles para hipotecar.");
                            }
                        } else {               //Deshipotecar
                            nombres = vista.crearArrayHipotecadas(jugador.getPropiedades());
                            elegida = vista.menuElegirPropiedad(nombres);
                            if (elegida != -1) {
                                seleccionada = jugador.getPropiedades().get(elegida).getCasilla();
                                puedo = juego.cancelarHipoteca(seleccionada);
                                if (puedo) {
                                    vista.mostrar("Hipoteca cancelada.");
                                } else {
                                    vista.mostrar("El jugador no tiene suficiente saldo.");
                                }
                            } else {
                                vista.mostrar("No hay propiedades disponibles para cancelar la hipoteca.");
                            }
                        }
                        esperar(1);
                        vista.mostrarSaldo(jugador);
                    }
                    if (jugador.tengoPropiedades()) {
                        opcion = vista.menuGestionInmobiliaria();
                        salir = opcion == 0;
                    } else {
                        salir = true;
                    }
                }
            } //Fin del turno. Pasamos al siguiente jugador
            if (!bancarrota) {
                vista.mostrar("Pasamos al siguiente jugador.");
                juego.siguienteJugador();
                jugador = juego.getJugadorActual();
                esperar(2);
            }
        } while (!bancarrota);
        vista.mostrar("El jugador " + jugador.getNombre() + " se quedó en bancarrota.");
        esperar(1);
        vista.mostrar("*************FIN DEL JUEGO****************");
        esperar(2);
        Map<String, Integer> ranking = juego.obtenerRanking();

        vista.mostrar(ranking.toString());
    }

    public static void main(String[] args) {
        ControladorQytetet controlador = new ControladorQytetet();
        controlador.inicializacionJuego();
        controlador.desarrolloJuego();
    }

}
