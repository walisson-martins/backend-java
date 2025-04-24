package com.sistema.cadastro.sistema_backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.cadastro.sistema_backend.domain.Pedido;
import com.sistema.cadastro.sistema_backend.repositories.PedidoRepository;
import com.sistema.cadastro.sistema_backend.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public Optional<Pedido> buscar(Integer id) {
        
        System.out.println("Buscando pedido com ID: " + id);

        Optional<Pedido> obj = repo.findById(id);
        
        return Optional.of(obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! :Id " + id + ", Tipo: " + Pedido.class.getName())));
    }
}
