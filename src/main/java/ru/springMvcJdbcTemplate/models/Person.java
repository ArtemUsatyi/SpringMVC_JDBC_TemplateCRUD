package ru.springMvcJdbcTemplate.models;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {

    private int id;
    @NotEmpty( message = "Имя не должно быть пустым")
    @Size(min = 3, max = 150, message = "Длина символов имени должна быть от 3 до 150 символов")
    private String name;
    @Min(value = 0, message = "Возраст человека должен быть больше 0")
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
