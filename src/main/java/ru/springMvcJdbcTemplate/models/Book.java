package ru.springMvcJdbcTemplate.models;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min=3, max = 150, message = "Длина символов в названии книги от 3 до 150")
    private String nameBook;
    @NotEmpty(message = "Имя автора книги не должно быть пустым")
    @Size(min=3, max = 150, message = "Длина названия автора книги от 3 до 150")
    private String nameAuthor;
    @Min(value = 10, message = "минимальный год выпуска книг от 800")
    @Max(value = 2023, message = "Максимальный год выпуска книги до 2023")
    private int age;

    public Book() {
    }

    public Book(String nameBook, String nameAuthor, int age) {
        this.nameBook = nameBook;
        this.nameAuthor = nameAuthor;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
