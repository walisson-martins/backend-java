package com.sistema.cadastro.sistema_backend.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.cadastro.sistema_backend.domain.Pedido;
import com.sistema.cadastro.sistema_backend.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<Pedido>> find(@PathVariable Integer id) {

        Optional<Pedido> obj = service.buscar(id);

        return ResponseEntity.ok().body(obj);
    }

}
