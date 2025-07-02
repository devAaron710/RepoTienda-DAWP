
package com.tienda.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable{
    //VERSION DE SERIALIZACION
    private static final long serialVersionUID =1L;
    
    @Id //id de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;
    private String descripcion;
    private String rutaImagen;
    private boolean activo;
    
    //relacion
    @OneToMany
    @JoinColumn (name = "id_categoria", insertable = false, updatable = false)
    private List<Producto>productos;

    //constructor base
    public Categoria() {
    }

    //constructor sobrecargado
    public Categoria(String descripcion, String rutaImagen, boolean activo) {
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.activo = activo;
    }
}
