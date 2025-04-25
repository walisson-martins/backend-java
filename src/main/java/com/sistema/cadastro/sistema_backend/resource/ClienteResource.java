package com.sistema.cadastro.sistema_backend.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.cadastro.sistema_backend.domain.Cliente;
import com.sistema.cadastro.sistema_backend.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<Cliente>> find(@PathVariable Integer id) {

        Optional<Cliente> obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }

}
