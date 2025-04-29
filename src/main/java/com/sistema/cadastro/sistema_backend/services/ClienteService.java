package com.sistema.cadastro.sistema_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.sistema.cadastro.sistema_backend.domain.Cliente;
import com.sistema.cadastro.sistema_backend.dto.ClienteDTO;
import com.sistema.cadastro.sistema_backend.repositories.ClienteRepository;
import com.sistema.cadastro.sistema_backend.services.exceptions.DataIntegrityException;
import com.sistema.cadastro.sistema_backend.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public Optional<Cliente> find(Integer id) {
        Optional<Cliente> obj = repo.findById(id);

        return Optional.of(obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! :Id " + id + ", Tipo: " + Cliente.class.getName())));
    }

    public Cliente update(Optional<Cliente> obj) {
        Cliente cliente = obj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
        Optional<Cliente> newObj = find(cliente.getId());
        Cliente updatedCliente = newObj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
        updateData(Optional.of(updatedCliente), obj);
        return repo.save(updatedCliente);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException("Não é possível excluir porque há entidade relacionadas");
        }
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDto(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }

    private void updateData(Optional<Cliente> newObj, Optional<Cliente> obj) {
        Cliente newCliente = newObj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
        Cliente cliente = obj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }
}
