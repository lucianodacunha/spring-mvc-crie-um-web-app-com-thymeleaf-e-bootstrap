package io.github.lucianodacunha.app.controller;

import io.github.lucianodacunha.app.model.Pedido;
import io.github.lucianodacunha.app.model.StatusPedido;
import io.github.lucianodacunha.app.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private PedidoRepository pedidoRepository;
    @GetMapping
    public String home(Model model){

        model.addAttribute("valor", "Meus Pedidos");
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "home";
    }

    @GetMapping("/{status}")
    public String porStatus(@PathVariable("status") String status, Model model){
        model.addAttribute("valor", "Meus Pedidos 2");
        model.addAttribute("pedidos", pedidoRepository.findByStatus(StatusPedido.valueOf(status.toUpperCase())));
        model.addAttribute("status", status);
        return "home";
    }

    @ExceptionHandler (IllegalArgumentException.class)
    public String onError(){
        return "redirect:/home";
    }
}
