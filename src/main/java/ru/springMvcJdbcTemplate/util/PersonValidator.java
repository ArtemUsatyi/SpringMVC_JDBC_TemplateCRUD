package ru.springMvcJdbcTemplate.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.springMvcJdbcTemplate.dao.PeopleDAO;
import ru.springMvcJdbcTemplate.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PeopleDAO peopleDAO;

    @Autowired
    public PersonValidator(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(peopleDAO.getFindName(person.getName()).isPresent())
            errors.rejectValue("name","","Пользователь с таким именем существует! Пожалуйста измените имя!");
    }
}
