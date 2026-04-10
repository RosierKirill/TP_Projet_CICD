package com.stockms.domain.port.out;

import com.stockms.domain.model.Location;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Output port (driven side) — persistence contract for Location. */
public interface LocationRepository {
    Location save(Location location);
    Optional<Location> findById(UUID id);
    List<Location> findAll();
    boolean existsByStoreCodeAndName(String storeCode, String name);
}
