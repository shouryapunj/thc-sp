package dev.shouryapunj.controller;

import dev.shouryapunj.dto.MenuDTO;
import dev.shouryapunj.entity.Menu;
import dev.shouryapunj.service.ApiStatsService;
import dev.shouryapunj.service.MenuService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private static final Logger logger = LogManager.getLogger(MenuController.class);

    private final MenuService menuService;

    private final ApiStatsService apiStatsService;

    public MenuController(MenuService menuService, ApiStatsService apiStatsService) {
        this.menuService = menuService;
        this.apiStatsService = apiStatsService;
    }

    /**
     * Fetches all items in the menu
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity getMenu() {
        long start = System.currentTimeMillis();
        logger.info("Fetching all Menu");
        Optional<List<Menu>> menuList = menuService.getMenu();
        if (menuList.isEmpty()) {
            logger.info("Fetched 0 Menu!");
            apiStatsService.saveApiStatistics("/api/menu/get", RequestMethod.GET, System.currentTimeMillis()-start);
            return ResponseEntity.noContent().build();
            //"No data present for Menu!"
        }
        long end = System.currentTimeMillis();
        logger.info("Fetched " + menuList.get().size() + " Menu Items in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/menu/get", RequestMethod.GET, end-start);
        return ResponseEntity.ok(menuList.get());
    }

    /**
     * Adds new item in the menu
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addNewMenu(@RequestBody MenuDTO menuDTO) {
        long start = System.currentTimeMillis();
        logger.info("Adding new Menu");
        Optional<Menu> menuItem = menuService.addNewMenuItem(menuDTO);
        if (menuItem.isEmpty()) {
            logger.info("Menu Item already exists!");
            apiStatsService.saveApiStatistics("/api/menu/add", RequestMethod.POST, System.currentTimeMillis()-start);
            return ResponseEntity.ok("Menu Item already exists, please use update api to make changes!");
        }
        long end = System.currentTimeMillis();
        logger.info("Added new Menu Item " + menuItem.get() + " in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/menu/add", RequestMethod.POST, end-start);
        return ResponseEntity.ok(menuItem.get());
    }

    /**
     * Searches for item in the menu using item's name
     */
    @RequestMapping(value = "/find/{name}", method = RequestMethod.GET)
    public ResponseEntity searchMenu(@PathVariable("name") String name) {
        long start = System.currentTimeMillis();
        logger.info("Searching for {" + name + "} in Menu" );
        Optional<Menu> menuItem = menuService.findMenuByName(name);
        if (menuItem.isEmpty()) {
            logger.info("No Menu Item found with name : {" + name + "}");
            apiStatsService.saveApiStatistics("/api/menu/find/{name}", RequestMethod.GET, System.currentTimeMillis()-start);
            return ResponseEntity.ok("No Menu Item found with name : {" + name + "}");
        }
        long end = System.currentTimeMillis();
        logger.info("Found {" + name + "} in 1 Menu in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/menu/find/{name}", RequestMethod.GET, end-start);
        return ResponseEntity.ok(menuItem.get());
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateMenu(@PathVariable("id") String id, @RequestBody MenuDTO menuDTO) {
        long start = System.currentTimeMillis();
        logger.warn("Updating Menu with id " + id);
        Optional<Menu> updatedMenu = menuService.updateMenu(id, menuDTO);
        if (updatedMenu.isEmpty()) {
            logger.info("Update failed, No Menu was found with id " + id);
            apiStatsService.saveApiStatistics("/api/menu/update/{id}", RequestMethod.PUT, System.currentTimeMillis()-start);
            return ResponseEntity.ok("No Menu with ID : \""+ id + "\" found for update!");
        }
        long end = System.currentTimeMillis();
        logger.info("Updated Menu " + updatedMenu.get() + " in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/menu/update/{id}", RequestMethod.PUT, end-start);
        return ResponseEntity.ok(updatedMenu.get());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMenu(@PathVariable("id") String id) {
        long start = System.currentTimeMillis();
        logger.warn("Deleting Menu Item with id " + id);
        Optional<Menu> deletedMenu = menuService.deleteMenu(id);
        if (deletedMenu.isEmpty()) {
            logger.info("Delete failed, No Menu Item was found with id " + id);
            apiStatsService.saveApiStatistics("/api/menu/delete/{id}", RequestMethod.DELETE, System.currentTimeMillis()-start);
            return ResponseEntity.ok("No Menu with ID : \""+ id + "\" found for delete!");
        }
        long end = System.currentTimeMillis();
        logger.info("Deleted Menu " + deletedMenu.get() + " in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/menu/delete/{id}", RequestMethod.DELETE, end-start);
        return ResponseEntity.ok(deletedMenu.get());
    }

}
