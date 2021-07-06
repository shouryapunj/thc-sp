package dev.shouryapunj.controller;

import dev.shouryapunj.dto.LocationDTO;
import dev.shouryapunj.entity.Location;
import dev.shouryapunj.service.ApiStatsService;
import dev.shouryapunj.service.LocationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private static final Logger logger = LogManager.getLogger(LocationController.class);

    private final LocationService locationService;

    private final ApiStatsService apiStatsService;

    public LocationController(LocationService locationService, ApiStatsService apiStatsService) {
        this.locationService = locationService;
        this.apiStatsService = apiStatsService;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity getAllLocations() {
        long start = System.currentTimeMillis();
        logger.info("Fetching all Locations");
        Optional<List<Location>> allLocations = locationService.getAllLocations();
        if (allLocations.isEmpty()) {
            logger.info("Fetched 0 Locations!");
            apiStatsService.saveApiStatistics("/api/locations/get", RequestMethod.GET, System.currentTimeMillis()-start);
            return ResponseEntity.ok("No data present for Locations!");
        }
        long end = System.currentTimeMillis();
        logger.info("Fetched " + allLocations.get().size() + " Locations in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/locations/get", RequestMethod.GET, end-start);
        return ResponseEntity.ok(allLocations.get());
    }

//    @RequestMapping(value = "/get", method = RequestMethod.GET)
//    public Page<Location> getAllLocationsByPageAndSize(@RequestParam("page") String page, @RequestParam("size") String size, Pageable P) {
//        int s = (int) P.getOffset();
//        int e = (int) ((s + pageable.getPageSize()) > fooList.size() ? fooList.size()
//                : (s + pageable.getPageSize()));
//    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addNewLocation(@RequestBody LocationDTO locationDto) {
        long start = System.currentTimeMillis();
        logger.info("Adding new location");
        Optional<Location> location = locationService.addNewLocation(locationDto);
        if (location.isEmpty()) {
            logger.info("Location already exists!");
            apiStatsService.saveApiStatistics("/api/locations/add", RequestMethod.POST, System.currentTimeMillis()-start);
            return ResponseEntity.ok("Location already exists, please use update api to make changes!");
        }
        long end = System.currentTimeMillis();
        logger.info("Added new location " + location.get() + " in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/locations/add", RequestMethod.POST, end-start);
        return ResponseEntity.ok(location.get());
    }

    @RequestMapping(value = "/find/{keyword}", method = RequestMethod.GET)
    public ResponseEntity searchLocation(@PathVariable("keyword") String keyword) {
        long start = System.currentTimeMillis();
        logger.info("Searching for {" + keyword + "} in Locations");
        Optional<List<Location>> locationList = locationService.findLocationByKeyword(keyword);
        if (locationList.isEmpty()) {
            logger.info("Found {" + keyword + "} in 0 Locations");
            return ResponseEntity.ok("No Location found with \"" + keyword + "\" keyword in it!");
        }
        long end = System.currentTimeMillis();
        logger.info("Found {" + keyword + "} in " + locationList.get().size() + " Locations in " + (end-start) + "ms");
        return ResponseEntity.ok(locationList.get());
    }

    @RequestMapping(value = "/find/id/{id}", method = RequestMethod.GET)
    public ResponseEntity searchLocationById(@PathVariable("id") String id) {
        long start = System.currentTimeMillis();
        logger.info("Searching for {" + id + "} in Locations");
        Optional<Location> location = locationService.findLocationById(id);
        if (location.isEmpty()) {
            logger.info("Found {" + id + "} in 0 Locations");
            return ResponseEntity.ok("No Location found with ID : \"" + id + "\"");
        }
        long end = System.currentTimeMillis();
        logger.info("Found ID : {" + id + "} in 1 Locations in " + (end-start) + "ms");
        return ResponseEntity.ok(location.get());
    }

    @RequestMapping(value = "/find/address/{address}", method = RequestMethod.GET)
    public ResponseEntity searchLocationByAddress(@PathVariable("address") String address) {
        long start = System.currentTimeMillis();
        logger.info("Searching for {" + address + "} in Locations");
        Optional<List<Location>> locationList = locationService.findLocationByAddress(address);
        if (locationList.isEmpty()) {
            logger.info("Found {" + address + "} in 0 Locations");
            return ResponseEntity.ok("No Location found with ADDRESS : \"" + address + "\"");
        }
        long end = System.currentTimeMillis();
        logger.info("Found ADDRESS : {" + address + "} in " + locationList.get().size() + " Locations in " + (end-start) + "ms");
        return ResponseEntity.ok(locationList.get());
    }

    @RequestMapping(value = "/find/city/{city}", method = RequestMethod.GET)
    public ResponseEntity searchLocationByCity(@PathVariable("city") String city) {
        long start = System.currentTimeMillis();
        logger.info("Searching for {" + city + "} in Locations");
        Optional<List<Location>> locationList = locationService.findLocationByCity(city);
        if (locationList.isEmpty()) {
            logger.info("Found {" + city + "} in 0 Locations ");
            return ResponseEntity.ok("No Location found with CITY : \"" + city + "\"");
        }
        long end = System.currentTimeMillis();
        logger.info("Found CITY : {" + city + "} in " + locationList.get().size() + " Locations in " + (end-start) + "ms");
        return ResponseEntity.ok(locationList.get());
    }

    @RequestMapping(value = "/find/zip/{zip}", method = RequestMethod.GET)
    public ResponseEntity searchLocationByZip(@PathVariable("zip") String zip) {
        long start = System.currentTimeMillis();
        logger.info("Searching for {" + zip + "} in Locations");
        Optional<List<Location>> locationList = locationService.findLocationByZip(zip);
        if (locationList.isEmpty()) {
            logger.info("Found {" + zip + "} in 0 locations");
            return ResponseEntity.ok("No Location found with ZIP : \"" + zip + "\"");
        }
        long end = System.currentTimeMillis();
        logger.info("Found ZIP : {" + zip + "} IN " + locationList.get().size() + " Locations in " + (end-start) + "ms");
        return ResponseEntity.ok(locationList.get());
    }

    @RequestMapping(value = "/find/state/{state}", method = RequestMethod.GET)
    public ResponseEntity searchLocationByState(@PathVariable("state") String state) {
        long start = System.currentTimeMillis();
        logger.info("Searching for {" + state + "} in locations");
        Optional<List<Location>> locationList = locationService.findLocationByState(state);
        if (locationList.isEmpty()) {
            logger.info("Found {" + state + "} in 0 Locations");
            return ResponseEntity.ok("No Location found with STATE : \"" + state + "\"");
        }
        long end = System.currentTimeMillis();
        logger.info("Found STATE : {" + state + "} in " + locationList.get().size() + " Locations in " + (end-start) + "ms");
        return ResponseEntity.ok(locationList.get());
    }

    @RequestMapping(value = "/find/country/{country}", method = RequestMethod.GET)
    public ResponseEntity searchLocationByCountry(@PathVariable("country") String country) {
        long start = System.currentTimeMillis();
        logger.info("Searching for {" + country + "} in Locations");
        Optional<List<Location>> locationList = locationService.findLocationByState(country);
        if (locationList.isEmpty()) {
            logger.info("Found {" + country + "} in 0 Locations");
            return ResponseEntity.ok("No Location found with COUNTRY : \"" + country + "\"");
        }
        long end = System.currentTimeMillis();
        logger.info("Found COUNTRY : {" + country + "} in " + locationList.get().size() + " Locations in " + (end-start) + "ms");
        return ResponseEntity.ok(locationList.get());
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateLocation(@PathVariable("id") String id, @RequestBody Location location) {
        long start = System.currentTimeMillis();
        logger.warn("Updating Location with id " + id);
        Optional<Location> updatedLocation = locationService.updateLocation(id, location);
        if (updatedLocation.isEmpty()) {
            logger.info("Update failed, No location was found with id " + id);
            return ResponseEntity.ok("No location with ID : \""+ id + "\" found for update!");
        }
        long end = System.currentTimeMillis();
        logger.info("Updated location " + updatedLocation.get() + " in " + (end-start) + "ms");
        return ResponseEntity.ok(updatedLocation.get());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLocation(@PathVariable("id") String id) {
        long start = System.currentTimeMillis();
        logger.warn("Deleting Location with id " + id);
        Optional<Location> deletedLocation = locationService.deleteLocation(id);
        if (deletedLocation.isEmpty()) {
            logger.info("Delete failed, No location was found with id " + id);
            return ResponseEntity.ok("No location with ID : \""+ id + "\" found for delete!");
        }
        long end = System.currentTimeMillis();
        logger.info("Deleted Location " + deletedLocation.get() + " in " + (end-start) + "ms");
        return ResponseEntity.ok(deletedLocation.get());
    }
}
