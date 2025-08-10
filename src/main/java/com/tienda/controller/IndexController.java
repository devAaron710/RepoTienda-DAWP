package com.tienda.controller;

import com.tienda.domain.Item;
import com.tienda.domain.Producto;
import com.tienda.service.ItemService;
import com.tienda.service.ProductoService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Value("${urlApi}")
    private String urlApi;

    @RequestMapping("/")
    public String page(Model model) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<List<Producto>> response = new RestTemplate().exchange(
                URI.create(String.format("%s/producto?activos=true", urlApi)),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Producto>>() {
        }
        );
        
        var listaProductos = response.getBody();
        model.addAttribute("productos", listaProductos);
        return "index";
    }

    @Autowired
    private ItemService itemService;

    @RequestMapping("/refrescarBoton")
    public ModelAndView refrescarBoton(Model model) {
        var lista = itemService.gets();
        var totalCarritos = 0;
        var carritoTotalVenta = 0;
        for (Item i : lista) {
            totalCarritos += i.getCantidad();
            carritoTotalVenta += (i.getCantidad() * i.getPrecio());
        }
        model.addAttribute("listaItems", lista);
        model.addAttribute("listaTotal", totalCarritos);
        model.addAttribute("carritoTotal", carritoTotalVenta);
        return new ModelAndView("/carrito/fragmentos :: verCarrito");
    }

//    @RequestMapping("/informacion")
//    public String info() {
//        return "info";
//    }
}
