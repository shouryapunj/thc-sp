package dev.shouryapunj.controller;

import dev.shouryapunj.dto.OpenHoursDTO;
import dev.shouryapunj.entity.OpenHours;
import dev.shouryapunj.service.OpenHoursService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/openhours")
public class OpenHoursController {

    private final Logger logger = LogManager.getLogger(OpenHoursController.class);

    private final OpenHoursService openHoursService;

    public OpenHoursController(OpenHoursService openHoursService) {
        this.openHoursService = openHoursService;
    }

    /**
     * Fetches all open hours
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity getOpenHours() {
        long start = System.currentTimeMillis();
        logger.info("Fetching all Open Hours");
        Optional<List<OpenHours>> openHoursList = openHoursService.getOpenHours();
        if (openHoursList.isEmpty()) {
            logger.info("Fetched 0 Open Hours!");
            return ResponseEntity.noContent().build();
            //"No data present for Menu!"
        }
        long end = System.currentTimeMillis();
        logger.info("Fetched " + openHoursList.get().size() + " Open Hours in " + (end-start) + "ms");
        return ResponseEntity.ok(openHoursList.get());
    }

    /**
     * Adds new open hour entry
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addOpenHours(@RequestBody OpenHoursDTO openHoursDTO) {
        long start = System.currentTimeMillis();
        logger.info("Adding new Open Hour");
        Optional<OpenHours> openHours = openHoursService.addOpenHours(openHoursDTO);
        if (openHours.isEmpty()) {
            logger.info("Open Hour for this day already exists!");
            return ResponseEntity.ok("Open Hours already exists, please use update api to make changes!");
        }
        long end = System.currentTimeMillis();
        logger.info("Added new Open Hour " + openHours.get() + " in " + (end-start) + "ms");
        return ResponseEntity.ok(openHours.get());
    }

    /**
     * Searches for open hours using date
     */
    @RequestMapping(value = "/find/{dateOfOperation}", method = RequestMethod.GET)
    public ResponseEntity searchOpenHours(@PathVariable("dateOfOperation") String dateOfOperation) {
        long start = System.currentTimeMillis();
        logger.info("Searching for {" + dateOfOperation + "} in Open Hours" );
        Optional<OpenHours> openHours = openHoursService.findOpenHoursByDateOfOperation(dateOfOperation);
        if (openHours.isEmpty()) {
            logger.info("No Open Hours found with dateOfOperation : {" + dateOfOperation + "}");
            return ResponseEntity.noContent().build();
//            return ResponseEntity.ok("No Menu Item found with name : {" + dateOfOperation + "}");
        }
        long end = System.currentTimeMillis();
        logger.info("Found {" + dateOfOperation + "} in 1 Open Hours in " + (end-start) + "ms");
        return ResponseEntity.ok(openHours.get());
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOpenHours(@PathVariable("id") String id, @RequestBody OpenHoursDTO openHoursDTO) {
        long start = System.currentTimeMillis();
        logger.warn("Updating Open Hours with id " + id);
        Optional<OpenHours> updatedOpenHours = openHoursService.updateOpenHours(id, openHoursDTO);
        if (updatedOpenHours.isEmpty()) {
            logger.info("Update failed, No Open Hours was found with id " + id);
            return ResponseEntity.noContent().build();
//            return ResponseEntity.ok("No Menu with ID : \""+ id + "\" found for update!");
        }
        long end = System.currentTimeMillis();
        logger.info("Updated Open Hours " + updatedOpenHours.get() + " in " + (end-start) + "ms");
        return ResponseEntity.ok(updatedOpenHours.get());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOpenHours(@PathVariable("id") String id) {
        long start = System.currentTimeMillis();
        logger.warn("Deleting OpenHours with id " + id);
        Optional<OpenHours> deletedOpenHours = openHoursService.deleteOpenHours(id);
        if (deletedOpenHours.isEmpty()) {
            logger.info("Delete failed, No OpenHours was found with id " + id);
            return ResponseEntity.noContent().build();
//            return ResponseEntity.ok("No Menu with ID : \""+ id + "\" found for delete!");
        }
        long end = System.currentTimeMillis();
        logger.info("Deleted OpenHours " + deletedOpenHours.get() + " in " + (end-start) + "ms");
        return ResponseEntity.ok(deletedOpenHours.get());
    }

}
