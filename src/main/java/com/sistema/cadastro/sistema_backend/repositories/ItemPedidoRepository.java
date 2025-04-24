package com.sistema.cadastro.sistema_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.cadastro.sistema_backend.domain.ItemPedido;
import com.sistema.cadastro.sistema_backend.domain.ItemPedidoPK;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK> {

}
