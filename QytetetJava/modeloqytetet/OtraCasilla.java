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
public class OtraCasilla extends Casilla {

    public OtraCasilla(int num, int c, TipoCasilla type) {
        super(num, c, type);
    }

    @Override
    public String toString() {
        String cost = ".\n";
        if (coste != 0) {
            cost = "Coste: " + coste + ".\n";
        }
        return "\nNumero de casilla: " + this.numeroCasilla + ".\n" + cost + "Tipo de casilla: " + this.tipo + "\n";
    }

    @Override
    boolean soyEdificable() {
        return false;
    }

}
