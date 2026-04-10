package com.stockms.interfaces.rest;

import com.stockms.interfaces.dto.request.CreateLocationRequest;
import com.stockms.interfaces.dto.request.UpdateLocationRequest;
import com.stockms.interfaces.dto.response.LocationResponse;
import com.stockms.domain.model.LocationType;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class LocationControllerIT extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    void cleanDb() {
        jdbc.execute("DELETE FROM stock_movement");
        jdbc.execute("DELETE FROM stock_item");
        jdbc.execute("DELETE FROM location");
    }

    @Test
    void createLocation_validRequest_returns201() {
        CreateLocationRequest req = new CreateLocationRequest(
                "STORE01", "Aisle-A1", LocationType.AISLE);

        ResponseEntity<LocationResponse> res = rest.postForEntity(
                "/api/v1/locations", req, LocationResponse.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(res.getBody()).isNotNull();
        assertThat(res.getBody().storeCode()).isEqualTo("STORE01");
        assertThat(res.getBody().type()).isEqualTo(LocationType.AISLE);
        assertThat(res.getBody().active()).isTrue();
    }

    @Test
    void createLocation_nullType_returns400() {
        CreateLocationRequest req = new CreateLocationRequest("STORE01", "Aisle-A2", null);

        ResponseEntity<String> res = rest.postForEntity(
                "/api/v1/locations", req, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void createLocation_duplicate_returns409() {
        CreateLocationRequest req = new CreateLocationRequest(
                "STORE01", "Reserve-R1", LocationType.RESERVE);
        rest.postForEntity("/api/v1/locations", req, LocationResponse.class);

        ResponseEntity<String> second = rest.postForEntity(
                "/api/v1/locations", req, String.class);

        assertThat(second.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void getLocationById_notFound_returns404() {
        ResponseEntity<String> res = rest.getForEntity(
                "/api/v1/locations/" + UUID.randomUUID(), String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateLocation_validRequest_returns200() {
        LocationResponse created = rest.postForEntity("/api/v1/locations",
                new CreateLocationRequest("STORE01", "OldName", LocationType.AISLE),
                LocationResponse.class).getBody();

        assertThat(created).isNotNull();

        UpdateLocationRequest update = new UpdateLocationRequest("NewName", LocationType.RESERVE);

        ResponseEntity<LocationResponse> res = rest.exchange(
                "/api/v1/locations/" + created.id(),
                HttpMethod.PUT,
                new HttpEntity<>(update),
                LocationResponse.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody()).isNotNull();
        assertThat(res.getBody().name()).isEqualTo("NewName");
        assertThat(res.getBody().type()).isEqualTo(LocationType.RESERVE);
    }

    @Test
    void deactivateLocation_returns204() {
        LocationResponse created = rest.postForEntity("/api/v1/locations",
                new CreateLocationRequest("STORE01", "ToDelete", LocationType.RESERVE),
                LocationResponse.class).getBody();

        assertThat(created).isNotNull();

        ResponseEntity<Void> del = rest.exchange(
                "/api/v1/locations/" + created.id(),
                HttpMethod.DELETE, null, Void.class);

        assertThat(del.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
