#encoding: utf-8

# To change thi s license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'Jugador.rb'
module ModeloQytetet
  class Especulador < Jugador
    def initialize(jugador,fianza)
      @cartaLibertad=jugador.cartaLibertad
      @casillaActual=jugador.casillaActual
      @encarcelado=jugador.encarcelado
      @nombre=jugador.nombre
      @saldo=jugador.saldo
      @propiedades= jugador.propiedades
      @fianza=fianza
    end
    
    attr_reader :fianza
    
    def pagarImpuestos(cantidad)
      mitad=cantidad/2
      modificarSaldo(-mitad)
    end
    
    def irACarcel(casilla)
      puedoPagar=pagarFianza(@fianza)
      if !puedoPagar
        super #Llamo a irACarcel de la superclase
      end
    end
    
    def convertirme(fianza)
      return self
    end
    
    def pagarFianza(cantidad) #Devuelve true si se libra de la cárcel
      tengoSaldo=tengoSaldo(cantidad)
      if tengoSaldo
        modificarSaldo(-cantidad)
      end
      return tengoSaldo
    end
    
    def factorEspeculador
      return 2
    end
    
    def to_s
      return  "Especulador: #{@nombre}.\nFianza: #{@fianza}.\nEncarcelado: #{@encarcelado}\n" +
        "Saldo: #{@saldo}.\nCasilla actual: #{@casillaActual}.\n" +
        "Títulos de propiedades: #{@propiedades}.\n"
    end
    
    protected :pagarImpuestos,:irACarcel,:convertirme
    private :pagarFianza
  end
end
