package io.github.lucianodacunha.app.dto;

import io.github.lucianodacunha.app.model.Pedido;
import io.github.lucianodacunha.app.model.StatusPedido;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class DadosCadastroPedido {
    @NonNull
    @NotBlank
    private String nome;
    @NonNull
    @NotBlank
    private String descricao;
    @NonNull
    @NotBlank
    private String url;
    private String imagem;

    public Pedido toPedido() {

        Pedido pedido = new Pedido();
        pedido.setNome(this.getNome());
        pedido.setDescricao(this.getDescricao());
        pedido.setUrl(this.getUrl());
        pedido.setImagem(this.getImagem());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        return pedido;
    }
}
