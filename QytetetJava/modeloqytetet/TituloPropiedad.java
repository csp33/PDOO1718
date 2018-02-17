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
public class TituloPropiedad {

    private Casilla casilla;
    private Jugador propietario;
    private String nombre;
    private boolean hipotecada = false;
    private int alquilerBase;
    private float factorRevalorizacion;
    private int hipotecaBase;
    private int precioEdificar;

    public TituloPropiedad(String name, int aBase, float rev, int hbase, int edif, Casilla casilla) {
        this.nombre = name;
        this.alquilerBase = aBase;
        this.factorRevalorizacion = rev;
        this.hipotecaBase = hbase;
        this.precioEdificar = edif;
        this.casilla = casilla;
        this.propietario = null;
    }

    void SetHipotecada(boolean h) {
        this.hipotecada = h;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean getHipotecada() {
        return hipotecada;
    }

    int getAlquilerBase() {
        return alquilerBase;
    }

    double getFactorRevalorizacion() {
        return factorRevalorizacion;
    }

    int getHipotecaBase() {
        return hipotecaBase;
    }

    int getPrecioEdificar() {
        return precioEdificar;
    }

    void setCasilla(Casilla casilla) {
        this.casilla = casilla;
    }

    void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }

    void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public Casilla getCasilla() {
        return casilla;
    }

    public void cobrarAlquiler(int coste) {
        propietario.modificarSaldo(coste);
    }

    public boolean propietarioEncarcelado() {
        return propietario.getEncarcelado();
    }

    public boolean tengoPropietario() {
        return propietario != null;
    }

    @Override
    public String toString() {
        String hip;
        if (hipotecada) {
            hip = "Sí";
        } else {
            hip = "No";
        }
        String pro;
        if (propietario != null) {
            pro = "\nPropietario: " + propietario.getNombre();
        } else {
            pro = "";
        }
        return "\nNombre de la propiedad: " + this.nombre + pro + ".\n¿Está hipotecada? : "
                + hip + ".\nPrecio base: " + this.alquilerBase
                + ".\nFactor de revalorización: " + this.factorRevalorizacion
                + ".\nHipoteca base: " + this.hipotecaBase + ".\nPrecio de edificación: "
                + this.precioEdificar + ".\nCasas construidas: "
                + ((Calle) this.casilla).numCasas + ".\nHoteles construidos: "
                + ((Calle) this.casilla).numHoteles + ".\n";
    }
}
