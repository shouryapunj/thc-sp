package dev.shouryapunj.service;

import dev.shouryapunj.dto.OpenHoursDTO;
import dev.shouryapunj.entity.OpenHours;
import dev.shouryapunj.repository.OpenHoursReposiotry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OpenHoursService {

    private final Logger logger = LogManager.getLogger(OpenHoursService.class);

    private final OpenHoursReposiotry openHoursReposiotry;

    public OpenHoursService(OpenHoursReposiotry openHoursReposiotry) {
        this.openHoursReposiotry = openHoursReposiotry;
    }

    public Optional<List<OpenHours>> getOpenHours() {
        logger.info("OpenHoursService : Fetching open hours");
        Optional<List<OpenHours>> openHoursList = Optional.of(new ArrayList<>());
        openHoursReposiotry
                .findAll()
                .forEach(openHours -> {
                    openHoursList.get().add(openHours);
                });

        return openHoursList.get().size() == 0 ? Optional.empty() : openHoursList;
    }

    @Transactional
    public Optional<OpenHours> addOpenHours(OpenHoursDTO openHoursDTO) {
        logger.info("OpenHoursService : Adding new open hours");
        Optional<OpenHours> existingOpenHours = Optional.ofNullable(openHoursReposiotry.findByDateOfOperation(openHoursDTO.getDateOfOperation()));
        if (existingOpenHours.isPresent()) {
            return Optional.empty();
        }

        OpenHours openHours = new OpenHours(
                openHoursDTO.getDateOfOperation(),
                openHoursDTO.getStartsAt(),
                openHoursDTO.getClosesAt(),
                ZonedDateTime.now(),
                ZonedDateTime.now()
        );
        openHoursReposiotry.save(openHours);

        return Optional.of(openHours);
    }


    public Optional<OpenHours> findOpenHoursByDateOfOperation(String dateOfOperation) {
        return Optional.ofNullable(openHoursReposiotry.findByDateOfOperation(dateOfOperation));
    }

    @Transactional
    public Optional<OpenHours> updateOpenHours(String id, OpenHoursDTO openHoursDTO) {
        logger.info("OpenHoursService : Updating open hours");
        Optional<OpenHours> updatedOpenHours = Optional.empty();
        if (findOpenHoursById(id).isPresent()) {
            OpenHours existingOpenHours = findOpenHoursById(id).get();
            if (!openHoursDTO.getDateOfOperation().equals(existingOpenHours.getDateOfOperation())) {
                logger.info("Date of operation mismatch in payload and database for this id, update failed!");
                return updatedOpenHours;
            }
            existingOpenHours.setStartsAt(openHoursDTO.getStartsAt());
            existingOpenHours.setClosesAt(openHoursDTO.getClosesAt());
            existingOpenHours.setModifiedOn(ZonedDateTime.now());

            updatedOpenHours =  Optional.of(openHoursReposiotry.save(existingOpenHours));
        }

        return updatedOpenHours;
    }

    private Optional<OpenHours> findOpenHoursById(String id) {
        return openHoursReposiotry.findById(id);
    }

    @Transactional
    public Optional<OpenHours> deleteOpenHours(String id) {
        logger.info("OpenHoursService : Deleting open hours" );
        Optional<OpenHours> deletedOpenHours = Optional.empty();
        if (findOpenHoursById(id).isPresent()) {
            deletedOpenHours = Optional.of(findOpenHoursById(id).get());
            openHoursReposiotry.deleteById(id);
        }
        return deletedOpenHours;
    }
}
