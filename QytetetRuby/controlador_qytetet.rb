#encoding: utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'vista_textual_qytetet'
require_relative 'Qytetet'
module InterfazTextualQytetet
  class ControladorQytetet
    
    def esperar(n)
      sleep(n)
    end  
    def initialize
      @juego
      @jugador
      @casilla
      @vista=VistaTextualQytetet.new 
    end
    
    def inicializacionJuego
      jugadores = @vista.obtenerNombreJugadores
      @juego =ModeloQytetet::Qytetet.instance
      @juego.inicializarJuego(jugadores)
      @jugador = @juego.jugadorActual
      @casilla = @juego.jugadorActual.casillaActual
      @vista.mostrar("Mostrando tablero de juego:\n")
      var=0
      while var != @juego.tablero.getTamanio
        @vista.mostrar(@juego.tablero.obtenerCasillaNumero(var).to_s)
        var+=1
        #esperar(1)
      end
      esperar(1)
      @vista.mostrar("Mostrando cartas sorpresa:\n")
      esperar(0.5)
      for s in @juego.mazo
        @vista.mostrar(s.to_s)
        esperar(0.5)
      end
      esperar(1)
      @vista.mostrar("El jugador que comienza es: #{@jugador.nombre}\n")
      esperar(1)
    end
      
    def desarrolloJuego
      bancarrota = false
      while not bancarrota
        #Parte 1. Movimiento
        @vista.mostrar("Comienza el turno del jugador #{@jugador.nombre}\n")
        @vista.mostrarSaldo(@jugador)
        esperar(1.5)
        if @jugador.encarcelado
          @vista.mostrar("El jugador se encuentra en la cárcel.")
          esperar(0.5)
          metodo=@vista.menuSalirCarcel
          libre=@juego.intentarSalirCarcel(metodo)
          if libre
            if metodo == MetodoSalirCarcel::TIRANDODADO
              @vista.mostrar("El jugador tira el dado y sale un 6.")
            else
              @vista.mostrar("El jugador paga su libertad (200 €).")
            end
            esperar(1)
            @vista.mostrar("El jugador consiguió salir de la cárcel")
          elsif metodo == MetodoSalirCarcel::TIRANDODADO
            @vista.mostrar("Mala suerte! No salió un 6.")
          else
            @vista.mostrar("No tienes saldo para pagar tu libertad.")
          end
          esperar(1.5)
        end
        if !@jugador.encarcelado #No estaba en la cárcel / pudo salir
          caeEnJuez=false
          @casilla=@juego.jugadorActual.casillaActual
          @vista.mostrar("El jugador #{@jugador.nombre} se encuentra en la casilla número #{@casilla.numeroCasilla}")
          esperar(1)
          valorDado=@casilla.numeroCasilla
          @vista.mostrar("El jugador #{@jugador.nombre} tira el dado.")
          tienePropietario = @juego.jugar
          @casilla=@juego.jugadorActual.casillaActual
          pasaPorSalida = false
          if valorDado > @casilla.numeroCasilla
            pasaPorSalida = true
          end
          if @jugador.encarcelado && @casilla.tipo == TipoCasilla::CARCEL  #Si el juez movió al jugador
            caeEnJuez = true
            if valorDado == 19
              valorDado = 6
            elsif valorDado == 0
              valorDado = 5
            elsif valorDado == 1
              valorDado = 4
            elsif valorDado == 2
              valorDado = 3
            elsif valorDado == 3
              valorDado = 2
            elsif valorDado == 4
              valorDado = 1
            end
          else
            valorDado-=@casilla.numeroCasilla
            valorDado= -valorDado
            if valorDado < 0
              valorDado += 20
            end
          end
          esperar(0.7)
          @vista.mostrar("Sale un #{valorDado}")
          esperar(1)
          if caeEnJuez
            @vista.mostrar("El juez manda al jugador a la cárcel")
            if @jugador.tengoCartaLibertad
              @vista.mostrar("Pero el jugador tenía la carta de libertad, así que la usa y se libra del calabozo.")
            end
          elsif @juego.jugadorActual.casillaActual.tipo == TipoCasilla::JUEZ  #Ha podido pagar la fianza
            @vista.mostrar("El juez intenta mandar al jugador a la cárcel, pero éste paga la fianza (#{@juego.jugadorActual.fianza} €.")
          end
          if pasaPorSalida
            @vista.mostrar("El jugador pasa por la salida, por lo que cobra 1000€.")
          end
          @vista.mostrar("El jugador #{@jugador.nombre} llega a la casilla #{@casilla.numeroCasilla}")
          esperar(2)
          @vista.mostrar(@casilla.to_s)
          if @casilla.tipo == TipoCasilla::CALLE
            if tienePropietario
              @vista.mostrar("El propietario de la casilla es #{@casilla.titulo.propietario.nombre}. Por tanto, has de pagarle el alquiler.")
              esperar(1)
              @casilla.cobrarAlquiler
            else
              @vista.mostrar("La casilla no tiene propietario.\n")
              esperar(1)
              quiero=@vista.elegirQuieroComprar
              if quiero == 0
                puedo=@juego.comprarTituloPropiedad
                if puedo
                  @vista.mostrar("Enhorabuena! Propiedad comprada!")
                else
                  @vista.mostrar("El jugador no tiene suficiente saldo.")
                end
                esperar(1)
              end
            end
          elsif @casilla.tipo == TipoCasilla::IMPUESTO
            @vista.mostrar("El jugador paga un impuesto de #{@casilla.coste} €.")
          elsif @casilla.tipo == TipoCasilla::PARKING
            @vista.mostrar("El jugador llega al parking.")
          elsif @casilla.tipo == TipoCasilla::SORPRESA
            @vista.mostrar("El jugador cae en una casilla sorpresa y saca una carta del mazo.")
            esperar(0.7)
            @vista.mostrar("La carta es:\n")
            @vista.mostrar(@juego.cartaActual.to_s)
            tienePropietario = @juego.aplicarSorpresa
            if @juego.cartaActual.tipo == TipoSorpresa::IRACASILLA
              @casilla=@juego.jugadorActual.casillaActual
              if @juego.tablero.obtenerCasillaNumero(@juego.cartaActual.valor) == @juego.tablero.carcel
                @vista.mostrar("La carta manda al jugador a la cárcel")
                esperar(1)
              else
                @vista.mostrar("La carta mueve al jugador a la casilla #{@juego.cartaActual.valor}.")
                if @casilla.tipo == TipoCasilla::CALLE
                  if tienePropietario
                    @vista.mostrar("El propietario de la casilla es #{@casilla.titulo.propietario.nombre}. Por tanto, has de pagarle el alquiler.")
                    esperar(1)
                    @casilla.cobrarAlquiler
                  else
                    @vista.mostrar("La casilla no tiene propietario.\n")
                    esperar(1)
                    quiero=@vista.elegirQuieroComprar
                    if quiero
                      puedo=@juego.comprarTituloPropiedad
                      if puedo
                        @vista.mostrar("Enhorabuena! Propiedad comprada!")
                      else
                        @vista.mostrar("El jugador no tiene suficiente saldo.")
                      end
                      esperar(1)
                    end
                  end 
                end
              end
            elsif @juego.cartaActual.tipo == TipoSorpresa::CONVERTIRSE
              @vista.mostrar("El jugador se convierte en especulador con una fianza de #{@juego.jugadorActual.fianza} €.")
            end
          end
        end
        esperar(1)
        @vista.mostrarSaldo(@jugador)
        esperar(1)
        @jugador=@juego.jugadorActual
        #Parte 2. Gestión inmobiliaria
        bancarrota=@jugador.saldo <= 0
        puedoGestionar= !bancarrota && !@jugador.encarcelado && @jugador.tengoPropiedades
        if puedoGestionar
          opcion=@vista.menuGestionInmobiliaria
          salir = opcion == 0
          while @jugador.tengoPropiedades && !salir
            if opcion != 0
              if opcion == 1
                nombres=@vista.crearArrayPuedoConstruirCasa(@jugador.propiedades,@jugador.factorEspeculador)
                elegida=@vista.menuElegirPropiedad(nombres)
                if elegida != -1
                  seleccionada=@jugador.propiedades[elegida].casilla
                  puedo=@juego.edificarCasa(seleccionada)
                  if puedo
                    @vista.mostrar("Casa construida.")
                  else
                    @vista.mostrar("Saldo insuficiente o no caben más casas.")
                  end
                else
                  @vista.mostrar("No hay propiedades disponibles para edificar casas.")
                end
              elsif opcion == 2 
                nombres=@vista.crearArrayPuedoConstruirHotel(@jugador.propiedades,@jugador.factorEspeculador)
                elegida=@vista.menuElegirPropiedad(nombres)
                if elegida != -1
                  seleccionada=@jugador.propiedades[elegida].casilla
                  puedo=@juego.edificarHotel(seleccionada)
                  if puedo
                    @vista.mostrar("Hotel construido.")
                  else
                    @vista.mostrar("Saldo o número de casas insuficiente.")
                  end
                else
                  @vista.mostrar("No hay propiedades disponibles para edificar hoteles.")
                end
              elsif opcion == 3
                nombres=@vista.crearArraySinHipoteca(@jugador.propiedades)
                elegida=@vista.menuElegirPropiedad(nombres)
                if elegida != -1
                  seleccionada = @jugador.propiedades[elegida].casilla
                  puedo=@juego.venderPropiedad(seleccionada)
                  if puedo
                    @vista.mostrar("Propiedad vendida.")
                  else
                    @vista.mostrar("La propiedad está hipotecada.")
                  end
                else
                  @vista.mostrar("No hay propiedades disponibles para la venta.")
                end
              elsif opcion == 4
                nombres=@vista.crearArraySinHipoteca(@jugador.propiedades)
                elegida=@vista.menuElegirPropiedad(nombres)
                if elegida != -1
                  seleccionada = @jugador.propiedades[elegida].casilla
                  puedo = @juego.hipotecarPropiedad(seleccionada)
                  if puedo
                    @vista.mostrar("Casilla hipotecada.")
                  else
                    @vista.mostrar("No se puede hipotecar.")
                  end
                else
                  @vista.mostrar("No hay propiedades disponibles para hipotecar.")
                end
              else #Deshipotecar
                nombres=@vista.crearArrayHipotecadas(@jugador.propiedades)
                elegida=@vista.menuElegirPropiedad(nombres)
                if elegida != -1
                  seleccionada=@jugador.propiedades[elegida].casilla
                  puedo = @juego.cancelarHipoteca(seleccionada)
                  if puedo
                    @vista.mostrar("Hipoteca cancelada.")
                  else
                    @vista.mostrar("No se puede deshipotecar.")
                  end
                else
                  @vista.mostrar("No hay propiedades disponibles para deshipotecar.")
                end
              end
              @vista.mostrarSaldo(@jugador)
            end
            if @jugador.tengoPropiedades
              opcion=@vista.menuGestionInmobiliaria
              salir = opcion == 0
            else
              salir = true
            end
          end
        end
        #Fin del turno
        if not bancarrota
          @vista.mostrar("Pasamos al siguiente jugador.")
          @juego.siguienteJugador
          @jugador=@juego.jugadorActual
          esperar(2)
        end
      end
      @vista.mostrar("El jugador #{@jugador.nombre} se quedó en bancarrota.")
      esperar(1)
      @vista.mostrar("***********FIN DEL JUEGO************")
      esperar(2)
      ranking=@juego.obtenerRanking
      @vista.mostrar(ranking.to_s)
    end
    
    def self.main
      controlador=ControladorQytetet.new
      controlador.inicializacionJuego
      controlador.desarrolloJuego
    end  
  end
  ControladorQytetet.main
end
