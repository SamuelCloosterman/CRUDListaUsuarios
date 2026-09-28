package com.lunaltas.ListaUsuarios.controllers;

import com.lunaltas.ListaUsuarios.model.Animal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    private List<Animal> listAnimal = new ArrayList<>();

    @GetMapping("/index")
    public String index(ModelMap model) {

        model.addAttribute("animals", listAnimal);
        model.addAttribute("animalSize", listAnimal.size());

        return "animals/index";
    }

    @GetMapping("/new")
    public String animalNew(ModelMap model) {

        model.addAttribute("animal", new Animal());

        return "animals/new";
    }

    @PostMapping("/create")
    public String animalCreate(@ModelAttribute Animal animal) {

        animal.setId((long) listAnimal.size() + 1);

        listAnimal.add(animal);

        return "redirect:/users/index";
    }

    @GetMapping("/edit/{id}")
    public String animalEdit(@PathVariable Long id, ModelMap model) {

        for (Animal animal : listAnimal) {

            if (animal.getId().equals(id)) {
                model.addAttribute("animal", animal);

                return "animals/edit";
            }
        }

        return "redirect:/users/index";
    }

    @PostMapping("/update")
    public String animalUpdate(@ModelAttribute Animal animal) {

        for (int i = 0; i < listAnimal.size(); i++) {

            if (listAnimal.get(i).getId().equals(animal.getId())) {
                listAnimal.set(i, animal);

                break;
            }
        }

        return "redirect:/users/index";
    }

    @GetMapping("/delete/{id}")
    public String animalDelete(@PathVariable Long id) {

        listAnimal.removeIf(animal -> animal.getId().equals(id));

        return "redirect:/users/index";
    }


}