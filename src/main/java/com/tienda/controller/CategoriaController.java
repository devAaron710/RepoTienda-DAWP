
package com.tienda.controller;

import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
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
@RequestMapping("/categoria")
public class CategoriaController {

    @Value("${urlApi}")
    private String urlApi;

    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;

    @GetMapping("/listado")
    public String listado(Model model) {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<List<Categoria>> response = new RestTemplate().exchange(
                URI.create(String.format("%s/categoria", urlApi)),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Categoria>>() {
        }
        );

        List<Categoria> categorias = response.getBody();
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());

        return "/categoria/listado";
    }

    @GetMapping("/nuevo")
    public String categoriaNuevo(Categoria categoria) {
        return "/categoria/modifica";
    }

    @PostMapping("/guardar")
    public String categoriaGuardar(Categoria categoria,
            @RequestParam("imagenFile") MultipartFile imagenFile) {

        if (!imagenFile.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Categoria> request = new HttpEntity<>(categoria, headers);
            ResponseEntity<Categoria> response = new RestTemplate().postForEntity(
                    URI.create(String.format("%s/categoria", urlApi)),
                    request,
                    Categoria.class
            );

            categoria.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile,
                            "categoria",
                            categoria.getIdCategoria()));
        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Categoria> request = new HttpEntity<>(categoria, headers);
        request = new HttpEntity<>(categoria, headers);
        ResponseEntity<Categoria> response = new RestTemplate().postForEntity(
                URI.create(String.format("%s/categoria", urlApi)),
                request,
                Categoria.class
        );

        return "redirect:/categoria/listado";
    }

    @GetMapping("/eliminar/{idCategoria}")
    public String categoriaEliminar(Categoria categoria) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Boolean> response = new RestTemplate().exchange(
                URI.create(String.format("%s/categoria/%s", urlApi, categoria.getIdCategoria())),
                HttpMethod.DELETE,
                request,
                new ParameterizedTypeReference<Boolean>() {
        }
        );
        return "redirect:/categoria/listado";
    }

    @GetMapping("/modificar/{idCategoria}")
    public String categoriaModificar(Categoria categoria, Model model) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Categoria> response = new RestTemplate().exchange(
                URI.create(String.format("%s/categoria/%s", urlApi, categoria.getIdCategoria())),
                HttpMethod.GET,
                request,
                Categoria.class
        );
        categoria = response.getBody();
        model.addAttribute("categoria", categoria);
        return "/categoria/modifica";
    }

}