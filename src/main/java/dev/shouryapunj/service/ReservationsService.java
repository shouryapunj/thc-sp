package dev.shouryapunj.service;

import dev.shouryapunj.dto.ReservationsDTO;
import dev.shouryapunj.entity.Location;
import dev.shouryapunj.entity.OpenHours;
import dev.shouryapunj.entity.Reservations;
import dev.shouryapunj.repository.ReservationsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationsService {

    private final Logger logger = LogManager.getLogger(ReservationsService.class);

    private final ReservationsRepository reservationsRepository;

    private final LocationService locationService;

    public ReservationsService(ReservationsRepository reservationsRepository, LocationService locationService) {
        this.reservationsRepository = reservationsRepository;
        this.locationService = locationService;
    }

    public Optional<List<Reservations>> getReservations() {
        logger.info("ReservationsService : Fetching reservations");
        Optional<List<Reservations>> reservationsList = Optional.of(new ArrayList<>());
        reservationsRepository
                .findAll()
                .forEach(reservations -> {
                    reservationsList.get().add(reservations);
                });

        return reservationsList.get().size() == 0 ? Optional.empty() : reservationsList;
    }

    @Transactional
    public Optional<Reservations> addReservation(ReservationsDTO reservationsDTO) {
        logger.info("ReservationsService : Adding new reservation");
        Optional<Reservations> existingReservation = Optional.ofNullable(reservationsRepository.findByEmail(reservationsDTO.getEmail()));
        if (existingReservation.isPresent()) {
            return Optional.empty();
        }

        Optional<Location> location = findLocationForReservation(reservationsDTO.getLocation().getLocationId());

        if (location.isEmpty()) {
            logger.info("No such location found for reservation!");
            return Optional.empty();
        }

        Reservations reservations = new Reservations(
                reservationsDTO.getName(),
                reservationsDTO.getEmail(),
                reservationsDTO.getDateTime(),
                reservationsDTO.getNumberOfPeople(),
                ZonedDateTime.now(),
                ZonedDateTime.now(),
                location.get()
        );
        reservationsRepository.save(reservations);

        return Optional.of(reservations);
    }

    private Optional<Location> findLocationForReservation(String locationId) {
        return locationService.findLocationById(locationId);
    }

    public Optional<Reservations> findReservationByEmail(String email) {
        return Optional.ofNullable(reservationsRepository.findByEmail(email));
    }

    @Transactional
    public Optional<Reservations> updateReservation(String id, ReservationsDTO reservationsDTO) {
        logger.info("ReservationService : Updating reservation");
        Optional<Reservations> updatedReservation = Optional.empty();
        if (findReservationById(id).isPresent()) {
            Reservations existingReservation = findReservationById(id).get();
            if (!reservationsDTO.getEmail().equals(existingReservation.getEmail())) {
                logger.info("Email mismatch in payload and database for this id, update failed!");
                return updatedReservation;
            }
            existingReservation.setName(reservationsDTO.getName());
            existingReservation.setDateTime(reservationsDTO.getDateTime());
            existingReservation.setNumberOfPeople(reservationsDTO.getNumberOfPeople());
            existingReservation.setLocation(reservationsDTO.getLocation());
            existingReservation.setModifiedOn(ZonedDateTime.now());

            updatedReservation =  Optional.of(reservationsRepository.save(existingReservation));
        }

        return updatedReservation;
    }

    private Optional<Reservations> findReservationById(String id) {
        return reservationsRepository.findById(id);
    }

    @Transactional
    public Optional<Reservations> deleteReservation(String id) {
        logger.info("ReservationService : Deleting reservation" );
        Optional<Reservations> deletedReservation = Optional.empty();
        if (findReservationById(id).isPresent()) {
            deletedReservation = Optional.of(findReservationById(id).get());
            reservationsRepository.deleteById(id);
        }
        return deletedReservation;
    }
}
