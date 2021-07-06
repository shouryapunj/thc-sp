package dev.shouryapunj.service;

import dev.shouryapunj.dto.LocationDTO;
import dev.shouryapunj.entity.Location;
import dev.shouryapunj.repository.LocationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private static final Logger logger = LogManager.getLogger(LocationService.class);

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Optional<List<Location>> getAllLocations() {
        Optional<List<Location>> locationList = Optional.of(new ArrayList<>());
        locationRepository
                .findAll()
                .forEach(location -> {
                    locationList.get().add(location);
                });

        return locationList.get().size() == 0 ? Optional.empty() : locationList;
    }

    public Optional<Location> addNewLocation(LocationDTO locationDto) {
        Optional<Location> existingLocation = Optional.ofNullable(locationRepository
                .findLocation(locationDto.getAddress(),
                        locationDto.getZip(),
                        locationDto.getCity(),
                        locationDto.getState(),
                        locationDto.getCountry()));
        if (existingLocation.isPresent()) {
            return Optional.empty();
        }

        Location location = new Location(
                locationDto.getAddress(),
                locationDto.getZip(),
                locationDto.getCity(),
                locationDto.getState(),
                locationDto.getCountry(),
                ZonedDateTime.now(),
                ZonedDateTime.now()
        );
        locationRepository.save(location);

        return Optional.of(location);
    }

    public Optional<List<Location>> findLocationByKeyword(String keyword) {
        Optional<List<Location>> locationList = Optional.of(new ArrayList<>());
        HashSet<Location> set = new HashSet<>();

        Optional<List<Location>> addressList = findLocationByAddress(keyword);
        addressList.ifPresent(set::addAll);

        Optional<List<Location>> cityList = findLocationByCity(keyword);
        cityList.ifPresent(set::addAll);

        Optional<List<Location>> zipList = findLocationByZip(keyword);
        zipList.ifPresent(set::addAll);

        Optional<List<Location>> stateList = findLocationByState(keyword);
        stateList.ifPresent(set::addAll);

        Optional<List<Location>> countryList = findLocationByCountry(keyword);
        countryList.ifPresent(set::addAll);

        set.forEach(location -> locationList.get().add(location));

        return locationList.get().size() == 0 ? Optional.empty() : locationList;
    }

    public Optional<Location> findLocationById(String id) {
        return locationRepository.findById(id);
    }

    public Optional<List<Location>> findLocationByAddress(String address) {
        Optional<List<Location>> locationListByAddress = Optional.of(new ArrayList<>());
        locationRepository
                .findByAddress(address)
                .forEach(location -> locationListByAddress.get().add(location));

        return locationListByAddress.get().size() == 0 ? Optional.empty() : locationListByAddress;
    }

    public Optional<List<Location>> findLocationByCity(String city) {
        Optional<List<Location>> locationListByCity = Optional.of(new ArrayList<>());
        locationRepository
                .findByCity(city)
                .forEach(location -> locationListByCity.get().add(location));

        return locationListByCity.get().size() == 0 ? Optional.empty() : locationListByCity;
    }

    public Optional<List<Location>> findLocationByZip(String zip) {
        Optional<List<Location>> locationListByZip = Optional.of(new ArrayList<>());
        locationRepository
                .findByZip(zip)
                .forEach(location -> locationListByZip.get().add(location));

        return locationListByZip.get().size() == 0 ? Optional.empty() : locationListByZip;
    }

    public Optional<List<Location>> findLocationByState(String state) {
        Optional<List<Location>> locationListByState = Optional.of(new ArrayList<>());
        locationRepository
                .findByState(state)
                .forEach(location -> locationListByState.get().add(location));

        return locationListByState.get().size() == 0 ? Optional.empty() : locationListByState;
    }

    public Optional<List<Location>> findLocationByCountry(String country) {
        Optional<List<Location>> locationListByCountry = Optional.of(new ArrayList<>());
        locationRepository
                .findByCountry(country)
                .forEach(location -> locationListByCountry.get().add(location));

        return locationListByCountry.get().size() == 0 ? Optional.empty() : locationListByCountry;
    }

    public Optional<Location> updateLocation(String id, Location location) {
        Optional<Location> updatedLocation = Optional.empty();
        if (findLocationById(id).isPresent()) {
            Location existingLocation = findLocationById(id).get();
            existingLocation.setAddress(location.getAddress());
            existingLocation.setCity(location.getCity());
            existingLocation.setZip(location.getZip());
            existingLocation.setState(location.getState());
            existingLocation.setCountry(location.getCountry());
            existingLocation.setModifiedOn(ZonedDateTime.now());

            updatedLocation =  Optional.of(locationRepository.save(existingLocation));
        }

        return updatedLocation;
    }

    public Optional<Location> deleteLocation(String id) {
        Optional<Location> deletedLocation = Optional.empty();
        if (findLocationById(id).isPresent()) {
            deletedLocation = Optional.of(findLocationById(id).get());
            locationRepository.deleteById(id);
        }
        return deletedLocation;
    }
}
