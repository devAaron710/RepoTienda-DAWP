package com.tienda.dao;

import com.tienda.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaDao extends JpaRepository<Categoria, Long> {
    //TODO: crear metodos ampliados
   
    List<Categoria>findByDescripcion(String descripcion);
    
    
}
