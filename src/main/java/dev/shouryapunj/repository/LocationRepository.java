package dev.shouryapunj.repository;

import dev.shouryapunj.entity.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LocationRepository extends PagingAndSortingRepository<Location, String> {

    @Query(
            value = "SELECT * FROM LOCATION WHERE ADDRESS ILIKE %:address%",
            nativeQuery = true
    )
    List<Location> findByAddress(String address);

    List<Location> findByZip(String zip);

    List<Location> findByCity(String city);

    List<Location> findByState(String state);

    List<Location> findByCountry(String country);

    @Query(
            value = "SELECT * FROM LOCATION WHERE ADDRESS = :address " +
                    "AND ZIP = :zip " +
                    "AND CITY = :city " +
                    "AND STATE = :state " +
                    "AND COUNTRY = :country ",
            nativeQuery = true
    )
    Location findLocation(String address, String zip, String city, String state, String country);
}
