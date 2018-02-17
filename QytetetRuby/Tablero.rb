#encoding: utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'Casilla'
require_relative 'Calle'
module ModeloQytetet
  class Tablero

    def initialize
      inicializar
    end
    attr_reader :casillas, :carcel

    def inicializar
      @casillas=Array.new
      titulos=Array.new
      i=0
      # Casilla.creaCasilla -> Casilla.new
      #Casilla.creaCalle -> Calle.new
      #TipoCasilla::CALLE delete
      @casillas<<Casilla.new(0,0,TipoCasilla::SALIDA)
      titulos<<nil
      i=i+1

      @casillas<<Calle.new(1,350)
      titulos<<TituloPropiedad.new("Calle de la esperanza",55,0.1,150,250,@casillas[i])
      @casillas[i].titulo=titulos[i]
      i=i+1

      @casillas<<Calle.new(2,420)
      titulos<<TituloPropiedad.new("Avenida del estudia-dia-antes", 56,0.105, 175, 275,@casillas[i])
      @casillas[i].titulo=titulos[i]
      i=i+1
      
      @casillas<<Casilla.new(3,0,TipoCasilla::SORPRESA)
      titulos<<nil
      i=i+1

      @casillas<<Calle.new(4,510)
      titulos<<TituloPropiedad.new("Torre de apuntes",57,0.11,200,300,@casillas[i])
      @casillas[i].titulo=titulos[i]
      i=i+1

      @casillas<<Casilla.new(5,0,TipoCasilla::JUEZ)
      titulos<<nil
      i=i+1

      @casillas<<Calle.new(6,690)
      titulos<<TituloPropiedad.new("Callejón del café",75,0.15,300,320,@casillas[i])
      @casillas[i].titulo=titulos[i]
      i=i+1

      @casillas<<Calle.new(7,1000)
      titulos<<TituloPropiedad.new("Desembarco del estrés",76,0.1525,325,370,@casillas[i])
      @casillas[i].titulo=titulos[i]
      i=i+1

      @casillas<<Casilla.new(8,450,TipoCasilla::IMPUESTO)
      titulos<<nil
      i=i+1

      @casillas<<Calle.new(9,1350)
      titulos<<TituloPropiedad.new("Cuesta del examen sorpresa",77,0.155,350,420,@casillas[i])
      @casillas[i].titulo=titulos[i]
      i=i+1

      @casillas<<Casilla.new(10,0,TipoCasilla::CARCEL)
      titulos<<nil
      i=i+1

      @casillas<<Calle.new(11,2100)
      titulos<<TituloPropiedad.new("Vía de la noche en vela", 85,-0.1,600,500,@casillas[i])
      @casillas[i].titulo=titulos[i]
      i=i+1

      @casillas<<Calle.new(12,2450)
      titulos<<TituloPropiedad.new("Avenida de la siesta",86,-0.105,650,525,@casillas[i])
      @casillas[i].titulo=titulos[i]
      i=i+1

      @casillas<<Casilla.new(13,0,TipoCasilla::SORPRESA)
      titulos<<nil
      i=i+1

      @casillas<<Calle.new(14,2800)
      titulos<<TituloPropiedad.new("Explanada del viernes noche", 87,-0.11, 700, 550,@casillas[i])
      @casillas[i].titulo=titulos[i]
      i=i+1

      @casillas<<Casilla.new(15,0,TipoCasilla::PARKING)
      titulos<<nil
      i=i+1

      @casillas<<Calle.new(16,3500)
      titulos<<TituloPropiedad.new("Avenida del aprobado general", 95,-0.15, 850, 600,@casillas[i])
      @casillas[i].titulo=titulos[i]
      i=i+1

      @casillas<<Calle.new(17,4800)
      titulos<<TituloPropiedad.new("Bulevar de las esperanzas rotas",96,-0.1525,900,625,@casillas[i])
      @casillas[i].titulo=titulos[i]
      i=i+1

      @casillas<<Casilla.new(18,0,TipoCasilla::SORPRESA)
      titulos<<nil
      i=i+1

      @casillas<<Calle.new(19,7000)
      titulos<<TituloPropiedad.new("Calle de los tempranos desertores", 97,-0.155, 950, 650,@casillas[i])
      @casillas[i].titulo=titulos[i]

      @carcel=@casillas[10]
    end

    def esCasillaCarcel(numeroCasilla)
      esCarcel=false
      if numeroCasilla == @carcel.numeroCasilla
        esCarcel = true
      end
      return esCarcel
    end

    def obtenerCasillaNumero(numeroCasilla)
      return @casillas[numeroCasilla]
    end

    def obtenerNuevaCasilla(casilla, desplazamiento)
      num_casilla = (casilla.numeroCasilla + desplazamiento) % 20;
      return @casillas[num_casilla]
    end

    public

    def to_s
      miCadena = ""
      var=0
      while var != @casillas.size
        miCadena = miCadena + "\t\tCasilla numero #{var}\n #{@casillas[var].to_s}\n\n"
        var=var+1
      end
      return miCadena
    end
    
    def getTamanio
      return @casillas.size
    end

  end

end
