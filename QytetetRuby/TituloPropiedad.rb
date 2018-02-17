#encoding = UTF-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'Calle.rb'
require_relative 'Casilla.rb'

module ModeloQytetet
  class TituloPropiedad
    
    def initialize (name,aBase,rev,hbase,edif, casilla)
      @casilla = casilla
      @propietario = nil
      @hipotecada=false
      @nombre=name
      @alquilerBase=aBase
      @factorRevalorizacion=rev
      @hipotecaBase=hbase
      @precioEdificar=edif
    end
    
    def to_s
      return "Nombre de la propiedad: #{@nombre}.
          Hipotecada : #{@hipotecada}.
          Factor de revalorización: #{@factorRevalorizacion}.
          Hipoteca base: #{@hipotecaBase}.
          Precio de edificación: #{@precioEdificar}.
      "
    end
    
    
    attr_reader :alquilerBase , :factorRevalorizacion , :hipotecaBase , :nombre , :precioEdificar
    attr_accessor :hipotecada, :propietario , :casilla
    
    def cobrarAlquiler(coste)
      if @propietario != nil
        @propietario.modificarSaldo(coste);
      end
    end
    
    def propietarioEncarcelado
      return @propietario.encarcelado
    end
    
    def tengoPropietario
      return @propietario != nil
    end
    
  end
end