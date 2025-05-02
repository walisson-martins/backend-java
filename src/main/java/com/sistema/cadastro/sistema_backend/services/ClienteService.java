package com.sistema.cadastro.sistema_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.sistema.cadastro.sistema_backend.domain.Cidade;
import com.sistema.cadastro.sistema_backend.domain.Cliente;
import com.sistema.cadastro.sistema_backend.domain.Endereco;
import com.sistema.cadastro.sistema_backend.dto.ClienteDTO;
import com.sistema.cadastro.sistema_backend.dto.ClienteNewDTO;
import com.sistema.cadastro.sistema_backend.enums.TipoCliente;
import com.sistema.cadastro.sistema_backend.repositories.CidadeRepository;
import com.sistema.cadastro.sistema_backend.repositories.ClienteRepository;
import com.sistema.cadastro.sistema_backend.repositories.EnderecoRepository;
import com.sistema.cadastro.sistema_backend.services.exceptions.DataIntegrityException;
import com.sistema.cadastro.sistema_backend.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

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
            throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
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

    public Cliente fromDto(ClienteNewDTO objDto) {
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
                TipoCliente.toEnum(objDto.getTipo()));
        Cidade cid = cidadeRepository.findById(objDto.getCidadeId())
                .orElseThrow(() -> new ObjectNotFoundException("Cidade não encontrada! Id: " + objDto.getCidadeId()));
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(),
                objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());

        if (objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }

        if (objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }

        return cli;
    }

    private void updateData(Optional<Cliente> newObj, Optional<Cliente> obj) {
        Cliente newCliente = newObj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
        Cliente cliente = obj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }

    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }
}
