package ru.springMvcJdbcTemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springMvcJdbcTemplate.dao.LibraryDAO;
import ru.springMvcJdbcTemplate.dao.PeopleDAO;
import ru.springMvcJdbcTemplate.models.Book;
import ru.springMvcJdbcTemplate.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class LibraryController {

    private final LibraryDAO libraryDAO;
    private final PeopleDAO peopleDAO;

    @Autowired
    public LibraryController(LibraryDAO libraryDAO, PeopleDAO peopleDAO) {
        this.libraryDAO = libraryDAO;
        this.peopleDAO = peopleDAO;
    }

    @GetMapping
    public String allBook(Model model) {
        model.addAttribute("listBook", libraryDAO.all());
        return "books/library";
        // Список всех книг
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        // Новая книга
        return "books/newBook";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        // Отправить данные новой книги на сервер
        if (bindingResult.hasErrors()) return "books/newBook";
        libraryDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("{id}")
    public String oneBook(@PathVariable("id") int id, Model model) {
        // Одна книга. Проверка состоянии книги, находиться у владельца или в библиотеке
        model.addAttribute("book", libraryDAO.alone(id));

        Optional<Person> bookOwner = libraryDAO.getBookOwner(id);
        if (bookOwner.isPresent()) model.addAttribute("owner", bookOwner.get());
        else model.addAttribute("people", peopleDAO.all());

        return "books/oneBook";
    }

    @GetMapping("{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        // Редактировать книгу
        model.addAttribute("book", libraryDAO.alone(id));
        return "books/editBook";
    }

    @GetMapping("{id}/free")
    public String freeBook(@PathVariable("id") int id) {
        //Вернуть книгу в библиотеку
        libraryDAO.backLibrary(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        //Отдать книгу владельцу
        libraryDAO.giveOwner(id, person);
        return "redirect:/books/{id}";
    }

    @PatchMapping("{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        // Обновить данные книги на сервере
        if(bindingResult.hasErrors()) return "books/editBook";
        libraryDAO.update(book, id);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable("id") int id) {
        // удалить книгу из БД
        libraryDAO.delete(id);
        return "redirect:/books";
    }
}
