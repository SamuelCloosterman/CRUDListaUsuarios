package com.lunaltas.ListaUsuarios.controllers;

import com.lunaltas.ListaUsuarios.controllers.UserController;
import com.lunaltas.ListaUsuarios.model.User;
import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

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

    @GetMapping("/new")
    public String userNew(ModelMap model) {
        model.addAttribute("user", new User());

        return "users/new";
    }


    @PostMapping("/create")
    public String userCreate(@ModelAttribute User user) {

        boolean conectado = false;

        try {
            Base.open(
                    "org.sqlite.JDBC",
                    "jdbc:sqlite:meu_app.db",
                    "",
                    ""
            );

            conectado = true;

            System.out.println("Nome: " + user.getName());
            System.out.println("Arroba: " + user.getArroba());
            System.out.println("CPF: " + user.getCpf());

            user.saveIt();

            System.out.println("ID gerado: " + user.getId());
            System.out.println("Total no banco: " + User.count());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {
            if (conectado) {
                Base.close();
            }
        }

        return "redirect:/users/index";
    }


    @GetMapping("/edit/{id}")
    public String userEdit(@PathVariable Long id, ModelMap model) {

        boolean conectado = false;

        try {
            Base.open("org.sqlite.JDBC", "jdbc:sqlite:meu_app.db", "", "");
            conectado = true;

            User user = User.findFirst("id = ?", id);

            if (user == null) {
                return "redirect:/users/index";
            }

            model.addAttribute("user", user);

            model.addAttribute("id", user.get("id"));
            model.addAttribute("name", user.get("name"));
            model.addAttribute("arroba", user.get("arroba"));
            model.addAttribute("cpf", user.get("cpf"));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {
            if (conectado) {
                Base.close();
            }
        }

        return "users/edit";
    }

    @PostMapping("/update")
    public String userUpdate(@ModelAttribute User user) {

        boolean conectado = false;

        try {
            Base.open(
                    "org.sqlite.JDBC",
                    "jdbc:sqlite:meu_app.db",
                    "",
                    ""
            );

            conectado = true;

            Base.exec("""
            UPDATE users
            SET name = ?, arroba = ?, cpf = ?
            WHERE id = ?
            """,
                    user.getName(),
                    user.getArroba(),
                    user.getCpf(),
                    user.getId()
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {
            if (conectado) {
                Base.close();
            }
        }

        return "redirect:/users/index";
    }

    @PostMapping("/delete/{id}")
    public String userDelete(@PathVariable Long id) {

        boolean conectado = false;

        try {
            Base.open(
                    "org.sqlite.JDBC",
                    "jdbc:sqlite:meu_app.db",
                    "",
                    ""
            );

            conectado = true;

            Base.exec("DELETE FROM users WHERE id = ?", id);

            Base.exec("CREATE TEMP TABLE users_backup AS SELECT * FROM users");

            Base.exec("DELETE FROM users");

            Base.exec("""
                INSERT INTO users (id, name, arroba, cpf)
                SELECT
                    ROW_NUMBER() OVER (ORDER BY id),
                    name,
                    arroba,
                    cpf
                FROM users_backup
            """);

            Base.exec("DROP TABLE users_backup");

            Base.exec("""
                UPDATE sqlite_sequence
                SET seq = (SELECT COUNT(*) FROM users)
                WHERE name = 'users'
            """);

            System.out.println("Usuário excluído. ID: " + id);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {
            if (conectado) {
                Base.close();
            }
        }

        return "redirect:/users/index";
    }


}