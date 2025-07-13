package com.tienda.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data   
@Entity
@Table(name = "producto")
public class Producto implements Serializable{
    //VERSION DE SERIALIZACION
    private static final long serialVersionUID =1L;
    
    @Id //id de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;//id_producto
    
    //private Long idCategoria;
    private String descripcion;
    private String detalle;
    private double precio;
    private int existencias;
    private String rutaImagen;
    private boolean activo;
    
    //Asociacion  delegar cargas transaccionales a la BD
    //relacion categorias asociada a un producto 
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    //constructor base
    public Producto() {
    }    
    
    //constructor sobrecargado
    public Producto(String descripcion, String detalle, double precio, int existencias, String rutaImagen, boolean activo) {
        this.descripcion = descripcion;
        this.detalle = detalle;
        this.precio = precio;
        this.existencias = existencias;
        this.rutaImagen = rutaImagen;
        this.activo = activo;
    } 
}   
