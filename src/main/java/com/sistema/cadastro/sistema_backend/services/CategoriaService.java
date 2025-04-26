package com.sistema.cadastro.sistema_backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sistema.cadastro.sistema_backend.domain.Categoria;
import com.sistema.cadastro.sistema_backend.repositories.CategoriaRepository;
import com.sistema.cadastro.sistema_backend.services.exceptions.DataIntegrityException;
import com.sistema.cadastro.sistema_backend.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Optional<Categoria> find(Integer id) {
        Optional<Categoria> obj = repo.findById(id);

        return Optional.of(obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! :Id " + id + ", Tipo: " + Categoria.class.getName())));
    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update(Categoria obj) {
        find(obj.getId());
        return repo.save(obj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
        }
    }
}
