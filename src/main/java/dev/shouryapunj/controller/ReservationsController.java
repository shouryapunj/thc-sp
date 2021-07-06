package dev.shouryapunj.controller;

import dev.shouryapunj.dto.ReservationsDTO;
import dev.shouryapunj.entity.ApiStats;
import dev.shouryapunj.entity.Reservations;
import dev.shouryapunj.service.ApiStatsService;
import dev.shouryapunj.service.ReservationsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    private final Logger logger = LogManager.getLogger(RestController.class);

    private final ReservationsService reservationsService;

    private final ApiStatsService apiStatsService;

    public ReservationsController(ReservationsService reservationsService, ApiStatsService apiStatsService) {
        this.reservationsService = reservationsService;
        this.apiStatsService = apiStatsService;
    }

    /**
     * Fetches all reservations
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity getReservations() {
        long start = System.currentTimeMillis();
        logger.info("Fetching all Reservations");
        Optional<List<Reservations>> reservationsList = reservationsService.getReservations();
        if (reservationsList.isEmpty()) {
            logger.info("Fetched 0 Reservations!");
            apiStatsService.saveApiStatistics("/api/reservations/get", RequestMethod.GET, System.currentTimeMillis()-start);
            return ResponseEntity.noContent().build();
            //"No data present for Menu!"
        }
        long end = System.currentTimeMillis();
        logger.info("Fetched " + reservationsList.get().size() + " Reservations in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/reservations/get", RequestMethod.GET, end-start);
        return ResponseEntity.ok(reservationsList.get());
    }

    /**
     * Adds new reservation
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addReservation(@RequestBody ReservationsDTO reservationsDTO) {
        long start = System.currentTimeMillis();
        logger.info("Adding new Reservation");
        Optional<Reservations> reservation = reservationsService.addReservation(reservationsDTO);
        if (reservation.isEmpty()) {
            logger.info("Reservation couldn't be made!");
            apiStatsService.saveApiStatistics("/api/reservations/add", RequestMethod.POST, System.currentTimeMillis()-start);
            return ResponseEntity.ok("Reservation already exists, please use update api to make changes!");
        }
        long end = System.currentTimeMillis();
        logger.info("Added new Reservation " + reservation.get().toString() + " in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/reservations/add", RequestMethod.POST, end-start);
        return ResponseEntity.ok(reservation.get());
    }

    /**
     * Searches for reservation by email
     */
    @RequestMapping(value = "/find/{email}", method = RequestMethod.GET)
    public ResponseEntity getReservationByEmail(@PathVariable("email") String email) {
        long start = System.currentTimeMillis();
        logger.info("Searching for {" + email + "} in Reservations" );
        Optional<Reservations> reservation = reservationsService.findReservationByEmail(email);
        if (reservation.isEmpty()) {
            logger.info("No Reservation found with email : {" + email + "}");
            apiStatsService.saveApiStatistics("/api/reservations/find/{email}", RequestMethod.GET, System.currentTimeMillis()-start);
            return ResponseEntity.noContent().build();
//            return ResponseEntity.ok("No Menu Item found with name : {" + dateOfOperation + "}");
        }
        long end = System.currentTimeMillis();
        logger.info("Found {" + email + "} in 1 Reservations in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/reservations/find/{email}", RequestMethod.GET, end-start);
        return ResponseEntity.ok(reservation.get());
    }

    /**
     * Updates reservation for given id
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateReservationById(@PathVariable("id") String id, @RequestBody ReservationsDTO reservationsDTO) {
        long start = System.currentTimeMillis();
        logger.warn("Updating Reservation with id " + id);
        Optional<Reservations> updatedReservation = reservationsService.updateReservation(id, reservationsDTO);
        if (updatedReservation.isEmpty()) {
            logger.info("Update failed, No Reservation was found with id " + id);
            apiStatsService.saveApiStatistics("/api/reservations/update/{id}", RequestMethod.PUT, System.currentTimeMillis()-start);
            return ResponseEntity.noContent().build();
//            return ResponseEntity.ok("No Menu with ID : \""+ id + "\" found for update!");
        }
        long end = System.currentTimeMillis();
        logger.info("Updated Reservation " + updatedReservation.get() + " in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/reservations/update/{id}", RequestMethod.PUT, end-start);
        return ResponseEntity.ok(updatedReservation.get());
    }

    /**
     * Deletes reservation by id
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteReservationById(@PathVariable("id") String id) {
        long start = System.currentTimeMillis();
        logger.warn("Deleting Reservation with id " + id);
        Optional<Reservations> deletedReservation = reservationsService.deleteReservation(id);
        if (deletedReservation.isEmpty()) {
            logger.info("Delete failed, No Reservation was found with id " + id);
            apiStatsService.saveApiStatistics("/api/reservations/delete/{id}", RequestMethod.DELETE, System.currentTimeMillis()-start);
            return ResponseEntity.noContent().build();
//            return ResponseEntity.ok("No Menu with ID : \""+ id + "\" found for delete!");
        }
        long end = System.currentTimeMillis();
        logger.info("Deleted Reservation " + deletedReservation.get() + " in " + (end-start) + "ms");
        apiStatsService.saveApiStatistics("/api/reservations/delete/{id}", RequestMethod.DELETE, end-start);
        return ResponseEntity.ok(deletedReservation.get());
    }
}
