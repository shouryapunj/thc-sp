package dev.shouryapunj.controller;

import dev.shouryapunj.entity.ApiStats;
import dev.shouryapunj.entity.Menu;
import dev.shouryapunj.service.ApiStatsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class THCController {

    private static final Logger logger = LogManager.getLogger(THCController.class);

    private final ApiStatsService apiStatsService;

    public THCController(ApiStatsService apiStatsService) {
        this.apiStatsService = apiStatsService;
    }

    @RequestMapping(value = "/test" , method = RequestMethod.GET)
    public ResponseEntity testApi() {
        logger.info("Request made to /testApi ... " + ZonedDateTime.now());
        return ResponseEntity.ok().body("works!");
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public ResponseEntity getStats() {
        long start = System.currentTimeMillis();
        logger.info("Fetching all stats for THC apis");
        Optional<List<ApiStats>> apiStatsList = apiStatsService.getStats();
        if (apiStatsList.isEmpty()) {
            logger.info("Fetched 0 Api Stats!");
            return ResponseEntity.noContent().build();
        }
        long end = System.currentTimeMillis();
        logger.info("Fetched " + apiStatsList.get().size() + " Api Stats in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/stats", RequestMethod.GET, end-start);
        return ResponseEntity.ok(apiStatsList.get());
    }

}
