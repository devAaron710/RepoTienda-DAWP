
package com.tienda.service;

import com.tienda.domain.Categoria;
import java.util.List;

//define los metodos a exponer a consumidores
public interface CategoriaService {
    
    //metodo publico tipo list de Categorias
    public List<Categoria> geCategorias(boolean activos);
    
}
