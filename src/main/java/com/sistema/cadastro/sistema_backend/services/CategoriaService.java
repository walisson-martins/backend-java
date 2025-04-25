package com.sistema.cadastro.sistema_backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.cadastro.sistema_backend.domain.Categoria;
import com.sistema.cadastro.sistema_backend.repositories.CategoriaRepository;
import com.sistema.cadastro.sistema_backend.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Optional<Categoria> buscar(Integer id) {
        Optional<Categoria> obj = repo.findById(id);
        
        return Optional.of(obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! :Id " + id + ", Tipo: " + Categoria.class.getName())));
    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return repo.save(obj);
    }
}
