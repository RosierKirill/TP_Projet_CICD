package com.stockms.interfaces.rest;

import com.stockms.application.service.LocationService;
import com.stockms.interfaces.dto.request.CreateLocationRequest;
import com.stockms.interfaces.dto.request.UpdateLocationRequest;
import com.stockms.interfaces.dto.response.LocationResponse;
import com.stockms.interfaces.mapper.LocationRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for location CRUD. */
@RestController
@RequestMapping("/api/v1/locations")
@Tag(name = "Locations", description = "Store location management")
public class LocationController {

    private final LocationService locationService;
    private final LocationRestMapper mapper;

    public LocationController(LocationService locationService, LocationRestMapper mapper) {
        this.locationService = locationService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new location")
    public ResponseEntity<LocationResponse> create(
            @Valid @RequestBody CreateLocationRequest request) {
        LocationResponse body = mapper.toResponse(
                locationService.create(mapper.toDomain(request)));
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping
    @Operation(summary = "List all locations")
    public ResponseEntity<List<LocationResponse>> findAll() {
        List<LocationResponse> body = locationService.findAll().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a location by ID")
    public ResponseEntity<LocationResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toResponse(locationService.findById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a location (storeCode is immutable)")
    public ResponseEntity<LocationResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateLocationRequest request) {
        LocationResponse body = mapper.toResponse(
                locationService.update(id, request.name(), request.type()));
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate a location (soft delete)")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        locationService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
