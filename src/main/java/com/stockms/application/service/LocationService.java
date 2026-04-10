package com.stockms.application.service;

import com.stockms.domain.exception.ConflictException;
import com.stockms.domain.exception.ResourceNotFoundException;
import com.stockms.domain.model.Location;
import com.stockms.domain.model.LocationType;
import com.stockms.domain.port.out.LocationRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Application service orchestrating location use cases. */
@Service
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location create(Location location) {
        if (locationRepository.existsByStoreCodeAndName(
                location.getStoreCode(), location.getName())) {
            throw new ConflictException(
                    "Location already exists: " + location.getStoreCode()
                    + "/" + location.getName());
        }
        location.setActive(true);
        return locationRepository.save(location);
    }

    @Transactional(readOnly = true)
    public Location findById(UUID id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Location update(UUID id, String name, LocationType type) {
        Location existing = findById(id);
        existing.setName(name);
        existing.setType(type);
        return locationRepository.save(existing);
    }

    public void deactivate(UUID id) {
        Location existing = findById(id);
        existing.setActive(false);
        locationRepository.save(existing);
    }
}
