package dev.shouryapunj.repository;

import dev.shouryapunj.entity.OpenHours;
import org.springframework.data.repository.CrudRepository;


public interface OpenHoursReposiotry extends CrudRepository<OpenHours, String> {

    OpenHours findByDateOfOperation(String dateOfOperation);
}
