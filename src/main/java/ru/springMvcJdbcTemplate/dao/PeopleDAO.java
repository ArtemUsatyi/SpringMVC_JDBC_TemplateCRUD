package ru.springMvcJdbcTemplate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.springMvcJdbcTemplate.models.Book;
import ru.springMvcJdbcTemplate.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PeopleDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> all() {
        return jdbcTemplate.query("SELECT * FROM Person", new PeopleMapper());
    }

    public Person alone(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new PeopleMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, age) VALUES (?,?)", person.getName(), person.getAge());
    }

    public void update(Person person, int id) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=? WHERE id=?", person.getName(), person.getAge(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public List<Book> getBooksByPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id_person=?", new Object[]{id}, new LibraryMapper());
    }
    public Optional<Person> getFindName(String name){
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=?",
                new Object[]{name}, new PeopleMapper()).stream().findAny();
    }
}
