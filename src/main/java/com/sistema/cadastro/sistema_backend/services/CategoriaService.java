package com.sistema.cadastro.sistema_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.sistema.cadastro.sistema_backend.domain.Categoria;
import com.sistema.cadastro.sistema_backend.dto.CategoriaDTO;
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
        Optional<Categoria> newObj = find(obj.getId());
        updateData(newObj, Optional.of(obj));
        return repo.save(newObj.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrado")));
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
        }
    }

    public List<Categoria> findAll() {
        return repo.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Categoria fromDto(CategoriaDTO objDto) {
        return new Categoria(objDto.getId(), objDto.getNome());
    }

    private void updateData(Optional<Categoria> newObj, Optional<Categoria> obj) {
        Categoria newCategoria = newObj.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrado"));
        Categoria categoria = obj.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrado"));
        newCategoria.setNome(categoria.getNome());
    }
}
