package ru.springMvcJdbcTemplate.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.springMvcJdbcTemplate.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();

        book.setId(rs.getInt("id"));
        book.setAge(rs.getInt("age"));
        book.setNameBook(rs.getString("name_book"));
        book.setNameAuthor(rs.getString("name_author"));

        return book;
    }
}
