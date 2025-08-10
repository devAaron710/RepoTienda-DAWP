
package com.tienda.controller;

import com.tienda.domain.Categoria;
import com.tienda.domain.Producto;
import com.tienda.service.ProductoService;
import com.tienda.service.impl.FirebaseStorageServiceImpl;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Value("${urlApi}")
    private String urlApi;

    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;

    @GetMapping("/listado")
    public String listado(Model model) {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<List<Producto>> response = new RestTemplate().exchange(
                URI.create(String.format("%s/producto", urlApi)),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Producto>>() {
        }
        );

        List<Producto> productos = response.getBody();
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        

        ResponseEntity<List<Categoria>> responseCat = new RestTemplate().exchange(
                URI.create(String.format("%s/categoria?activos=false", urlApi)),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Categoria>>() {
        }
        );

        List<Categoria> categorias = responseCat.getBody();
        model.addAttribute("categorias", categorias);

        return "/producto/listado";
    }

    @GetMapping("/nuevo")
    public String productoNuevo(Producto producto) {
        return "/producto/modifica";
    }

    @PostMapping("/guardar")
    public String productoGuardar(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile) {

        if (!imagenFile.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Producto> request = new HttpEntity<>(producto, headers);
            ResponseEntity<Producto> response = new RestTemplate().postForEntity(
                    URI.create(String.format("%s/producto", urlApi)),
                    request,
                    Producto.class
            );

            producto.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile,
                            "producto",
                            producto.getIdProducto()));
        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Producto> request = new HttpEntity<>(producto, headers);
        request = new HttpEntity<>(producto, headers);
        ResponseEntity<Producto> response = new RestTemplate().postForEntity(
                URI.create(String.format("%s/producto", urlApi)),
                request,
                Producto.class
        );

        return "redirect:/producto/listado";
    }

    @GetMapping("/eliminar/{idProducto}")
    public String productoEliminar(Producto producto) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Boolean> response = new RestTemplate().exchange(
                URI.create(String.format("%s/producto/%s", urlApi, producto.getIdProducto())),
                HttpMethod.DELETE,
                request,
                new ParameterizedTypeReference<Boolean>() {
        }
        );
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String productoModificar(Producto producto, Model model) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Producto> response = new RestTemplate().exchange(
                URI.create(String.format("%s/producto/%s", urlApi, producto.getIdProducto())),
                HttpMethod.GET,
                request,
                Producto.class
        );
        producto = response.getBody();
        model.addAttribute("producto", producto);
        
        ResponseEntity<List<Categoria>> responseCat = new RestTemplate().exchange(
                URI.create(String.format("%s/categoria?activos=false", urlApi)),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Categoria>>() {
        }
        );

        List<Categoria> categorias = responseCat.getBody();
        model.addAttribute("categorias", categorias);
        
        return "/producto/modifica";
    }

}