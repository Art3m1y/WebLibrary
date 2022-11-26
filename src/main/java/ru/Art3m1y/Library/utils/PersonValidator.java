package ru.Art3m1y.Library.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.Art3m1y.Library.models.Person;
import ru.Art3m1y.Library.services.PersonService;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        System.out.println(person);

        if (personService.findByName(person.getName()) != null) {
            errors.rejectValue("name", "", "Это имя уже есть в базе данных");
        }

    }
}
