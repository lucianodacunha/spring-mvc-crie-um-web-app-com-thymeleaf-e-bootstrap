package io.github.lucianodacunha.app.controller;

import io.github.lucianodacunha.app.dto.DadosCadastroPedido;
import io.github.lucianodacunha.app.model.Pedido;
import io.github.lucianodacunha.app.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/formulario")
    public String formulario(DadosCadastroPedido dadosCadastroPedido){
        return "pedido/formulario";
    }

    @PostMapping("/novo")
    public String novo(@Valid DadosCadastroPedido dadosCadastroPedido,
                       BindingResult result) {
        if (result.hasErrors()){
            return "pedido/formulario";
        }

        Pedido pedido = dadosCadastroPedido.toPedido();
        pedidoRepository.save(pedido);
        return "redirect:/home";
    }
}
