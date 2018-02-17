#encoding: utf-8

module ModeloQytetet

  require_relative 'Sorpresa'
  require_relative 'Casilla'
  require_relative 'TituloPropiedad'
  require_relative 'Especulador'

  class Jugador
    def initialize(name)
      @cartaLibertad
      @casillaActual
      @encarcelado=false
      @nombre=name
      @saldo=0
      @propiedades= Array.new
    end
    attr_writer :casillaActual,:propiedades
    attr_reader  :encarcelado , :casillaActual , :nombre, :saldo , :propiedades,:cartaLibertad

    def factorEspeculador
      return 1
    end
    
    def tengoPropiedades
      return @propiedades.size != 0
    end
    
    def to_s
      return  "Jugador: #{@nombre}.\nEncarcelado: #{@encarcelado}\n" +
        "Saldo: #{@saldo}.\nCasilla actual: #{@casillaActual}.\n" +
        "Títulos de propiedades:: #{@propiedades}.\n"
    end
    
    def modificarSaldo(cantidad)
      @saldo += cantidad
    end
    attr_writer :encarcelado, :cartaLibertad

    def obtenerCapital
      capital = @saldo
      cap_prop = 0
      i = 0
      for i in @propiedades.size
        cap_prop += @propiedades[i].alquilerBase + @propiedades[i].casilla.coste *
        (@propiedades[i].casilla.numCasas +@propiedades[i].casilla.numHoteles*4)
        if (@propiedades[i].hipotecada)
          cap_prop -= @propiedades[i].hipotecaBase
        end
        capital += cap_prop
        cap_prop = 0
      end
      return capital
    end

    def comprarTitulo
      puedo_comprar = false
      if @casillaActual.soyEdificable
        if !@casillaActual.tengoPropietario
          if @casillaActual.coste <= @saldo
            puedo_comprar = true
            titulo=@casillaActual.titulo
            titulo.propietario=self
            @propiedades<<titulo
            modificarSaldo(-@casillaActual.coste)
          end
        end
      end
      return puedo_comprar
    end

    def devolverCartaLibertad
      aux = @cartaLibertad
      @cartaLibertad = nil
      return aux
    end

    def irACarcel (casilla)
      @casillaActual=casilla
      @encarcelado=true
    end

    def actualizarPosicion (casilla)
      if (casilla.numeroCasilla < @casillaActual.numeroCasilla)
        modificarSaldo(1000)
      end
      @casillaActual = casilla
      if casilla.soyEdificable
        if casilla.tengoPropietario
          if not casilla.propietarioEncarcelado
            coste = casilla.cobrarAlquiler()
            modificarSaldo(-coste)
          end
        end
      elsif casilla.tipo == TipoCasilla::IMPUESTO
        coste = casilla.coste
        pagarImpuestos(coste)
      end
      return casilla.tengoPropietario
    end

    def obtenerPropiedadesHipotecadas(hipotecada)
      aux = Array.new
      for i in @propiedades.size
        if (@propiedades[i].hipotecada == hipotecadas)
          aux << @propiedades[i]
        end
      end
      return aux
    end

    def pagarCobrarPorCasaYHotel(cantidad)
      numeroTotal=cuantasCasasHotelesTengo
      cantidad*=numeroTotal
      modificarSaldo(cantidad)
    end

    def pagarLibertad(cantidad)
      tengoSaldo=tengoSaldo(cantidad)
      if tengoSaldo
        modificarSaldo(-cantidad)
      end
    end

    def puedoEdificarCasa(casilla)
      esMia = esDeMiPropiedad(casilla)
      puedoEdificar = false
      if (esMia)
        coste_edificar_casa = casilla.titulo.precioEdificar
        tengoSaldo = tengoSaldo(coste_edificar_casa)
        puedoEdificar = esMia && tengoSaldo
      end
      return puedoEdificar
    end

    def puedoEdificarHotel(casilla)
      esMia = esDeMiPropiedad(casilla)
      puedoEdificar = false
      if (esMia)
        coste_edificar_hotel = casilla.titulo.precioEdificar
        tengoSaldo = tengoSaldo(coste_edificar_hotel)
        puedoEdificar = esMia && tengoSaldo
      end
      return puedoEdificar
    end

    def puedoHipotecar(casilla)
      esMia = esDeMiPropiedad(casilla)
      return esMia
    end

    def puedoPagarHipoteca(casilla)
      esMia=esDeMiPropiedad(casilla)
      tengoSaldo=tengoSaldo( Integer(casilla.getCosteHipoteca) )
      return esMia && tengoSaldo
    end

    def puedoVenderPropiedad(casilla)
      return esDeMiPropiedad(casilla) && !casilla.estaHipotecada
    end

    def tengoCartaLibertad
      return @cartaLibertad != nil
    end

    def venderPropiedad(casilla)
      precioVenta=casilla.venderTitulo
      modificarSaldo(precioVenta)
      eliminarDeMisPropiedades(casilla.titulo)
    end


    def cuantasCasasHotelesTengo
      num_total = 0
      i = 0
      for i in @propiedades.size
        num_total += @propiedades[i].casilla.numCasas+ @propiedades[i].casilla.numHoteles
      end
      return num_total
    end

    def eliminarDeMisPropiedades(casilla)
      @propiedades.delete(casilla)
    end

    def esDeMiPropiedad(casilla)
      return @propiedades.include?(casilla.titulo)
    end

    def tengoSaldo(cantidad)
      return @saldo >= cantidad
    end
    
    def pagarImpuestos(cantidad)
      modificarSaldo(-cantidad)
    end
    
    def convertirme(fianza)
      copia=Especulador.new(self,fianza)
      return copia      
    end
    
    # def self.constructorCopia(jugador)
    #  nuevo=self.new(jugador.nombre)
    # nuevo.cartaLibertad=@cartaLibertad
    #nuevo.casillaActual=@casillaActual
    #nuevo.encarcelado=@encarcelado
    #nuevo.saldo=@saldo
    #nuevo.propiedades=@propiedades
    #return nuevo
    #end
      
    protected :pagarImpuestos
  end
end

