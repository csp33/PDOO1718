//     ___            _            _            _   
//    / _ \   _   _  | |_    ___  | |_    ___  | |_ 
//   | | | | | | | | | __|  / _ \ | __|  / _ \ | __|
//   | |_| | | |_| | | |_  |  __/ | |_  |  __/ | |_ 
//    \__\_\  \__, |  \__|  \___|  \__|  \___|  \__|
//            |___/                                 
package InterfazTextualQytetet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import modeloqytetet.Calle;
import modeloqytetet.Jugador;
import modeloqytetet.MetodoSalirCarcel;
import modeloqytetet.TituloPropiedad;

/**
 *
 * @author csp98
 */
public class VistaTextualQytetet {

    private static final Scanner in = new Scanner(System.in);

    public VistaTextualQytetet() {

    }

    public int menuGestionInmobiliaria() { //ejemplo de menú

        this.mostrar("Elige la gestion inmobiliaria que deseas hacer");
        Map<Integer, String> menuGI = new TreeMap();
        menuGI.put(0, "Siguiente Jugador");
        menuGI.put(1, "Edificar casa");
        menuGI.put(2, "Edificar Hotel");
        menuGI.put(3, "Vender propiedad ");
        menuGI.put(4, "Hipotecar Propiedad");
        menuGI.put(5, "Cancelar Hipoteca");
        int salida = this.seleccionMenu(menuGI); // Método para controlar la elección correcta en el menú 
        return salida;
    }

    MetodoSalirCarcel menuSalirCarcel() {
        Map<Integer, String> menuSalir;
        menuSalir = new HashMap();
        menuSalir.put(0, "Pagar la libertad");
        menuSalir.put(1, "Tirar el dado");
        MetodoSalirCarcel metodo;
        int opcion;
        opcion = seleccionMenu(menuSalir);
        if (opcion == 0) {
            metodo = MetodoSalirCarcel.PAGANDOLIBERTAD;
        } else {
            metodo = MetodoSalirCarcel.TIRANDODADO;
        }
        return metodo;

    }

    public boolean elegirQuieroComprar() {
        boolean quiero;
        Map<Integer, String> menuComprar;
        menuComprar = new HashMap();
        menuComprar.put(0, "Comprar la propiedad");
        menuComprar.put(1, "Pasar el turno");
        int opcion = seleccionMenu(menuComprar);
        if (opcion == 0) {
            quiero = true;
        } else {
            quiero = false;
        }
        return quiero;
    }

    public int menuElegirPropiedad(ArrayList<String> listaPropiedades) {  //numero y nombre de propiedades            
        Map<Integer, String> menuEP = new TreeMap();
        int numeroOpcion = 0;
        for (String prop : listaPropiedades) {
            menuEP.put(numeroOpcion, prop); //opcion de menu, numero y nombre de propiedad
            numeroOpcion = numeroOpcion + 1;
        }
        int salida = this.seleccionMenu(menuEP); // Método para controlar la elección correcta en el menú 
        return salida;

    }

    private int seleccionMenu(Map<Integer, String> menu) //Método para controlar la elección correcta de una opción en el menú que recibe como argumento   
    {
        if (!menu.isEmpty()) {
            boolean valido = true;
            int numero;
            String lectura;
            do { // Hasta que se hace una selección válida
                for (Map.Entry<Integer, String> fila : menu.entrySet()) {
                    numero = fila.getKey();
                    String texto = fila.getValue();
                    this.mostrar(numero + " : " + texto);  // número de opción y texto
                }
                this.mostrar("\n Elige una opción: ");
                lectura = in.nextLine();  //lectura de teclado
                valido = this.comprobarOpcion(lectura, 0, menu.size() - 1); //método para comprobar la elección correcta
            } while (!valido);
            return Integer.parseInt(lectura);
        } else {
            return -1;
        }
    }

    public ArrayList<String> obtenerNombreJugadores() { //método para pedir el nombre de los jugadores
        boolean valido;
        String lectura;
        ArrayList<String> nombres = new ArrayList();
        do { //repetir mientras que el usuario no escriba un número correcto 
            this.mostrar("Escribe el número de jugadores: (de 2 a 4):");
            lectura = in.nextLine();  //lectura de teclado
            valido = this.comprobarOpcion(lectura, 2, 4); //método para comprobar la elección correcta
        } while (!valido);

        for (int i = 1; i <= Integer.parseInt(lectura); i++) { //solicitud del nombre de cada jugador
            this.mostrar("Nombre del jugador " + i + ": ");
            nombres.add(in.nextLine());
        }
        return nombres;
    }

    private boolean comprobarOpcion(String lectura, int min, int max) {
//método para comprobar que se introduce un entero correcto, usado por seleccion_menu
        boolean valido = true;
        int opcion;
        try {
            opcion = Integer.parseInt(lectura);
            if (opcion < min || opcion > max) { // No es un entero entre los válidos
                this.mostrar("el numero debe estar entre " + min + " y " + "max");
                valido = false;
            }

        } catch (NumberFormatException e) { // No se ha introducido un entero
            this.mostrar("debes introducir un numero");
            valido = false;
        }
        if (!valido) {
            this.mostrar("\n\n Seleccion erronea. Intentalo de nuevo.\n\n");
        }
        return valido;
    }

    public void mostrar(String texto) { //método que muestra en pantalla el string que recibe como argumento

        System.out.println(texto);
    }

    void mostrarSaldo(Jugador jugador) {
        mostrar("El saldo del jugador " + jugador.getNombre() + " es de " + jugador.getSaldo() + " euros.\n");
    }

    ArrayList<String> crearArrayPropiedades(ArrayList<TituloPropiedad> propiedades) {
        ArrayList<String> nombres = new ArrayList();
        for (TituloPropiedad p : propiedades) {
            nombres.add(p.getNombre());
        }
        return nombres;
    }

    ArrayList<String> crearArrayHipotecadas(ArrayList<TituloPropiedad> propiedades) {
        ArrayList<String> nombres = new ArrayList();
        for (TituloPropiedad p : propiedades) {
            if (p.getHipotecada()) {
                nombres.add(p.getNombre());
            }
        }
        return nombres;
    }

    ArrayList<String> crearArraySinHipoteca(ArrayList<TituloPropiedad> propiedades) {
        ArrayList<String> nombres = new ArrayList();
        for (TituloPropiedad p : propiedades) {
            if (!p.getHipotecada()) {
                nombres.add(p.getNombre());
            }
        }
        return nombres;
    }

    ArrayList<String> crearArrayPuedoConstruirCasa(ArrayList<TituloPropiedad> propiedades, int factor) {
        ArrayList<String> nombres = new ArrayList();
        for (TituloPropiedad p : propiedades) {
            if (((Calle) p.getCasilla()).sePuedeEdificarCasa(factor)) {
                nombres.add(p.getNombre());
            }
        }
        return nombres;
    }

    ArrayList<String> crearArrayPuedoConstruirHotel(ArrayList<TituloPropiedad> propiedades, int factor) {
        ArrayList<String> nombres = new ArrayList();
        for (TituloPropiedad p : propiedades) {
            if (((Calle) p.getCasilla()).sePuedeEdificarHotel(factor)) {
                nombres.add(p.getNombre());
            }
        }
        return nombres;
    }

    public void obtenerPosicionJugadores(ArrayList<Jugador> jugadores) {
        for (Jugador j : jugadores) {
            System.out.println("El jugador " + j.getNombre() + " se encuentra en la casilla " + j.getCasillaActual().getNumeroCasilla() + "\n");
        }
    }

}
