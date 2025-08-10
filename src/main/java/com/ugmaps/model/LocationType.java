package com.ugmaps.model;

public enum LocationType {
    ACADEMIC_BUILDING("Academic Building"),
    ADMINISTRATIVE_BUILDING("Administrative Building"),
    LIBRARY("Library"),
    RESIDENTIAL_HALL("Residential Hall"),
    DINING_HALL("Dining Hall"),
    SPORTS_FACILITY("Sports Facility"),
    MEDICAL_CENTER("Medical Center"),
    PARKING("Parking"),
    ENTRANCE_GATE("Entrance Gate"),
    LANDMARK("Landmark"),
    DEPARTMENT("Department"),
    FACULTY("Faculty"),
    AUDITORIUM("Auditorium"),
    LABORATORY("Laboratory"),
    CAFETERIA("Cafeteria"),
    ATM("ATM"),
    BOOKSTORE("Bookstore"),
    BUS_STOP("Bus Stop"),
    MOSQUE("Mosque"),
    CHAPEL("Chapel"),
    GARDEN("Garden"),
    RECREATIONAL_AREA("Recreational Area");
    
    private final String displayName;
    
    LocationType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public static LocationType fromDisplayName(String displayName) {
        for (LocationType type : LocationType.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No LocationType with display name: " + displayName);
    }
}
