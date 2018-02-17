#encoding: utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'TipoCasilla'
require_relative 'TituloPropiedad'
module ModeloQytetet
  class Casilla
    #attr_reader :coste, :numeroCasilla , :tipo
    #attr_accessor :numCasas, :numHoteles , :titulo

    #  def initialize (num,c,type,prop)
    #   @numeroCasilla=num
    #  @coste=c
    # @numCasas=0
    #@numHoteles=0
    #@tipo=type
    #@titulo=prop
    #end
    def initialize(num,c,type)
      @numeroCasilla=num
      @coste=c
      @tipo=type
    end
    
    def soyEdificable
      #soy_edificable = false
      #if (@tipo == TipoCasilla::CALLE)
      #  soy_edificable = true
      #end
      #return soy_edificable
      return false
    end
    
    def tengoPropietario
      return false
    end
    
    def to_s
      cost=""
      if @coste != 0
        cost="Coste: #{@coste}."
      end
      return "Numero de casilla: #{@numeroCasilla}.#{cost}\nTipo de casilla: #{@tipo}\n\n"
    end
    
    attr_reader :coste,:numeroCasilla,:tipo
    # def self.creaCalle(num,c)
    #  new(num,c,TipoCasilla::CALLE,nil)
    #end

    # def self.creaCasilla(num,c,type)
    #  new(num,c,type,nil)
    #end


    #   def to_s
    # if @tipo == TipoCasilla::CALLE
    #      pro= "Título de propiedad :\n#{@titulo}"
    #    else
    #      pro=""
    #    end
    #    "Numero de casilla: #{@numeroCasilla}.\nCoste de compra: #{@coste}\n" +
    #     "Número de casas construidas: #{@numCasas}.\nNúmero de hoteles construidos: #{@numHoteles}.\n" +
    #     "Tipo de casilla: #{@tipo}.\n#{pro}\n"
    # end

    #   def calcularValorHipoteca
    #    hipotecaBase=@titulo.hipotecaBase
    #    cantidadRecibida = hipotecaBase + Integer( @numCasas * 0.5 * hipotecaBase + @numHoteles * hipotecaBase)
    #    return cantidadRecibida
    #  end

    #  def cancelarHipoteca
    #    @titulo.hipotecada=false
    #    aPagar=getCosteHipoteca
    #    return aPagar
    #  end

    #def cobrarAlquiler
    #  costeAlquilerBase=@titulo.alquilerBase
    #   costeAlquiler = costeAlquilerBase + @numCasas * 0.5 + @numHoteles * 2
    #   @titulo.cobrarAlquiler(costeAlquiler)
    #   return costeAlquiler
    # end

    # def edificarCasa
    #   @numCasas+=1
    #   costeEdificarCasa=getPrecioEdificar
    #   return costeEdificarCasa
    # end

    # def edificarHotel
    #   @numHoteles+=1
    #   costeEdificarHotel=getPrecioEdificar
    #   return costeEdificarHotel
    #  end

    #def estaHipotecada
    #  return @titulo.hipotecada
    # end

    #    def getCosteHipoteca
    #      coste = calcularValorHipoteca * 1.10
    #     return Integer(coste)
    #   end

    #   def getPrecioEdificar
    #     return @titulo.precioEdificar

    #   end

    #   def hipotecar
    #     @titulo.hipotecada=true
    #     cantidadRecibida=calcularValorHipoteca
    ##     return cantidadRecibida
    #   end

    #   def propietarioEncarcelado
    #     return @encarcelado
    #   end

    #   def sePuedeEdificarCasa(factorEspeculador)
    #     return @numCasas < 4*factorEspeculador
    #   end

    #   def sePuedeEdificarHotel(factorEspeculador)
    #     return @numHoteles < 4*factorEspeculador && @numCasas == 4*factorEspeculador
    #   end

 

    #   def tengoPropietario
    #     if @titulo != nil
    #       return @titulo.propietario != nil
    #     end
    #   end

    #   def venderTitulo
    ##     precioCompra = @coste + (@numCasas + @numHoteles)*@titulo.precioEdificar
    #     precioVenta = precioCompra + @titulo.factorRevalorizacion * precioCompra
    #     @titulo.propietario= nil
    #     @numHoteles=0
    #     @numCasas=0
    #     return Integer(precioVenta)
    #   end
  end
end
