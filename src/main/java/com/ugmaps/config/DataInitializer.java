package com.ugmaps.config;

import com.ugmaps.model.Location;
import com.ugmaps.model.LocationType;
import com.ugmaps.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private LocationService locationService;
    
    @Override
    public void run(String... args) throws Exception {
        // Only initialize if database is empty
        if (locationService.getAllLocations().isEmpty()) {
            initializeUGCampusLocations();
        }
    }
    
    private void initializeUGCampusLocations() {
        System.out.println("Initializing University of Ghana campus locations...");
        
        // Academic Buildings
        locationService.saveLocation(new Location(
            "Balme Library", 
            "Main university library with extensive collection of books, journals, and digital resources",
            5.6484, -0.1864, LocationType.LIBRARY
        ));
        
        locationService.saveLocation(new Location(
            "School of Medicine and Dentistry",
            "Faculty of Medicine and Dentistry with medical training facilities",
            5.6500, -0.1880, LocationType.ACADEMIC_BUILDING
        ));
        
        locationService.saveLocation(new Location(
            "School of Engineering Sciences",
            "Engineering faculty with laboratories and workshops",
            5.6470, -0.1890, LocationType.ACADEMIC_BUILDING
        ));
        
        locationService.saveLocation(new Location(
            "Business School",
            "University of Ghana Business School",
            5.6460, -0.1870, LocationType.ACADEMIC_BUILDING
        ));
        
        locationService.saveLocation(new Location(
            "School of Law",
            "University of Ghana School of Law",
            5.6495, -0.1855, LocationType.ACADEMIC_BUILDING
        ));
        
        // Administrative Buildings
        locationService.saveLocation(new Location(
            "Vice-Chancellor's Office",
            "Main administrative building and Vice-Chancellor's office",
            5.6488, -0.1868, LocationType.ADMINISTRATIVE_BUILDING
        ));
        
        locationService.saveLocation(new Location(
            "Registrar's Office",
            "Student registration and academic records office",
            5.6485, -0.1865, LocationType.ADMINISTRATIVE_BUILDING
        ));
        
        locationService.saveLocation(new Location(
            "Finance Office",
            "University finance and accounts office",
            5.6482, -0.1862, LocationType.ADMINISTRATIVE_BUILDING
        ));
        
        // Auditoriums
        locationService.saveLocation(new Location(
            "Great Hall",
            "Main auditorium for graduation ceremonies and major events",
            5.6490, -0.1870, LocationType.AUDITORIUM
        ));
        
        locationService.saveLocation(new Location(
            "N.N. Fosu Auditorium",
            "Large lecture auditorium for academic events",
            5.6475, -0.1885, LocationType.AUDITORIUM
        ));
        
        // Residential Halls
        locationService.saveLocation(new Location(
            "Commonwealth Hall",
            "Male residential hall known as 'Vandals'",
            5.6475, -0.1850, LocationType.RESIDENTIAL_HALL
        ));
        
        locationService.saveLocation(new Location(
            "Volta Hall",
            "Female residential hall known as 'Vikings'",
            5.6470, -0.1875, LocationType.RESIDENTIAL_HALL
        ));
        
        locationService.saveLocation(new Location(
            "Legon Hall",
            "Mixed residential hall known as 'Casford'",
            5.6465, -0.1845, LocationType.RESIDENTIAL_HALL
        ));
        
        locationService.saveLocation(new Location(
            "Akuafo Hall",
            "Male residential hall known as 'Guerrillas'",
            5.6455, -0.1835, LocationType.RESIDENTIAL_HALL
        ));
        
        locationService.saveLocation(new Location(
            "Africa Hall",
            "Female residential hall",
            5.6450, -0.1840, LocationType.RESIDENTIAL_HALL
        ));
        
        // Dining Facilities
        locationService.saveLocation(new Location(
            "TF Dining Hall",
            "Traditional Foods dining hall serving local cuisine",
            5.6478, -0.1858, LocationType.DINING_HALL
        ));
        
        locationService.saveLocation(new Location(
            "Pentagon Cafeteria",
            "Student cafeteria near residential areas",
            5.6468, -0.1848, LocationType.CAFETERIA
        ));
        
        // Sports Facilities
        locationService.saveLocation(new Location(
            "Sports Complex",
            "Main sports and recreation complex with gym and courts",
            5.6465, -0.1845, LocationType.SPORTS_FACILITY
        ));
        
        locationService.saveLocation(new Location(
            "Swimming Pool",
            "University swimming pool and aquatic center",
            5.6462, -0.1842, LocationType.SPORTS_FACILITY
        ));
        
        // Medical Facilities
        locationService.saveLocation(new Location(
            "University Hospital",
            "Korle Bu Teaching Hospital - University medical facility",
            5.6505, -0.1885, LocationType.MEDICAL_CENTER
        ));
        
        locationService.saveLocation(new Location(
            "Health Services",
            "Student health services and clinic",
            5.6480, -0.1860, LocationType.MEDICAL_CENTER
        ));
        
        // Gates and Entrances
        locationService.saveLocation(new Location(
            "Main Gate",
            "Primary entrance to University of Ghana campus",
            5.6520, -0.1890, LocationType.ENTRANCE_GATE
        ));
        
        locationService.saveLocation(new Location(
            "Okponglo Gate",
            "Secondary entrance near Okponglo junction",
            5.6440, -0.1820, LocationType.ENTRANCE_GATE
        ));
        
        // Religious Centers
        locationService.saveLocation(new Location(
            "University Mosque",
            "Central mosque for Muslim community",
            5.6472, -0.1852, LocationType.MOSQUE
        ));
        
        locationService.saveLocation(new Location(
            "University Chapel",
            "Interdenominational chapel for Christian worship",
            5.6485, -0.1867, LocationType.CHAPEL
        ));
        
        // Other Facilities
        locationService.saveLocation(new Location(
            "University Bookshop",
            "Main bookstore for academic materials and supplies",
            5.6483, -0.1863, LocationType.BOOKSTORE
        ));
        
        locationService.saveLocation(new Location(
            "Student Centre",
            "Central hub for student activities and services",
            5.6480, -0.1860, LocationType.ADMINISTRATIVE_BUILDING
        ));
        
        locationService.saveLocation(new Location(
            "Botanical Gardens",
            "University botanical gardens and research facility",
            5.6445, -0.1825, LocationType.GARDEN
        ));
        
        locationService.saveLocation(new Location(
            "Central Car Park",
            "Main parking facility for students and staff",
            5.6490, -0.1875, LocationType.PARKING
        ));
        
        locationService.saveLocation(new Location(
            "ATM - Main Campus",
            "Automated Teller Machine near student center",
            5.6481, -0.1861, LocationType.ATM
        ));
        
        locationService.saveLocation(new Location(
            "Pent Bus Stop",
            "Main bus stop for university transport",
            5.6475, -0.1855, LocationType.BUS_STOP
        ));
        
        System.out.println("Successfully initialized " + locationService.getAllLocations().size() + " campus locations!");
    }
}
