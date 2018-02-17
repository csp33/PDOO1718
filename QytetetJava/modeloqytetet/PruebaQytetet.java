//     ___            _            _            _   
//    / _ \   _   _  | |_    ___  | |_    ___  | |_ 
//   | | | | | | | | | __|  / _ \ | __|  / _ \ | __|
//   | |_| | | |_| | | |_  |  __/ | |_  |  __/ | |_ 
//    \__\_\  \__, |  \__|  \___|  \__|  \___|  \__|
//            |___/                                 
package modeloqytetet;

import java.util.ArrayList;

/**
 *
 * @author csp98
 */
public class PruebaQytetet {

    /**
     * @param args the command line arguments
     */
    private static ArrayList<Sorpresa> mazo = new ArrayList();

    private static void inicializarSorpresas() {
        mazo.add(new Sorpresa("Se te vienen encima los exámenes finales e intentas salvar"
                + "el curso con una academia. Paga 100€ para clases", -100, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Sacas matrícula de honor en una asignatura y la universidad"
                + "te devuelve el pago de una matrícula. Recibes 100€", 100, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Suspendes una asignatura y no te guardan las prácticas. Vuelve al punto de salida", 0, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Pierdes las llaves de casa y pasas una noche en la casilla 8. Ve a la casilla 8", 8, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Te pillan copiando una práctica. Vas a la cárcel",
                9, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Los inquilinos de tus propiedades pagan su estancia. Recibe 100€ por casa y 200 por hotel", +100, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Das una macrofiesta en tus propiedades pero la cosa se desmadra."
                + " Paga 75€ por cada casa que tengas y 150€ por hotel.", -75, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Cada jugador te hace un regalo de 75€ por ser buena gente. Recibe 75€ por cada jugador", +75, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("No te cuentan las prácticas en la convocatoria extraordinaria y tu nota"
                + "se queda en 4,89. Lo siento, pero pagas segunda matrícula", -160, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("Aquel chaval que aprobó programación gracias a ti paga tu fianza."
                + "Eres libre de nuevo.", 0, TipoSorpresa.SALIRCARCEL));
    }

    private static ArrayList sorpresasMayoresQueCero() {
        ArrayList<Sorpresa> sorpresas = new ArrayList();
        for (int i = 0; i < mazo.size(); i++) {
            if (mazo.get(i).getValor() > 0) {
                sorpresas.add(mazo.get(i));
            }
        }
        return sorpresas;
    }

    private static ArrayList sorpresasTipoIrACasilla() {
        ArrayList<Sorpresa> sorpresas = new ArrayList();
        for (int i = 0; i < mazo.size(); i++) {
            if (mazo.get(i).getTipo() == TipoSorpresa.IRACASILLA) {
                sorpresas.add(mazo.get(i));
            }
        }
        return sorpresas;
    }

    private static ArrayList sorpresasTipo(TipoSorpresa tipo) {
        ArrayList<Sorpresa> sorpresas = new ArrayList();
        for (int i = 0; i < mazo.size(); i++) {
            if (mazo.get(i).getTipo() == tipo) {
                sorpresas.add(mazo.get(i));
            }
        }
        return sorpresas;
    }

    public static void esperar(double segundos) {
        try {
            Thread.sleep((int) (segundos * 1000));
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public static void imprimeLogo() {
        System.out.println("                   /\\             /\\\n"
                + "                  |`\\\\_,--=\"=--,_//`|\n"
                + "                  \\ .\"  :'. .':  \". /\n"
                + "                 ==)  _ :  '  : _  (==\n"
                + "                   |>/O\\   _   /O\\<|\n"
                + "                   | \\-\"~` _ `~\"-/ |\n"
                + "                  >|`===. \\_/ .===`|<\n"
                + "            .-\"-.   \\==='  |  '===/   .-\"-.\n"
                + ".----------{'. '`}---\\,  .-'-.  ,/---{.'. '}----------.\n"
                + " )         `\"---\"`     `~-===-~`     `\"---\"`         (\n"
                + "(            ___        _       _       _             )\n"
                + " )          / _ \\ _   _| |_ ___| |_ ___| |_          (\n"
                + "(          | | | | | | | __/ _ \\ __/ _ \\ __|          )\n"
                + " )         | |_| | |_| | ||  __/ ||  __/ |_          (\n"
                + "(           \\__\\_\\\\__, |\\__\\___|\\__\\___|\\__|          )\n"
                + " )                |___/                              (\n"
                + "'-----------------------------------------------------'\n\n");

    }

    public static void main(String[] args) {

        imprimeLogo();
        esperar(2);
        System.out.println("/*******PROBANDO CASILLA ***********/");
        Casilla miCasilla = new OtraCasilla(6, 30, TipoCasilla.SORPRESA);
        System.out.println(miCasilla.toString());
        esperar(1);
        /**
         * *************************************************
         */
        //  System.out.println("/*******PROBANDO DADO ***********/");
        // Dado miDado = Dado.getInstance();
        //for(int i=0;i<3;i++){
        //      System.out.println("Tirando dado : \t " + miDado.tirar());
        //     esperar(0.5);
        // }
        //esperar(1);
        /**
         * *************************************************
         */
        System.out.println("/*******PROBANDO JUGADOR ***********/");
        Jugador miJugador = new Jugador("Juan");
        System.out.println(miJugador.toString());
        esperar(1);
        /**
         * *************************************************
         */

        System.out.println("/*******PROBANDO SORPRESA ***********/");
        Sorpresa miSorpresa = new Sorpresa("Sorpresa de prueba", 50, TipoSorpresa.PAGARCOBRAR);
        System.out.println(miSorpresa.toString());
        esperar(1);

        /**
         * *************************************************
         */
        System.out.println("/*******PROBANDO TABLERO ***********/");
        Tablero miTablero = new Tablero();
        System.out.println(miTablero.toString());
        esperar(1);

        /**
         * *************************************************
         */
        System.out.println("/*******PROBANDO TITULO PROPIEDAD ***********/");
        TituloPropiedad miPropiedad = new TituloPropiedad("Título", 10, (float) 0.7, 150, 30, miCasilla);
        System.out.println(miPropiedad.toString());
        esperar(1);

        /**
         * *************************************************
         */
        System.out.println("/*******PROBANDO QYTETET ***********/");
        ArrayList<String> jugadores = new ArrayList();
        jugadores.add("Carlos");
        jugadores.add("David");
        jugadores.add("Jose");
        jugadores.add("Jesus");
        Qytetet partida = Qytetet.getInstance(jugadores);
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.println(partida.getJugadorActual().toString());
            partida.siguienteJugador();
        }

    }

}
