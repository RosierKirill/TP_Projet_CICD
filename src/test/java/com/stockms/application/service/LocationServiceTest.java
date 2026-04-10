package com.stockms.application.service;

import com.stockms.domain.exception.ConflictException;
import com.stockms.domain.exception.ResourceNotFoundException;
import com.stockms.domain.model.Location;
import com.stockms.domain.model.LocationType;
import com.stockms.domain.port.out.LocationRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    private Location aLocation;

    @BeforeEach
    void setUp() {
        aLocation = new Location(UUID.randomUUID(), "STORE01", "Aisle-A1",
                LocationType.AISLE, true);
    }

    @Test
    void create_uniqueStoreAndName_savesAndReturns() {
        given(locationRepository.existsByStoreCodeAndName("STORE01", "Aisle-A1"))
                .willReturn(false);
        given(locationRepository.save(any(Location.class))).willReturn(aLocation);

        Location result = locationService.create(aLocation);

        assertThat(result.getStoreCode()).isEqualTo("STORE01");
        assertThat(result.isActive()).isTrue();
        then(locationRepository).should().save(aLocation);
    }

    @Test
    void create_duplicate_throwsConflict() {
        given(locationRepository.existsByStoreCodeAndName("STORE01", "Aisle-A1"))
                .willReturn(true);

        assertThatThrownBy(() -> locationService.create(aLocation))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("STORE01");
    }

    @Test
    void findById_exists_returnsLocation() {
        given(locationRepository.findById(aLocation.getId()))
                .willReturn(Optional.of(aLocation));

        Location result = locationService.findById(aLocation.getId());

        assertThat(result).isEqualTo(aLocation);
    }

    @Test
    void findById_notFound_throwsResourceNotFound() {
        UUID unknown = UUID.randomUUID();
        given(locationRepository.findById(unknown)).willReturn(Optional.empty());

        assertThatThrownBy(() -> locationService.findById(unknown))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(unknown.toString());
    }

    @Test
    void findAll_returnsList() {
        given(locationRepository.findAll()).willReturn(List.of(aLocation));

        List<Location> result = locationService.findAll();

        assertThat(result).hasSize(1).contains(aLocation);
    }

    @Test
    void update_exists_updatesNameAndType() {
        given(locationRepository.findById(aLocation.getId()))
                .willReturn(Optional.of(aLocation));
        given(locationRepository.save(aLocation)).willReturn(aLocation);

        Location result = locationService.update(aLocation.getId(),
                "Reserve-R1", LocationType.RESERVE);

        assertThat(result.getName()).isEqualTo("Reserve-R1");
        assertThat(result.getType()).isEqualTo(LocationType.RESERVE);
    }

    @Test
    void update_notFound_throwsResourceNotFound() {
        UUID unknown = UUID.randomUUID();
        given(locationRepository.findById(unknown)).willReturn(Optional.empty());

        assertThatThrownBy(() -> locationService.update(unknown, "N", LocationType.AISLE))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deactivate_exists_setsActiveFalse() {
        given(locationRepository.findById(aLocation.getId()))
                .willReturn(Optional.of(aLocation));
        given(locationRepository.save(aLocation)).willReturn(aLocation);

        locationService.deactivate(aLocation.getId());

        assertThat(aLocation.isActive()).isFalse();
    }
}
