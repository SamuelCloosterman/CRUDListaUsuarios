package com.lunaltas.ListaUsuarios.controllers;

import com.lunaltas.ListaUsuarios.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> listUser = new ArrayList<User>();

    @GetMapping("/index")
    public String index(ModelMap model){
        model.addAttribute("users", listUser);
        model.addAttribute("size", listUser.size());
        return "users/index";
    }

    @GetMapping("new")
    public String userNew(ModelMap model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping("create")
    public String userCreate(@ModelAttribute User user, ModelMap model){
        // Imprime os dados no console do servidor
        System.out.println("######################################");
        System.out.println("Nome recebido: " + user.getName());
        System.out.println("######################################");

        // Passa o objeto de volta para exibir os dados na tela de sucesso
        long id = listUser.size()+1;
        listUser.add(user);

        return "redirect:/users/index";
    }
}
