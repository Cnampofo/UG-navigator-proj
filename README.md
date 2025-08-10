# UG Campus Navigator

A production-ready Java web application for navigating the University of Ghana campus using open-source OpenStreetMap data.

## Features

- 🗺️ **Interactive Campus Map** - High-resolution OpenStreetMap integration with campus-specific bounds
- 🔍 **Location Search** - Find buildings, facilities, and landmarks across campus
- 📍 **Real-time Navigation** - Get directions between any two campus locations
- 🏢 **Categorized Locations** - Filter by academic buildings, residential halls, dining facilities, etc.
- 📱 **Responsive Design** - Works seamlessly on desktop, tablet, and mobile devices
- 💾 **Persistent Data** - H2 database with pre-populated campus locations
- 🌐 **RESTful API** - Complete backend API for location management

## Technology Stack

- **Backend**: Spring Boot 3.2.0, Java 17
- **Frontend**: HTML5, CSS3, JavaScript ES6+
- **Mapping**: Leaflet.js with OpenStreetMap tiles
- **Database**: H2 (development), easily configurable for PostgreSQL/MySQL in production
- **Build Tool**: Maven
- **UI Framework**: Custom responsive CSS with modern design

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Modern web browser

### Running the Application

1. **Clone or navigate to the project directory**
   ```bash
   cd "Java Maps"
   ```

2. **Build the application**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Main Application: http://localhost:8080
   - H2 Database Console: http://localhost:8080/h2-console
   - API Documentation: http://localhost:8080/api/locations

## API Endpoints

### Location Management

- `GET /api/locations` - Get all locations
- `GET /api/locations/{id}` - Get location by ID
- `GET /api/locations/type/{type}` - Get locations by type
- `GET /api/locations/search?q={query}` - Search locations
- `GET /api/locations/nearest?lat={lat}&lon={lon}&limit={limit}` - Find nearest locations
- `POST /api/locations` - Create new location (Admin)
- `PUT /api/locations/{id}` - Update location (Admin)
- `DELETE /api/locations/{id}` - Delete location (Admin)

### Location Types

Available location types:
- ACADEMIC_BUILDING
- ADMINISTRATIVE_BUILDING
- LIBRARY
- RESIDENTIAL_HALL
- DINING_HALL
- SPORTS_FACILITY
- MEDICAL_CENTER
- PARKING
- ENTRANCE_GATE
- LANDMARK
- AUDITORIUM
- CAFETERIA
- ATM
- BOOKSTORE
- BUS_STOP
- MOSQUE
- CHAPEL
- GARDEN

## Configuration

### Map Settings

Edit `src/main/resources/application.properties` to customize map behavior:

```properties
# Map Configuration
map.center.latitude=5.6484
map.center.longitude=-0.1864
map.zoom.default=16
map.zoom.min=14
map.zoom.max=20
map.bounds.north=5.6580
map.bounds.south=5.6388
map.bounds.east=-0.1780
map.bounds.west=-0.1948
```

### Database Configuration

For production deployment, update database settings:

```properties
# Production Database (PostgreSQL example)
spring.datasource.url=jdbc:postgresql://localhost:5432/ugmaps
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## Project Structure

```
Java Maps/
├── src/
│   ├── main/
│   │   ├── java/com/ugmaps/
│   │   │   ├── UgCampusNavigatorApplication.java
│   │   │   ├── config/
│   │   │   │   └── DataInitializer.java
│   │   │   ├── controller/
│   │   │   │   ├── LocationController.java
│   │   │   │   └── WebController.java
│   │   │   ├── model/
│   │   │   │   ├── Location.java
│   │   │   │   └── LocationType.java
│   │   │   ├── repository/
│   │   │   │   └── LocationRepository.java
│   │   │   └── service/
│   │   │       └── LocationService.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/style.css
│   │       │   └── js/app.js
│   │       ├── templates/
│   │       │   └── index.html
│   │       └── application.properties
├── pom.xml
└── README.md
```

## Key Features Explained

### 1. Campus-Bounded Map
The application restricts the map view to University of Ghana campus boundaries, ensuring users stay focused on relevant areas.

### 2. Location Categories
All campus locations are categorized for easy filtering:
- Academic facilities (schools, departments, libraries)
- Student life (residential halls, dining, recreation)
- Administrative services
- Support facilities (parking, ATMs, transport)

### 3. Intelligent Search
Search functionality looks through both location names and descriptions, making it easy to find specific places.

### 4. Turn-by-Turn Navigation
Uses Leaflet Routing Machine to provide walking directions between any two campus locations.

### 5. Responsive Design
The interface adapts to different screen sizes:
- Desktop: Full sidebar with detailed controls
- Mobile: Collapsible sidebar with touch-friendly interface

## Deployment

### Production Build

1. **Create production JAR**
   ```bash
   mvn clean package -Pprod
   ```

2. **Run production build**
   ```bash
   java -jar target/ug-campus-navigator-1.0.0.jar
   ```

### Docker Deployment

Create `Dockerfile`:
```dockerfile
FROM openjdk:17-jre-slim
COPY target/ug-campus-navigator-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

Build and run:
```bash
docker build -t ug-campus-navigator .
docker run -p 8080:8080 ug-campus-navigator
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is open source and available under the MIT License.

## Support

For technical support or feature requests, please create an issue in the project repository.

---

**University of Ghana Campus Navigator** - Making campus navigation simple and accessible for everyone.
