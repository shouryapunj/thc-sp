package dev.shouryapunj.repository;

import dev.shouryapunj.entity.Menu;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<Menu, String> {

    Menu findByName(String name);
}
