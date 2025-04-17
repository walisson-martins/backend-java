package com.sistema.cadastro.sistema_backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.cadastro.sistema_backend.domain.Cliente;
import com.sistema.cadastro.sistema_backend.repositories.ClienteRepository;
import com.sistema.cadastro.sistema_backend.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public Optional<Cliente> buscar(Integer id) {
        Optional<Cliente> obj = repo.findById(id);
        
        return Optional.of(obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! :Id " + id + ", Tipo: " + Cliente.class.getName())));
    }
}
