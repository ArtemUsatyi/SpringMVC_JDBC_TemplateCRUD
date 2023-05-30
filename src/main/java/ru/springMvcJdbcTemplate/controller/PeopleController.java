package ru.springMvcJdbcTemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springMvcJdbcTemplate.dao.PeopleDAO;
import ru.springMvcJdbcTemplate.models.Person;
import ru.springMvcJdbcTemplate.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleDAO peopleDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleDAO peopleDAO, PersonValidator personValidator) {
        this.peopleDAO = peopleDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String allPeople(Model model) {
        // Список всех люди
        model.addAttribute("people", peopleDAO.all());
        return "people/allPeople";
    }

    @GetMapping("/new")
    public String newPeople(@ModelAttribute("person") Person person) {
        // создание формы человека
        return "people/newPerson";
    }

    @PostMapping
    public String createPeople(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        // Отправить данные о новом человеке на сервер
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors()) return "people/newPerson";
        peopleDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}")
    public String onePeople(@PathVariable("id") int id, Model model) {
        // Один человек и список книг
        model.addAttribute("person", peopleDAO.alone(id));
        model.addAttribute("books", peopleDAO.getBooksByPerson(id));
        return "people/onePeople";
    }

    @GetMapping("{id}/edit")
    public String editPeople(@PathVariable("id") int id, Model model) {
        // Редактировать человека
        model.addAttribute("person", peopleDAO.alone(id));
        return "people/editPerson";
    }

    @PatchMapping("{id}")
    public String updatePeople(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        // Обновить данные о человека
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()) return "people/editPerson";
        peopleDAO.update(person, id);
        return "redirect:/people";
    }

    @DeleteMapping("{id}")
    public String deletePeople(@PathVariable("id") int id) {
        // Удалить человека
        peopleDAO.delete(id);
        return "redirect:/people";
    }
}
