package dev.shouryapunj.repository;

import dev.shouryapunj.entity.Reservations;
import org.springframework.data.repository.CrudRepository;

public interface ReservationsRepository extends CrudRepository<Reservations, String> {

    Reservations findByEmail(String email);
}
