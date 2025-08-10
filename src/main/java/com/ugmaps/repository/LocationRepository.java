package com.ugmaps.repository;

import com.ugmaps.model.Location;
import com.ugmaps.model.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    
    // Find locations by type
    List<Location> findByType(LocationType type);
    
    // Find locations by name containing (case insensitive)
    @Query("SELECT l FROM Location l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Location> findByNameContainingIgnoreCase(@Param("name") String name);
    
    // Find locations within a bounding box
    @Query("SELECT l FROM Location l WHERE l.latitude BETWEEN :minLat AND :maxLat AND l.longitude BETWEEN :minLon AND :maxLon")
    List<Location> findLocationsWithinBounds(@Param("minLat") Double minLat, 
                                           @Param("maxLat") Double maxLat,
                                           @Param("minLon") Double minLon, 
                                           @Param("maxLon") Double maxLon);
    
    // Search locations by name or description
    @Query("SELECT l FROM Location l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(l.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Location> searchLocations(@Param("searchTerm") String searchTerm);
    
    // Find all locations (nearest calculation will be done in service layer)
    List<Location> findAll();
}
