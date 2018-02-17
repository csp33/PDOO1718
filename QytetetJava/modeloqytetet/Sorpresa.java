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
public class Sorpresa {
      private String descripcion;
      private TipoSorpresa tipo;
      private int valor;
           
      public Sorpresa(String d,int value, TipoSorpresa type){
          this.descripcion=d;
          this.tipo=type;
          this.valor=value;
      }
      
      public Sorpresa(Sorpresa otra){
          descripcion=otra.getDescripcion();
          tipo=otra.getTipo();
          valor=otra.getValor();
      }
      
      public String getDescripcion(){
          return this.descripcion;
      }   
      public int getValor(){
          return this.valor;
      }  
      public TipoSorpresa getTipo(){
          return this.tipo;
      }
      @Override
      public String toString(){
          return "Texto=" + descripcion + "\nValor=" + 
                  Integer.toString(valor) + "\nTipo=" + tipo + "\n";
      }
          
}
