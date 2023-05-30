package ru.springMvcJdbcTemplate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.springMvcJdbcTemplate.models.Book;
import ru.springMvcJdbcTemplate.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class LibraryDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LibraryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> all() {
        return jdbcTemplate.query("SELECT * FROM Book", new LibraryMapper());
    }

    public Book alone(int id) {
        return jdbcTemplate.query("SELECT *  FROM Book WHERE id=?", new Object[]{id}, new LibraryMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name_book, name_author, age) VALUES (?,?,?)", book.getNameBook(), book.getNameAuthor(), book.getAge());
    }

    public void update(Book book, int id) {
        jdbcTemplate.update("UPDATE Book SET name_book=?, name_author=?, age=? WHERE id=?", book.getNameBook(), book.getNameAuthor(), book.getAge(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON book.id_person = person.id WHERE book.id=?",
                new Object[]{id}, new PeopleMapper()).stream().findAny();
    }

    public void backLibrary(int id) {
        jdbcTemplate.update("UPDATE Book SET id_person=null WHERE id=?", id);
    }

    public void giveOwner(int id, Person person) {
        jdbcTemplate.update("UPDATE Book SET id_person=? WHERE id=?", person.getId(), id);
    }
}
