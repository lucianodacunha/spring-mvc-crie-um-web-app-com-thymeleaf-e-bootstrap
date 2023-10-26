package io.github.lucianodacunha.app.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "PEDIDO")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String nome;
    private String descricao;
    @NonNull
    private BigDecimal valor;
    @NonNull
    private LocalDate data;
    @NonNull
    private String url;
    @NonNull
    private String imagem;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
}
