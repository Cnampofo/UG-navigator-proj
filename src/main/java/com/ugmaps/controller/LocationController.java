package com.ugmaps.controller;

import com.ugmaps.model.Location;
import com.ugmaps.model.LocationType;
import com.ugmaps.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "*")
public class LocationController {
    
    @Autowired
    private LocationService locationService;
    
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        Optional<Location> location = locationService.getLocationById(id);
        return location.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Location>> getLocationsByType(@PathVariable String type) {
        try {
            LocationType locationType = LocationType.valueOf(type.toUpperCase());
            List<Location> locations = locationService.getLocationsByType(locationType);
            return ResponseEntity.ok(locations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Location>> searchLocations(@RequestParam(required = false) String q) {
        List<Location> locations = locationService.searchLocations(q);
        return ResponseEntity.ok(locations);
    }
    
    @GetMapping("/bounds")
    public ResponseEntity<List<Location>> getLocationsWithinBounds(
            @RequestParam Double minLat,
            @RequestParam Double maxLat,
            @RequestParam Double minLon,
            @RequestParam Double maxLon) {
        List<Location> locations = locationService.getLocationsWithinBounds(minLat, maxLat, minLon, maxLon);
        return ResponseEntity.ok(locations);
    }
    
    @GetMapping("/nearest")
    public ResponseEntity<List<Location>> getNearestLocations(
            @RequestParam Double lat,
            @RequestParam Double lon,
            @RequestParam(defaultValue = "10") int limit) {
        List<Location> locations = locationService.getNearestLocations(lat, lon, limit);
        return ResponseEntity.ok(locations);
    }
    
    @PostMapping
    public ResponseEntity<Location> createLocation(@Valid @RequestBody Location location) {
        Location savedLocation = locationService.saveLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLocation);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @Valid @RequestBody Location location) {
        if (!locationService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        location.setId(id);
        Location updatedLocation = locationService.saveLocation(location);
        return ResponseEntity.ok(updatedLocation);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        if (!locationService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/types")
    public ResponseEntity<LocationType[]> getLocationTypes() {
        return ResponseEntity.ok(LocationType.values());
    }
}
