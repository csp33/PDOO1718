#encoding: utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'Casilla.rb'
module ModeloQytetet
  class Calle < Casilla
    def initialize(num,c)
      super(num,c,TipoCasilla::CALLE)      
      @titulo=nil  
      @numCasas=0
      @numHoteles=0
    end
        
    attr_accessor :numCasas, :numHoteles , :titulo

    def calcularValorHipoteca
      hipotecaBase=@titulo.hipotecaBase
      cantidadRecibida = hipotecaBase + Integer( @numCasas * 0.5 * hipotecaBase + @numHoteles * hipotecaBase)
      return cantidadRecibida
    end

    def cancelarHipoteca
      @titulo.hipotecada=false
      aPagar=getCosteHipoteca
      return aPagar
    end

    def cobrarAlquiler
      costeAlquilerBase=@titulo.alquilerBase
      costeAlquiler = costeAlquilerBase + @numCasas * 0.5 + @numHoteles * 2
      @titulo.cobrarAlquiler(costeAlquiler)
      return costeAlquiler
    end

    def edificarCasa
      @numCasas+=1
      costeEdificarCasa=getPrecioEdificar
      return costeEdificarCasa
    end

    def edificarHotel
      @numHoteles+=1
      costeEdificarHotel=getPrecioEdificar
      return costeEdificarHotel
    end

    def estaHipotecada
      return @titulo.hipotecada
    end
    def getCosteHipoteca
      coste = calcularValorHipoteca * 1.10
      return Integer(coste)
    end

    def getPrecioEdificar
      return @titulo.precioEdificar
    end

    def hipotecar
      @titulo.hipotecada=true
      cantidadRecibida=calcularValorHipoteca
      return cantidadRecibida
    end

    def propietarioEncarcelado
      return @encarcelado
    end

    def sePuedeEdificarCasa(factorEspeculador)
      maximo=4*factorEspeculador
      return @numCasas < maximo
    end

    def sePuedeEdificarHotel(factorEspeculador)
      maximo=4*factorEspeculador
      return @numHoteles < maximo && @numCasas == maximo
    end

    def soyEdificable
      #soy_edificable = false
      #if (@tipo == TipoCasilla::CALLE)
      #  soy_edificable = true
      #end
      #return soy_edificable
      return true
    end

    def tengoPropietario
      return @titulo.propietario != nil
    end

    def venderTitulo
      precioCompra = @coste + (@numCasas + @numHoteles)*@titulo.precioEdificar
      precioVenta = precioCompra + @titulo.factorRevalorizacion * precioCompra
      @titulo.propietario= nil
      @numHoteles=0
      @numCasas=0
      return Integer(precioVenta)
    end
    
    def to_s  
      return "Numero de casilla: #{@numeroCasilla}.\nCoste de compra: #{@coste}\n" +
        "Número de casas construidas: #{@numCasas}.\nNúmero de hoteles construidos: #{@numHoteles}.\n" +
        "Tipo de casilla: #{@tipo}.\nTítulo de propiedad :\n#{@titulo}\n"
    end
  end
end
