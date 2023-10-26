package io.github.lucianodacunha.app.repository;

import io.github.lucianodacunha.app.model.Pedido;
import io.github.lucianodacunha.app.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByStatus(StatusPedido statusPedido);
}

