package com.ugmaps.service;

import com.ugmaps.model.Location;
import com.ugmaps.model.LocationType;
import com.ugmaps.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    
    @Autowired
    private LocationRepository locationRepository;
    
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
    
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }
    
    public List<Location> getLocationsByType(LocationType type) {
        return locationRepository.findByType(type);
    }
    
    public List<Location> searchLocations(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllLocations();
        }
        return locationRepository.searchLocations(searchTerm.trim());
    }
    
    public List<Location> getLocationsWithinBounds(Double minLat, Double maxLat, Double minLon, Double maxLon) {
        return locationRepository.findLocationsWithinBounds(minLat, maxLat, minLon, maxLon);
    }
    
    public List<Location> getNearestLocations(Double latitude, Double longitude, int limit) {
        List<Location> allLocations = locationRepository.findAll();
        
        // Calculate distance for each location and sort by distance
        return allLocations.stream()
                .sorted((loc1, loc2) -> {
                    double dist1 = calculateDistance(latitude, longitude, loc1.getLatitude(), loc1.getLongitude());
                    double dist2 = calculateDistance(latitude, longitude, loc2.getLatitude(), loc2.getLongitude());
                    return Double.compare(dist1, dist2);
                })
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
    }
    
    private double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        // Simple Euclidean distance (for small distances like campus)
        double latDiff = lat1 - lat2;
        double lonDiff = lon1 - lon2;
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }
    
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }
    
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return locationRepository.existsById(id);
    }
}
