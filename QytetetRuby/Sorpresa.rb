#encoding: utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'TipoSorpresa'
module ModeloQytetet

  class Sorpresa

    def initialize(description,value,type)
      @descripcion=description
      @valor=value
      @tipo=type
    end
    
    
    attr_reader :descripcion ,:valor ,:tipo

    
    def to_s()
      "Texto: #{@descripcion} \n Valor: #{@valor} \n Tipo: #{@tipo}"
    end
  end
end
