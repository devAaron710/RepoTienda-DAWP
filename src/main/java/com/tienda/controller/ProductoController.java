
package com.tienda.controller;

import com.tienda.domain.Categoria;
import com.tienda.domain.Producto;
import com.tienda.service.CategoriaService;
import com.tienda.service.ProductoService;
import com.tienda.service.impl.FirebaseStorageServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/producto")
public class ProductoController {
    
   @Autowired
    ProductoService productoService;
   
   //se utiliza para llamar o utilizar las distinstas caregorias y agrgarlas a una lista
   @Autowired
    CategoriaService categoriaService;
   
   @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        //listado de productos 
        List<Producto> productos = productoService.getProductos(false);
        model.addAttribute("productos",productos);
        model.addAttribute("totalProductos",productos.size());
        
        //listado de categoria 
        List<Categoria> categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias",categorias);
        
        return "/producto/listado";
    }
    
    @GetMapping("/nuevo")
    public String productoNuevo(Producto producto) {
        return "/producto/modifica";
    }
    
    @PostMapping("/guardar")
    public String productoGuardar(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile) {  
        //verificamos que la imagen sea distinto de vacio
        if (!imagenFile.isEmpty()) {
            //guardar registro de la producto
            productoService.save(producto);
            producto.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile, 
                            "producto", 
                            producto.getIdProducto()));
        }
        productoService.save(producto);
        //redirect vaya a una accion con este nombre que me sirve para hacer algo previo
        return "redirect:/producto/listado";
    }

    //rediccionamiento a un url
    @GetMapping("/eliminar/{idProducto}")
    public String productoEliminar(Producto producto) {
        productoService.delete(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String productoModificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);
        //listado de categoria 
        List<Categoria> categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias",categorias);
        
        return "/producto/modifica";
    }
}
