package dev.shouryapunj.service;

import dev.shouryapunj.dto.MenuDTO;
import dev.shouryapunj.entity.Location;
import dev.shouryapunj.entity.Menu;
import dev.shouryapunj.repository.MenuRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final Logger logger = LogManager.getLogger(MenuService.class);

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Optional<List<Menu>> getMenu() {
        logger.info("MenuService : Fetching menu items");
        Optional<List<Menu>> menuList = Optional.of(new ArrayList<>());
        menuRepository
                .findAll()
                .forEach(menu -> {
                    menuList.get().add(menu);
                });

        return menuList.get().size() == 0 ? Optional.empty() : menuList;
    }


    public Optional<Menu> addNewMenuItem(MenuDTO menuDTO) {
        logger.info("MenuService : Adding new menu item");
        Optional<Menu> existingMenu = Optional.ofNullable(menuRepository.findByName(menuDTO.getName()));
        if (existingMenu.isPresent()) {
            return Optional.empty();
        }

        Menu menu = new Menu(
                menuDTO.getName(),
                menuDTO.getUnitPrice(),
                ZonedDateTime.now(),
                ZonedDateTime.now()
        );
        menuRepository.save(menu);

        return Optional.of(menu);
    }

    private Optional<Menu> findMenuById(String id) {
        return menuRepository.findById(id);
    }

    public Optional<Menu> findMenuByName(String name) {
        return Optional.ofNullable(menuRepository.findByName(name));
    }

    public Optional<Menu> updateMenu(String id, MenuDTO menuDTO) {
        logger.info("MenuService : Updating menu item");
        Optional<Menu> updatedMenu = Optional.empty();
        if (findMenuById(id).isPresent()) {
            Menu existingMenu = findMenuById(id).get();
            existingMenu.setName(menuDTO.getName());
            existingMenu.setUnitPrice(menuDTO.getUnitPrice());
            existingMenu.setModifiedOn(ZonedDateTime.now());

            updatedMenu =  Optional.of(menuRepository.save(existingMenu));
        }

        return updatedMenu;
    }

    public Optional<Menu> deleteMenu(String id) {
        logger.info("MenuService : Deleting menu item" );
        Optional<Menu> deletedMenu = Optional.empty();
        if (findMenuById(id).isPresent()) {
            deletedMenu = Optional.of(findMenuById(id).get());
            menuRepository.deleteById(id);
        }
        return deletedMenu;
    }
}
