package modeloqytetet;

import java.util.ArrayList;

/**
 *
 * @author csp98
 */
public class Tablero {

    private ArrayList<Casilla> casillas;
    private Casilla carcel;

    public Tablero() {
        inicializar();
    }

    private void inicializar() {
        casillas = new ArrayList();
        ArrayList<TituloPropiedad> aux = new ArrayList();
        int i = 0;

        casillas.add(new OtraCasilla(i, 0, TipoCasilla.SALIDA));        //0
        aux.add(null); //Para que concuerde con el índice     
        i++;

        casillas.add(new Calle(i, 350));
        aux.add(new TituloPropiedad("Calle de la esperanza", 55, (float) 0.1, 150, 250, ((Calle)(casillas.get(i)))));      //1
         ((Calle)(casillas.get(i))).setTitulo(aux.get(i));
        i++;

        casillas.add(new Calle(i, 420));
        aux.add(new TituloPropiedad("Avenida del estudia-dia-antes", 56, (float) 0.105, 175, 275, ((Calle)(casillas.get(i)))));        //2
        ((Calle)(casillas.get(i))).setTitulo(aux.get(i));
        i++;

        casillas.add(new OtraCasilla(i, 0, TipoCasilla.SORPRESA));
        aux.add(null); //Para que concuerde con el índice                       //3
        i++;

        casillas.add(new Calle(i, 510));
        aux.add(new TituloPropiedad("Torre de apuntes", 57, (float) 0.11, 200, 300, ((Calle)(casillas.get(i)))));      //4
        ((Calle)(casillas.get(i))).setTitulo(aux.get(i));
        i++;

        casillas.add(new OtraCasilla(i, 0, TipoCasilla.JUEZ));                  //5
        aux.add(null); //Para que concuerde con el índice      

        i++;

        casillas.add(new Calle(i, 690));
        aux.add(new TituloPropiedad("Callejón del café", 75, (float) 0.15, 300, 320, ((Calle)(casillas.get(i)))));     //6
        ((Calle)(casillas.get(i))).setTitulo(aux.get(i));
        i++;

        casillas.add(new Calle(i, 1000));
        aux.add(new TituloPropiedad("Desembarco del estrés", 76, (float) 0.1525, 325, 370, ((Calle)(casillas.get(i)))));       //7
        ((Calle)(casillas.get(i))).setTitulo(aux.get(i));
        i++;

        casillas.add(new OtraCasilla(i, 450, TipoCasilla.IMPUESTO));
        aux.add(null); //Para que concuerde con el índice                               //8

        i++;

        casillas.add(new Calle(i, 1350));
        aux.add(new TituloPropiedad("Cuesta del examen sorpresa", 77, (float) 0.155, 350, 420, ((Calle)(casillas.get(i)))));       //9
        ((Calle)(casillas.get(i))).setTitulo(aux.get(i));
        i++;

        casillas.add(new OtraCasilla(i, 0, TipoCasilla.CARCEL));                                                        //10
        aux.add(null); //Para que concuerde con el índice      

        i++;

        casillas.add(new Calle(i, 2100));
        aux.add(new TituloPropiedad("Vía de la noche en vela", 85, (float) -0.1, 600, 500, ((Calle)(casillas.get(i)))));           //11
        ((Calle)(casillas.get(i))).setTitulo(aux.get(i));
        i++;

        casillas.add(new Calle(i, 2450));
        aux.add(new TituloPropiedad("Avenida de la siesta", 86, (float) -0.105, 650, 525, ((Calle)(casillas.get(i)))));            //12
        ((Calle)(casillas.get(i))).setTitulo(aux.get(i));
        i++;

        casillas.add(new OtraCasilla(i, 0, TipoCasilla.SORPRESA));                                                          //13
        aux.add(null); //Para que concuerde con el índice      

        i++;

        casillas.add(new Calle(i, 2800));
        aux.add(new TituloPropiedad("Explanada del viernes noche", 87, (float) -0.11, 700, 550, ((Calle)(casillas.get(i)))));      //14
        ((Calle)(casillas.get(i))).setTitulo(aux.get(i));
        i++;

        casillas.add(new OtraCasilla(i, 0, TipoCasilla.PARKING));
        aux.add(null); //Para que concuerde con el índice                                                               //15

        i++;

        casillas.add(new Calle(i, 3500));
        aux.add(new TituloPropiedad("Avenida del aprobado general", 95, (float) -0.15, 850, 600, ((Calle)(casillas.get(i)))));         //16
        ((Calle)(casillas.get(i))).setTitulo(aux.get(i));
        i++;

        casillas.add(new Calle(i, 4800));
        aux.add(new TituloPropiedad("Bulevar de las esperanzas rotas", 96, (float) -0.1525, 900, 625, ((Calle)(casillas.get(i)))));       //17
        ((Calle)(casillas.get(i))).setTitulo(aux.get(i));
        i++;

        casillas.add(new OtraCasilla(i, 0, TipoCasilla.SORPRESA));
        aux.add(null); //Para que concuerde con el índice                                                                       //18
        i++;

        casillas.add(new Calle(i, 5400));
        aux.add(new TituloPropiedad("Calle de los tempranos desertores", 97, (float) -0.155, 950, 650, ((Calle)(casillas.get(i)))));       //19
        ((Calle)(casillas.get(i))).setTitulo(aux.get(i));
        i++;
        
        carcel = casillas.get(10);
    }

    public Casilla getCarcel() {
        return carcel;
    }

    public Casilla obtenerCasillaNumero(int numeroCasilla) {
        return casillas.get(numeroCasilla);
    }

    Casilla obtenerNuevaCasilla(Casilla casilla, int desplazamiento) {
        int pos = (desplazamiento + casilla.getNumeroCasilla()) % casillas.size();
        return casillas.get(pos);
    }

    @Override
    public String toString() {
        String miCadena = "";
        for (int i = 0; i < casillas.size(); i++) {
            miCadena += "\t\tCasilla numero " + i + "\n" + ((Calle)(casillas.get(i))).toString() + "\n\n";
        }
        return miCadena;
    }

    boolean esCasillaCarcel(int numeroCasilla) {
        return casillas.get(numeroCasilla).getTipoCasilla() == TipoCasilla.CARCEL;
    }

    public int getTamanio() {
        return casillas.size();
    }

}
