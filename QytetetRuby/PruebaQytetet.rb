#encoding: utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


require_relative 'Qytetet.rb'

module ModeloQytetet

  class PruebaQytetet
 
    #   def cartasMayoresCero
    #    mayores=Array.new
    #   var=0
    #  while var < @@mazo.size()
    #   if @@mazo[var].valor > 0
    #    mayores << @@mazo[var]
    # end
    #var+=1
    # end
    # return mayores
    # end

    # def cartasTipoIrACasilla
    #  casilla=Array.new()
    #  var=0
    # while var<@@mazo.size()
    #   if @@mazo[var].tipo == TipoSorpresa::IRACASILLA
    #      casilla<<@@mazo[var]
    #    end
    #   var=var+1
    # end
    #  return casilla
    # end
    def self.imprimeLogo
      puts '

                        /\             /\
                       |`\\_,--="=--,_//`|
                       \ ."  :". .":  ". /
                      ==)  _ :  "  : _  (==
                        |>/O\   _   /O\<|
                        | \-"~` _ `~"-/ |
                       >|`===. \_/ .===`|<
                 .-"-.   \==="  |  "===/   .-"-.
     .----------{". "`}---\,  .-"-.  ,/---{.". "}----------.
      )         `"---"`     `~-===-~`     `"---"`         (
     (            ___        _       _       _             )
      )          / _ \ _   _| |_ ___| |_ ___| |_          (
     (          | | | | | | | __/ _ \ __/ _ \ __|          )
      )         | |_| | |_| | ||  __/ ||  __/ |_          (
     (           \__\_\\__, |\__\___|\__\___|\__|          )
      )                |___/                              (
     "-----------------------------------------------------"
      
      '
    end
    
    # def cartasTipo(tipo)
    #   casilla=Array.new()
    #   var=0
    #  while var<@@mazo.size()
    #    if @@mazo[var].tipo == tipo
    #      casilla<<@@mazo[var]
    #     end
    #     var=var+1
    #   end
    #    return casilla
    # end
    
    def self.main
      
      PruebaQytetet.imprimeLogo
      sleep(2)
      #######################################################
      
      puts "/***************PROBANDO CASILLA************/"
      miCasilla = Casilla.creaCasilla(5,2,TipoCasilla::CARCEL)
      puts miCasilla
      sleep(1)
      #######################################################
      
      puts "/***************PROBANDO DADO************/"
      miDado= Dado.new
      i=0
      while i != 3
        puts "Tirando dado ... #{miDado.tirar}\n"
        i=i+1
        sleep(0.5)
      end
      sleep(1)
      #######################################################
      
      puts "/***************PROBANDO JUGADOR************/"
      miJugador= Jugador.new("Manolo")
      puts miJugador.to_s
      sleep(1)
      #######################################################
      
      puts "/***************PROBANDO SORPRESA************/"
      miSorpresa= Sorpresa.new("Prueba",50,TipoSorpresa::PAGARCOBRAR)
      puts miSorpresa.to_s
      sleep(1)
      #######################################################
      
      puts "/***************PROBANDO TABLERO************/"
      miTablero = Tablero.new
      puts miTablero.to_s
      sleep(1)
      #######################################################
      
      puts "/***************PROBANDO TITULOPROPIEDAD************/"
      miTitulo=TituloPropiedad.new("Calle de prueba", 0, -0.155, 150, 650, miCasilla)
      puts miTitulo.to_s
      sleep(1)
      
      #######################################################
      
      puts "/***************PROBANDO QYTETET************/"
     
      partida = Qytetet.instance
      nombres=Array.new
      nombres<<"Carlos"
      nombres<<"David"
      nombres<<"Jose"
      nombres<<"Jesus"
      partida.inicializarJuego(nombres)
      
      var=0
      while var != 4
        puts "#{partida.jugadorActual.to_s}\n"
        partida.siguienteJugador
        var=var+1
      end
      sleep(1)
    end
    PruebaQytetet.main
  end
end
