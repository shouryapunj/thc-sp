package dev.shouryapunj.service;

import dev.shouryapunj.entity.ApiStats;
import dev.shouryapunj.entity.Location;
import dev.shouryapunj.repository.ApiStatsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApiStatsService {

    private final Logger logger = LogManager.getLogger(ApiStatsService.class);

    private final ApiStatsRepository apiStatsRepository;

    public ApiStatsService(ApiStatsRepository apiStatsRepository) {
        this.apiStatsRepository = apiStatsRepository;
    }

    @Transactional
    public void saveApiStatistics(String name, RequestMethod method, long executionTime) {
        logger.info("Saving api statistics for : " + name);
        ApiStats apiStats = new ApiStats(
                name,
                method,
                executionTime,
                ZonedDateTime.now(),
                ZonedDateTime.now()
        );
        apiStatsRepository.save(apiStats);
        logger.info("Saved api statistics for : " + name);
    }

    @Transactional
    public Optional<List<ApiStats>> getStats() {
        Optional<List<ApiStats>> apiStatsList = Optional.of(new ArrayList<>());
        apiStatsRepository
                .findAll()
                .forEach(apiStats -> {
                    apiStatsList.get().add(apiStats);
                });
        return apiStatsList.get().size() == 0 ? Optional.empty() : apiStatsList;
    }
}
