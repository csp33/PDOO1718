#encoding: utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require "singleton"



require_relative 'Casilla'
require_relative 'TipoCasilla'
require_relative 'MetodoSalirCarcel'
require_relative 'Tablero'
require_relative 'TipoSorpresa'
require_relative 'Sorpresa'
require_relative 'Jugador'
require_relative 'Especulador'
require_relative 'Dado'

module ModeloQytetet
  class Qytetet
    include Singleton
    def initialize
      @CartaActual=nil
      @jugadores=Array.new
      @mazo=Array.new
      @jugadorActual=nil
      @tablero=nil
      @dado=Dado.new
    end

    public

    attr_reader :MAX_JUGADORES,:MAX_CARTAS,:MAX_CASILLAS,:PRECIO_LIBERTAD,:SALDO_SALIDA
    @@MAX_JUGADORES=4
    @@MAX_CARTAS=10
    @@MAX_CASILLAS=20
    @@PRECIO_LIBERTAD=200
    @@SALDO_SALIDA=1000
    attr_reader :cartaActual , :jugadorActual , :jugadores, :tablero , :mazo
    
    def inicializarJugadores (nombres)
      i=0
      while i<nombres.size
        @jugadores<<Jugador.new(nombres[i])
        i=i+1
      end
    end
    
    def barajarCartas
      veces= rand(10)+1
      i=0
      while i!=veces
        j=rand(@mazo.size)
        k=rand(@mazo.size)
        temp=Sorpresa.new(@mazo[i].descripcion,@mazo[i].valor,@mazo[i].tipo)
        @mazo[i]=@mazo[j]
        @mazo[j]=temp
        i+=1
      end
    end
    
    def aplicarSorpresa
      tienePropietario=false
      if @cartaActual.tipo == TipoSorpresa::PAGARCOBRAR
        @jugadorActual.modificarSaldo(@cartaActual.valor)
      elsif @cartaActual.tipo == TipoSorpresa::IRACASILLA
        esCarcel=@tablero.esCasillaCarcel(@cartaActual.valor)
        if esCarcel
          encarcelarJugador
        else
          nuevaCasilla=@tablero.obtenerCasillaNumero(@cartaActual.valor)
          tienePropietario=@jugadorActual.actualizarPosicion(nuevaCasilla)
        end
      elsif @cartaActual.tipo == TipoSorpresa::PORCASAHOTEL
        @jugadorActual.pagarCobrarPorCasaYHotel(@cartaActual.valor)
      elsif @cartaActual.tipo == TipoSorpresa::PORJUGADOR
        i=0
        while i < @jugadores.size
          if @jugadores[i] != @jugadorActual
            @jugadores[i].modificarSaldo(@cartaActual.valor)
            @jugadorActual.modificarSaldo(-@cartaActual.valor)
          end
        end
      elsif @cartaActual.tipo == TipoSorpresa::CONVERTIRSE
        fianza=@cartaActual.valor
        nuevo=@jugadorActual.convertirme(fianza)
        @jugadores[@jugadores.index(@jugadorActual)]=nuevo
        @jugadorActual=nuevo
        for p in @jugadorActual.propiedades
          p.propietario=@jugadorActual
        end
      end
      @mazo.delete(@cartaActual)
      if@cartaActual.tipo == TipoSorpresa::SALIRCARCEL
        @jugadorActual.cartaLibertad=@cartaActual
      else
        @mazo<<@cartaActual
      end
      return tienePropietario
    end

    def cancelarHipoteca(casilla)
      puedoDeshipotecar=false
      estaHipotecada=casilla.estaHipotecada
      if estaHipotecada
        puedoDeshipotecar=@jugadorActual.puedoPagarHipoteca(casilla)
        if puedoDeshipotecar
          aPagar = casilla.cancelarHipoteca
          @jugadorActual.modificarSaldo(-aPagar)
        end
      end
      return puedoDeshipotecar
    end

    def comprarTituloPropiedad
      puedoComprar=@jugadorActual.comprarTitulo
      return puedoComprar
    end

    def edificarCasa(casilla)
      puedo_edificar = false
      if casilla.soyEdificable
        if casilla.sePuedeEdificarCasa(@jugadorActual.factorEspeculador)
          puedo_edificar = @jugadorActual.puedoEdificarCasa(casilla)
          if (puedo_edificar)
            costeEdificarCasa = casilla.edificarCasa
            @jugadorActual.modificarSaldo(-costeEdificarCasa)
          end
        end
      end
      return puedo_edificar
    end
    
    def edificarHotel(casilla)
      puedo_edificar = false
      if casilla.soyEdificable
        if casilla.sePuedeEdificarHotel(@jugadorActual.factorEspeculador)
          puedo_edificar = @jugadorActual.puedoEdificarHotel(casilla)
          if puedo_edificar
            costeEdificarHotel = casilla.edificarHotel
            @jugadorActual.modificarSaldo(-costeEdificarHotel)
          end
        end
      end
      return puedo_edificar
    end

    def hipotecarPropiedad(casilla)
      puedoHipotecarPropiedad=false
      if casilla.soyEdificable
        sePuedeHipotecar= !casilla.estaHipotecada
        if sePuedeHipotecar
          puedoHipotecar= @jugadorActual.puedoHipotecar(casilla)
          if puedoHipotecar
            cantidadRecibida=casilla.hipotecar
            @jugadorActual.modificarSaldo(cantidadRecibida)
            puedoHipotecarPropiedad=puedoHipotecar
          end
        end
      end
      return puedoHipotecarPropiedad
    end

    def inicializarJuego(nombres)
      inicializarCartasSorpresa
      inicializarTablero
      inicializarJugadores(nombres)
      salidaJugadores
    end

    def intentarSalirCarcel (metodo)
      libre=false
      if metodo == MetodoSalirCarcel::TIRANDODADO
        valorDado=@dado.tirar
      elsif metodo == MetodoSalirCarcel::PAGANDOLIBERTAD
        tengoSaldo=@jugadorActual.pagarLibertad(-@@PRECIO_LIBERTAD)
        libre=tengoSaldo
        if libre
          @jugadorActual.encarcelado=false
        end
      end
      return libre
    end

    def jugar
      valorDado=@dado.tirar
      casillaPosicion=@jugadorActual.casillaActual
      nuevaCasilla=@tablero.obtenerNuevaCasilla(casillaPosicion,valorDado)
      tienePropietario=@jugadorActual.actualizarPosicion(nuevaCasilla)
      if !nuevaCasilla.soyEdificable
        if nuevaCasilla.tipo == TipoCasilla::JUEZ
          encarcelarJugador
        elsif nuevaCasilla.tipo == TipoCasilla::SORPRESA
          @cartaActual=@mazo[0]
        end
      end
      return tienePropietario
    end
    def obtenerRanking
      ranking = Hash.new
      for j in @jugadores
        ranking[j.nombre] = j.obtenerCapital
      end
      return ranking
    end

    def propiedadesHipotecadasJugador(hipotecadas)
      aux = Array.new
      for i in @jugadorActual.propiedades.size
        if (@jugadorActual.propiedades[i].hipotecada == hipotecadas)
          aux << @jugadorActual.propiedades[i]
        end
      end

      return aux
    end

    def  siguienteJugador
      nuevo =( @jugadores.index(@jugadorActual) + 1) % @jugadores.size
      @jugadorActual=@jugadores[nuevo]
    end


    def venderPropiedad(casilla)
      puedoVender = false
      if casilla.soyEdificable
        puedoVender = @jugadorActual.puedoVenderPropiedad(casilla);
        if puedoVender
          @jugadorActual.venderPropiedad(casilla)
        end
      end
      return puedoVender
    end


    private

    def encarcelarJugador
      if !@jugadorActual.tengoCartaLibertad
        casillaCarcel=@tablero.carcel
        @jugadorActual.irACarcel(casillaCarcel)
      else
        carta=@jugadorActual.devolverCartaLibertad
        @mazo<<carta
      end
    end

    def inicializarTablero
      @tablero=Tablero.new
    end

    def inicializarCartasSorpresa
      @mazo<<Sorpresa.new("Te conviertes en especulador. Tu fianza es de 5000€",5000,TipoSorpresa::CONVERTIRSE)
      @mazo<<Sorpresa.new("Se te vienen encima los examenes finales e intentas salvar el curso con una academia. Paga 100 euros para clases",-100,TipoSorpresa::PAGARCOBRAR)
      @mazo<<Sorpresa.new("Te pillan copiando una practica. Ve a la carcel",9,TipoSorpresa::IRACASILLA)
      @mazo<<Sorpresa.new("Pierdes las llaves de casa y pasas una noche en la casilla 8. Ve a la casilla 8",8, TipoSorpresa::IRACASILLA);
      @mazo<<Sorpresa.new("Suspendes una asignatura y no te guardan las prácticas. Vuelve al punto de salida",0, TipoSorpresa::IRACASILLA)
      @mazo<<Sorpresa.new("Das una macrofiesta en tus propiedades pero la cosa se desmadra. Paga 75 euros por casa y 150 por hotel.",-75,TipoSorpresa::PORCASAHOTEL)
      @mazo<<Sorpresa.new("No te cuentan las practicas en la convocatoria extraordinaria y tu nota se queda en 4.89. Lo siento, pagas segunda matricula",-160,TipoSorpresa::PORJUGADOR)
      @mazo<<Sorpresa.new("Aquel chaval que aprobo programacion gracias a ti paga tu fianza. Eres libre de nuevo",0,TipoSorpresa::SALIRCARCEL)
      @mazo<<Sorpresa.new("Sacas matrícula de honor en una asignatura y la universidad te devuelve el pago de una matrícula. Recibes 100€", 100, TipoSorpresa::PAGARCOBRAR);
      @mazo<<Sorpresa.new("Los inquilinos de tus propiedades pagan su estancia. Recibe 100€ por casa y 200 por hotel", +100, TipoSorpresa::PORCASAHOTEL)
      @mazo<<Sorpresa.new("Cada jugador te hace un regalo de 75€ por ser buena gente. Recibe 75€ por cada jugador", +75, TipoSorpresa::PORJUGADOR)
      @mazo<<Sorpresa.new("Te conviertes en especulador. Tu fianza es de 3000€",3000,TipoSorpresa::CONVERTIRSE)
      barajarCartas
    end

    def salidaJugadores

      aleatorio = rand(@jugadores.size)

      for j in @jugadores
        j.casillaActual = @tablero.obtenerCasillaNumero(0)
        j.modificarSaldo(7500)
      end

      @jugadorActual = @jugadores[aleatorio]
    end

  end
end
    