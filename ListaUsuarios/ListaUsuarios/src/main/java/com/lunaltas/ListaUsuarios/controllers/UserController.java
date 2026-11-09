package com.lunaltas.ListaUsuarios.controllers;

import com.lunaltas.ListaUsuarios.controllers.UserController;
import com.lunaltas.ListaUsuarios.model.User;
import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private List<User> listUser = new ArrayList<>();


    @GetMapping("/index")
    public String index(ModelMap model) {

        try {
            Base.open("org.sqlite.JDBC", "jdbc:sqlite:meu_app.db", "", "");

            List<User> users = new ArrayList<>(User.findAll());

            model.addAttribute("users", users);
            model.addAttribute("userSize", User.count());

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            Base.close();
        }

        return "users/index";
    }

    @PostMapping("/create")
    public String userCreate(@ModelAttribute User user) {

        try {
            Base.open("org.sqlite.JDBC", "jdbc:sqlite:meu_app.db", "", "");

            user.saveIt();

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            Base.close();
        }

        return "redirect:/users/index";
    }


    @GetMapping("/new")
    public String userNew(ModelMap model) {
        model.addAttribute("user", new User());

        return "users/new";
    }

    @PostMapping("/create")
    public String userCreate(@ModelAttribute User user) {
        System.out.println("$$$$$$$$$$$$$$$$$$");
        System.out.println(user.getId());
        System.out.println(user.getCpf());

        user.setId((long) listUser.size() + 1);

        listUser.add(user);

        return "redirect:/users/index";
    }

    @GetMapping("/edit/{id}")
    public String userEdit(@PathVariable Long id, ModelMap model) {

        for (User user : listUser) {

            if (user.getId().equals(id)) {
                model.addAttribute("user", user);

                return "users/edit";
            }
        }

        return "redirect:/users/index";
    }

    @PostMapping("/update")
    public String userUpdate(@ModelAttribute User user) {

        for (int i = 0; i < listUser.size(); i++) {

            if (listUser.get(i).getId().equals(user.getId())) {
                listUser.set(i, user);

                break;
            }
        }

        return "redirect:/users/index";
    }

    @GetMapping("/delete/{id}")
    public String userDelete(@PathVariable Long id) {

        listUser.removeIf(user -> user.getId().equals(id));

        return "redirect:/users/index";
    }


}