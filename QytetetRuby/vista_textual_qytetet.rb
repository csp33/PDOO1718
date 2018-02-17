#encoding: utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'MetodoSalirCarcel'
module InterfazTextualQytetet
  class VistaTextualQytetet
    def initialize
      
    end  
    def seleccionMenu(menu)
      if menu.empty?
        return -1
      else
        begin #Hasta que se hace una seleccionn valida
          valido= true
          for m in menu #se muestran las opciones del menuº
            mostrar( "#{m[0]}" + " : " + "#{m[1]}")
          end
          mostrar( "\n Elige un numero de opcion: ")
          captura = gets.chomp
          valido=comprobarOpcion(captura, 0, menu.length-1); #metodo para comprobar la eleccion correcta
          if (! valido) then
            mostrar( "\n\n ERROR !!! \n\n Seleccion erronea. Intentalo de nuevo.\n\n")
          end
        end while (! valido)
        Integer(captura)  
      end
    end
  
  
    def comprobarOpcion(captura,min,max)
      # metodo para comprobar si la opcion introducida es correcta, usado por seleccion_menu
      valido=true
      begin
        opcion = Integer(captura)
        if (opcion<min || opcion>max) #No es un entero entre los validos
          valido = false
          mostrar("el numero debe estar entre #{min} y #{max}")
        end
      rescue Exception => e  #No se ha introducido un entero
        valido = false
        mostrar('debes introducir un numero')
      end 
      valido
    end
  
    def menuGestionInmobiliaria
      mostrar( 'Elige la gestion inmobiliaria que deseas hacer')
      menuGI=[[0, 'Siguiente Jugador'], [1, 'Edificar casa'], [2, 'Edificar Hotel'], [3, 'Vender propiedad'],[4, 'Hipotecar Propiedad'], [5, 'Cancelar Hipoteca']]
      salida=seleccionMenu(menuGI)
      return salida
    end
  
    def menuSalirCarcel
      mostrar( 'Elige el metodo para salir de la carcel')
      menuSC=[[0, 'Tirando el dado'], [1, 'Pagando mi libertad']]
      salida=seleccionMenu(menuSC)
      if salida == 0
        metodo=MetodoSalirCarcel::TIRANDODADO
      else
        metodo=MetodoSalirCarcel::PAGANDOLIBERTAD
      end
      return metodo
    end
  
    def elegirQuieroComprar
      menuComprar=[[0, 'Comprar la propiedad'], [1, 'Pasar el turno']]
      salida=seleccionMenu(menuComprar)
      return salida
    end
        
    def menuElegirPropiedad(listaPropiedades) # numero y nombre de propiedades            
      menuEP = Array.new
      numero_opcion=0;
      for prop in listaPropiedades
        menuEP<<[numero_opcion, prop]; # opcion de menu, numero y nombre de propiedad
        numero_opcion=numero_opcion+1
      end
      puts menuEP.inspect
      salida=seleccionMenu(menuEP); # Método para controlar la elección correcta en el menú 
      salida;
    end  
  
    def obtenerNombreJugadores
      nombres=Array.new
      valido = true; 
      begin
        self.mostrar("Escribe el numero de jugadores: (de 2 a 4):");
        lectura = gets.chomp #lectura de teclado
        valido=comprobarOpcion(lectura, 2, 4); #método para comprobar la elección correcta
      end while(!valido)
    
      for i in 1..Integer(lectura)  #pide nombre de jugadores y los mete en un array
        mostrar('Jugador:  '+ i.to_s)
        nombre=gets.chomp
        nombres<<nombre
      end
      nombres
    end
  
    def mostrar(texto)  #metodo para mostrar el string que recibe como argumento
      puts texto
    end
  
    def crearArrayPropiedades (props)
      nombres=Array.new
      for p in props
        nombres.add(p.nombre)
      end
      return nombres
    end
    
    def obtenerPosicionJugadores(jugadores)
      for j in jugadores
        mostrar("El jugador #{j.nombre} se encuentra en la casilla #{j.casillaActual.numeroCasilla}\n")
      end
    end
    
    def crearArrayHipotecadas(propiedades)
      nombres = Array.new
      for p in propiedades
        if p.hipotecada
          nombres<<p.nombre
        end
      end
      return nombres
    end
        
    def crearArraySinHipoteca(propiedades)
      nombres = Array.new
      for p in propiedades
        if not p.hipotecada
          nombres<<p.nombre
        end
      end
      return nombres
    end
    
    def crearArrayPuedoConstruirCasa(propiedades,factorEspeculador)
      nombres = Array.new
      for p in propiedades
        if p.casilla.sePuedeEdificarCasa(factorEspeculador)
          nombres<<p.nombre
        end
      end
      return nombres
    end
    
    def crearArrayPuedoConstruirHotel(propiedades,factorEspeculador)
      nombres = Array.new
      for p in propiedades
        if p.casilla.sePuedeEdificarHotel(factorEspeculador)
          nombres<<p.nombre
        end
      end
      return nombres
    end
    
    def mostrarSaldo(jugador)
      mostrar("El saldo del jugador #{jugador.nombre} es de #{jugador.saldo} €.")
    end
    private :comprobarOpcion, :seleccionMenu
  
 
  end
end




